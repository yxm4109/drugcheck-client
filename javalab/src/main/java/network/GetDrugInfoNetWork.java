package network;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.network
 */
public class GetDrugInfoNetWork implements INetWork {

    @Override
    public JSONObject getDrugInfoByBarCode(String barCode) {

        OkHttpClient client = new OkHttpClient();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

        try {
            /**
             * 请求上级页面
             */
            Request request = new Request.Builder().url(NetConfig.sJsp).build();
            Response response;
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                response.body().string();

                /**
                 * 请求验证码
                 */
                request = new Request.Builder().url(NetConfig.sAuthCode).build();
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    /**
                     * 识别验证码
                     */

                    InputStream inputStream = response.body().byteStream();

                    FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/yanwei/image.jpg"));

                    byte[] bytes = new byte[1024];

                    int ch=0;

                    while ((ch=inputStream.read(bytes))!=-1){
                        fileOutputStream.write(bytes,0,ch);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    String captcha ;//= getCaptcha(inputStream);
                    Scanner scanner = new Scanner(System.in);

                    captcha = scanner.nextLine();


                    if (captcha != null) {

                        /**
                         * 查询请求
                         */
                        HashMap<String, String> postBody = NetConfig.getPostParam4GetFinalSearch(captcha, barCode);

                        RequestBody formBody;
                        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();

                        for (String s : postBody.keySet()) {
                            formEncodingBuilder.add(s, postBody.get(s));
                        }

                        formBody = formEncodingBuilder.build();

                        request = new Request.Builder().url(NetConfig.sSearchHost).post(formBody).build();
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()){
                            String html = response.body().string();
                            return ParseHtml.parseHtml(html);
                        }
                    }
                }

            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getCaptcha(InputStream is) {

        Socket socket = new Socket();

        try {
            socket.setSoTimeout(30);
            socket.setTcpNoDelay(true);
            socket.connect(new InetSocketAddress(NetConfig.ServerHostAddress, NetConfig.ServerHostPort));

            OutputStream os = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int ch;
            while ((ch = is.read(buffer)) != -1) {
                os.write(buffer, 0, ch);
            }
            os.flush();
            os.close();
            is.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            return br.readLine();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}

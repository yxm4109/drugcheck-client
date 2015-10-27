package net.ucopy.drugcheck.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.network
 */
public class GetDrugInfoNetWork implements INetWork {

    OkHttpClient client = new OkHttpClient();


    @Override
    public JSONObject getDrugInfoByBarCode(String barCode) {


        Request request = new Request.Builder().url(NetConfig.sJsp).build();
        Response response ;
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                response.body().string();

                client.getAuthenticator();


            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}

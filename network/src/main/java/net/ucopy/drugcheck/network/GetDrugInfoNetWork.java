package net.ucopy.drugcheck.network;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.network
 */
public class GetDrugInfoNetWork implements INetWork {
    @Override
    public JSONObject getDrugInfoByBarCode(String barCode) {

        new Thread(){

            @Override
            public void run(){
                OkHttpClient okHttpClient =new OkHttpClient();

            }

        }.start();



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hello","world");

            jsonObject.put("hello","world");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}

package network;


import org.json.JSONObject;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.network
 */
public interface INetWork {

    JSONObject getDrugInfoByBarCode(String barCode);

}

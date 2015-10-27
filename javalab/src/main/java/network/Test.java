package network;

/**
 * Created by meituan on 15/10/26.
 * com.meituan.srq.robo2.network
 */
public class Test {


    public static void main(String[] args){
        GetDrugInfoNetWork getDrugInfoNetWork = new GetDrugInfoNetWork();
        System.out.println(getDrugInfoNetWork.getDrugInfoByBarCode("81056020290075626833").toString());
    }


    public static final String TEST_DRUG_CODE = "81056020290075626833";
}

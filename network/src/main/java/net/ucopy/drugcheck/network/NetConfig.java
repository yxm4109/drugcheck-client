package net.ucopy.drugcheck.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class NetConfig {

    /**
     * 自己的服务器地址
     */
    public static final String ServerHostAddress = "http://192.168.1.106:8002";

    /**
     * 自己的服务器地址
     */
    public static final int ServerHostPort = 8002;

    /**
     * 药品监管局地址
     */
    public static String sBaseDomain = "http://sp.drugadmin.com";

    /**
     * 上层页面地址
     */
    public static String sJsp = "http://sp.drugadmin.com/drugWebQueryEmbed.jsp";

    /**
     * 获取验证码的地址
     */
    public static String sAuthCode = "http://sp.drugadmin.com/showValidateCode?" + Math.random();

    /**
     * 最终的查询地址
     */
    public static String sSearchHost = "http://sp.drugadmin.com/ivr/code/codeQuery.jhtml";

    /**
     * 监管码分段数量
     */
    static int iCheckCodeNum = 5;


    public static HashMap<String, String> getPostParam4GetFinalSearch(String authCode, String medicineCode)
            throws UnsupportedEncodingException {

        HashMap<String, String> params = new HashMap<>();

        params.put("captchaUUID", "");
        params.put("code", medicineCode);
        params.put("contactNo", "028-87563456");
        params.put("systemId", "drug-web");
        params.put("backno", "");

        for (int i = 0; i < iCheckCodeNum; i++) {
            params.put("checkcode" + (i + 1), medicineCode.substring(i * 4, i * 4 + 4));
        }

        params.put("areaNo", "028");
        params.put("contactNo1", "87563456");
        params.put("captcha", authCode);
        params.put("Submit.x", "20");
        params.put("Submit.y", "8");

        return params;

    }

}

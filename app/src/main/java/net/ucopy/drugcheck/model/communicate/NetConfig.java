package net.ucopy.drugcheck.model.communicate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.ucopy.drugcheck.exception.SuperviseCodeException;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class NetConfig {

	public static final String ServerHostAddress = "http://192.168.1.106:8002";

	public static final String Server4Command = ServerHostAddress + "/drugser/getcommand" + "/?code=";
	public static final String Server4ParseCommandRes = ServerHostAddress + "/drugser/parsecommandres" ;
	
	
	public static String sBaseDomain = "http://sp.drugadmin.com";
	public static String sJsp = "http://sp.drugadmin.com/drugWebQueryEmbed.jsp";
	public static String sAuthCode = "http://sp.drugadmin.com/showValidateCode?" + Math.random();
	public static String sSearchHost = "http://sp.drugadmin.com/ivr/code/codeQuery.jhtml";

	static int iCheckCodeNum = 5;

	public static final String Server4CaptCha = "http://192.168.1.125:8882/drugSer/search"
	        + "/?searchcode=81056020290075626833";

	public static final String TEST_DRUG_CODE = "81056020290075626833";

	public static String getFinalURL(String captcha, String medicineCode) {
		String reString = "http://sp.drugadmin.com/ivr/code/codeQuery.jhtml?" + "captchaUUID=&code=" + medicineCode
		        + "&" + "contactNo=028-87563456&" + "systemId=drug-web&backno=&";
		//
		// + "checkcode1=1233&" + "checkcode2=3323&" + "checkcode3=2323&"
		// + "checkcode4=2323&" + "checkcode5=2323&" + "areaNo=028&"
		// + "contactNo1=87563456&" + "captcha=7eh8&" + "searchthrough=3&"
		// + "Submit.x=20&Submit.y=8";

		if (20 != medicineCode.length()) {
			return null;
		}

		// String[] checkCodes = new String[iCheckCodeNum];
		for (int i = 0; i < medicineCode.length(); i++) {
			// checkCodes[i] = medicineCode.substring(i * 4, i * 4 + 4);
			reString = reString + "checkcode" + (i + 1) + "=" + medicineCode.substring(i * 4, i * 4 + 4) + "&";
		}

		reString = reString + "captcha=" + captcha + "&";

		reString = reString + "searchthrough=3&" + "Submit.x=20&Submit.y=8";

		return reString;
	}

	public class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static HttpPost getPostRequset4Search(String authCode, String medicineCode) throws SuperviseCodeException,
	        UnsupportedEncodingException {

		if (20 != medicineCode.length()) {
			throw new SuperviseCodeException();
		}

		HttpPost request = new HttpPost(sSearchHost);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("captchaUUID", ""));
		params.add(new BasicNameValuePair("code", medicineCode));
		params.add(new BasicNameValuePair("contactNo", "028-87563456"));
		params.add(new BasicNameValuePair("systemId", "drug-web"));
		params.add(new BasicNameValuePair("backno", ""));

		for (int i = 0; i < iCheckCodeNum; i++) {
			params.add(new BasicNameValuePair("checkcode" + i, medicineCode.substring(i * 4, i * 4 + 4)));
		}

		params.add(new BasicNameValuePair("areaNo", "028"));
		params.add(new BasicNameValuePair("contactNo1", "87563456"));
		params.add(new BasicNameValuePair("captcha", authCode));
		params.add(new BasicNameValuePair("Submit.x", "20"));
		params.add(new BasicNameValuePair("Submit.y", "8"));

		request.setEntity(new UrlEncodedFormEntity(params, Charset.forName("utf-8").toString()));

		return request;

	}

	public static HashMap<String, String> getPostParam4GetFinalSearch(String authCode, String medicineCode)
	        throws SuperviseCodeException, UnsupportedEncodingException {

		if (20 != medicineCode.length()) {
			throw new SuperviseCodeException();
		}

		HashMap<String, String> params = new HashMap<String, String>();

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

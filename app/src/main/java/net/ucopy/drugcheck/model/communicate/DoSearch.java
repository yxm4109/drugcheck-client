/**
  //=======\\//=======\\
  ||        ||         ||
  ||        ||         ||
  ||        ||         || //======   ||         /=====\     ||
  ||        ||         || ||         ||        ||     ||    ||
  ||        ||         || ||         ||        ||     ||    ||====\\
  ||        ||         || ||         ||        ||     ||    ||    ||
  ||        ||         || \\======   \\=====    \======\\   \\====//
  ------------------------------------------------------------------
                    
 */

/**
 * 药品搜索主要的类，所有的逻辑都在这里。根据信息码获取信息。并且保存以及显示。
 */

package net.ucopy.drugcheck.model.communicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.ucopy.drugcheck.BuildConfig;
import net.ucopy.drugcheck.model.manager.DCLocationManager;
import net.ucopy.drugcheck.view.DrugInfoActivity;
import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.tools.InputStreamUtils;
import net.ucopy.drugcheck.tools.KeyValueEntry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DoSearch {

	private static String TAG = "DoSearch";
	// private static String TAG = DoSearch.class.getSimpleName();

	private Context mContext = null;
	private RequestQueue mRequestQueue;

	private ProgressDialog mProcessDialog;

	private String mDrugCode;// 扫描得到的药品监管码

	public void setEleSupCode(String code) {
		this.mDrugCode = code;
	}

	private DrugInfo mDrugInfo = null;

	public DoSearch(Context context, String code) {
		this.mContext = context;
		mDrugCode = code;
		mRequestQueue = Volley.newRequestQueue(context);
		mProcessDialog = new ProgressDialog(context);
	}

	// 主要的逻辑控制,本来想把主要的逻辑放在这里的，但是因为使用的异步的网络
	// 框架，所以都放在这里有点麻烦，因此只能作为一个起点了！
	public void run() {
		// 连接服务器获取指令
		getCommandFromServer();
		mProcessDialog.show();
	}

	// 连接服务器获取命令。 服务器返回格式为json对象，包括
	// 1.url:要请求的地址
	// 2.method：请求方式get或者post，
	// 3.header：请求头部，是一个json对象
	// 4.params：请求的参数，是一个json对象，method为post时需要
	private void getCommandFromServer() {
		final String thisFunctionName = new Exception().getStackTrace()[0]
				.getMethodName();

		Log.e(TAG, "url=" + NetConfig.Server4Command + mDrugCode);

		JsonObjectRequest req = new JsonObjectRequest(NetConfig.Server4Command
				+ mDrugCode, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (BuildConfig.DEBUG) {
					Log.e(TAG, thisFunctionName + ":" + response.toString());
				}

				parseCommandReqRes(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, thisFunctionName + ":" + error.toString());
				showErrorToast();
			}
		});

		req.setTag(TAG);
		mRequestQueue.add(req);
	}

	// 执行从服务器获取的命令。 并将返回的数据返回给服务器
	@SuppressLint("DefaultLocale")
	private void executeCommand(String url, String method,
			final HashMap<String, String> headerMap,
			final HashMap<String, String> paramsMap) {

		final String thisFunctionName = new Exception().getStackTrace()[0]
				.getMethodName();

		Log.e(TAG, "executeCommand url=" + url);
		int reqMethod = (method.toLowerCase().equals("post")) ? Request.Method.POST
				: Request.Method.GET;

		String getUrl = getGetReqUrl(url, paramsMap);
		Log.e(TAG, thisFunctionName + " getUrl=" + getUrl);

		// ExecuteCommandTask task = new ExecuteCommandTask(getUrl, headerMap,
		// paramsMap);
		// task.execute();
		// return;

		StringRequest sr = new StringRequest(reqMethod, getUrl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						if (BuildConfig.DEBUG) {
							Log.e(TAG, thisFunctionName
									+ ",executeCommand succeed" + response);
						}

						sendCommandResToServer(DCLocationManager.getInstance.getLastLocation().getCity(),
								response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG,
								thisFunctionName + ":" + " error:"
										+ error.toString());
						showErrorToast();
					}

				}) {
			@Override
			protected Map<String, String> getParams() {
				return paramsMap;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return headerMap;
			}

		};
		sr.setTag(TAG);
		mRequestQueue.add(sr);

	}

	private String getGetReqUrl(String url, HashMap<String, String> paramsMap) {
		StringBuilder res = new StringBuilder();
		res.append(url);
		res.append("?");

		Iterator<String> it = paramsMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			res.append(key);
			res.append("=");
			res.append(paramsMap.get(key).trim());
			res.append("&");

		}

		// 去除最后一个&
		res.deleteCharAt(res.length() - 1);

		return res.toString();
	}

	class ExecuteCommandTask extends AsyncTask<Void, Void, String> {

		String url = null;

		HashMap<String, String> headerMap;
		HashMap<String, String> paramsMap;

		public ExecuteCommandTask(String url,
				HashMap<String, String> headerMap,
				HashMap<String, String> paramsMap) {
			super();
			this.url = url;
			this.headerMap = headerMap;
			this.paramsMap = paramsMap;
		}

		@Override
		protected void onPostExecute(String result) {

			Log.e(TAG, "onPostExecute result= " + result);

			sendCommandResToServer(DCLocationManager.getInstance.getLastLocation().getCity(), result);
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(Void... params) {

			HttpClient client = new DefaultHttpClient();

			// try {
			// HttpPost post = NetConfig.getPostRequset4Search(
			// paramsMap.get("captcha"), mDrugCode);
			// Iterator<String> it = headerMap.keySet().iterator();
			// while (it.hasNext()) {
			// String key = it.next();
			// String value = headerMap.get(key);
			// post.addHeader(key, value);
			// }
			//
			// HttpResponse response = client.execute(post);
			// if (response.getStatusLine().getStatusCode() == 200) {
			// HttpEntity he = response.getEntity();
			// return InputStreamUtils
			// .InputStreamTOString(he.getContent());
			// }
			// } catch (UnsupportedEncodingException e1) {
			// Log.e(TAG,
			// "UnsupportedEncodingException result= " + e1.toString());
			// } catch (SuperviseCodeException e1) {
			//
			// Log.e(TAG, "SuperviseCodeException result= " + e1.toString());
			// } catch (Exception e) {
			//
			// Log.e(TAG, "Exception result= " + e.toString());
			// }

			HttpGet get = new HttpGet(url);
			Iterator<String> it = headerMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = headerMap.get(key);
				get.addHeader(key, value);

			}

			try {
				HttpResponse response = client.execute(get);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity he = response.getEntity();

					try {
						return InputStreamUtils.InputStreamTOString(he
								.getContent());
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	/**
	 * 将command的执行结果返回到服务器
	 *
	 * @param city
	 * @param datas
	 */
	private void sendCommandResToServer(final String city, final String datas) {
		final String thisFunctionName = new Exception().getStackTrace()[0]
				.getMethodName();

		Log.e(TAG, thisFunctionName + ":url="
				+ NetConfig.Server4ParseCommandRes);
		StringRequest sr = new StringRequest(Request.Method.GET,
				NetConfig.Server4ParseCommandRes,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String responseString) {

						if (BuildConfig.DEBUG) {
							Log.e(TAG, "executeCommand succeed"
									+ responseString);
						}

						JSONObject response = null;
						try {
							response = new JSONObject(responseString);
							startInfosActivity(response);
						} catch (JSONException e) {
							Log.e(TAG, thisFunctionName + ":"
									+ "sendCommandResToServer error in json"
									+ e.getMessage());
							showErrorToast();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG,
								thisFunctionName + ":"
										+ "sendCommandResToServer error "
										+ error.getMessage());
						showErrorToast();
					}

				}) {
			@Override
			protected Map<String, String> getParams() {

				Map<String, String> res = new HashMap<String, String>();

				res.put("code", mDrugCode);
				res.put("city", city);
				res.put("datas", datas);

				return res;
			}

		};
		sr.setTag(TAG);
		mRequestQueue.add(sr);
	}

	/**
	 * 拼装druginfo数据Intent，启动druginfo显示activity。
	 */
	@SuppressWarnings("unchecked")
	public void startInfosActivity(JSONObject jsonObject) {
		final String thisFunctionName = new Exception().getStackTrace()[0]
				.getMethodName();
		try {
			// public DrugInfo(String picUrl, String
			// status,ArrayList<Entry<String, String>> baseInfos)
			String picUrl = jsonObject.getString("imageurl");
			String status = jsonObject.getString("status");

			JSONObject baseInfosJson = (JSONObject) jsonObject.get("header");
			ArrayList<Entry<String, String>> baseInfos = new ArrayList<Entry<String, String>>();
			Iterator<String> iterator = baseInfosJson.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				baseInfos.add(new KeyValueEntry<String, String>(key,
						(String) baseInfosJson.get(key)));
			}


		} catch (Exception e) {
			Log.e(TAG, thisFunctionName + ":" + e.toString());
			showErrorToast();
			return;
		}

		Intent intent = new Intent();
		intent.putExtra(DrugInfoActivity.DRUG_CODE_TAG, mDrugCode);

		// 图片单独处理一下，根据图片的url，如果是默认的图片，则直接传一个空的字符串过去。
		if (mDrugInfo.picUrl.endsWith("nodrugtip.jpg")) {
			intent.putExtra(DrugInfoActivity.DRUG_IMG_TAG, "");
		} else {
			intent.putExtra(DrugInfoActivity.DRUG_IMG_TAG, mDrugInfo.picUrl);
		}

		intent.putExtra(DrugInfoActivity.DRUG_STATE_TAG, mDrugInfo.status);

		intent.setClass(mContext, DrugInfoActivity.class);

		mContext.startActivity(intent);
		mProcessDialog.cancel();

	}

	/**
	 * 处理从服务器获取到的命令数据
	 */
	@SuppressWarnings("unchecked")
	private void parseCommandReqRes(JSONObject resJsonObject) {

		final String thisFunctionName = new Exception().getStackTrace()[0]
				.getMethodName();

		try {

			if (((String) resJsonObject.get("type")).equals("datas")) {
				startInfosActivity(resJsonObject.getJSONObject("datas"));
				return;
			}

			JSONObject paramDatas = resJsonObject.getJSONObject("datas");

			String url = (String) paramDatas.get("url");
			String method = (String) paramDatas.get("method");

			JSONObject header = (JSONObject) paramDatas.get("header");
			HashMap<String, String> headerMap = new HashMap<String, String>();
			Iterator<String> iterator = header.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				try {
					String value = (String) header.get(key);

					headerMap.put(key, value);
				} catch (Exception e) {
					Log.e(TAG,
							thisFunctionName + ":"
									+ "parseCommandReqRes:error in header"
									+ e.getMessage());
				}

			}

			JSONObject params = (JSONObject) paramDatas.get("params");
			HashMap<String, String> paramsMap = new HashMap<String, String>();
			iterator = params.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();

				try {
					String value = (String) params.get(key);
					if (value == null) {
						value = "";
					}
					paramsMap.put(key, value);
				} catch (Exception e) {
					Log.e(TAG,
							thisFunctionName + ":"
									+ "parseCommandReqRes:error in params"
									+ e.getMessage());
				}

			}
			Log.e(TAG, "len of params:" + paramsMap.size());

			executeCommand(url, method, headerMap, paramsMap);

		} catch (JSONException e) {
			Log.e(TAG, thisFunctionName + ":"
					+ "parseCommandReqRes:error in json" + e.getMessage());
		}
	}

	private void showErrorToast() {
		// Looper.prepare();
		Toast.makeText(mContext, "查找失败，请重试", Toast.LENGTH_SHORT).show();
		mProcessDialog.cancel();
	}

	public void cancelNetwork() {
		mRequestQueue.cancelAll(TAG);// 取消
	}

}

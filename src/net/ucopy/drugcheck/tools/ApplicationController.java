//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  
//                  不见满街漂亮妹，哪个归得程序员？ 

/**
 * Application 
 * @author yw (yxm4109[at]foxmail[dot]com)
 * 
 */
 
package net.ucopy.drugcheck.tools;

import java.util.Date;

import net.ucopy.drugcheck.entity.DCLocationModel;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

public class ApplicationController extends Application implements AMapLocationListener, OnClickListener {

	/**
	 * Log or request TAG
	 */
	public static final String TAG = ApplicationController.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		initLocation();
	}

	// /********************************************************************************************///
	//
	// / Locatin位置信息相关 ///
	//
	// /********************************************************************************************///

	SharedPreferences sp = this.getSharedPreferences("location", MODE_PRIVATE);
	private LocationManagerProxy mLocationManagerProxy;

	private void initLocation() {
		// 加载上次缓存的位置信息
		DCLocationModel.getLastLocation(sp);

		// 初始化定位，采用混合定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(true);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 5, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息
			DCLocationModel.setLatitude(amapLocation.getLatitude() + "");
			DCLocationModel.setLongtude(amapLocation.getLongitude() + "");
			DCLocationModel.setProvider(amapLocation.getProvider());
			DCLocationModel.setDate(new Date(amapLocation.getTime()));
			DCLocationModel.setAddress(amapLocation.getAddress());
			DCLocationModel.setCountry(amapLocation.getCountry());
			if (amapLocation.getProvince() == null) {
				DCLocationModel.setProvince(null);
			} else {
				DCLocationModel.setProvince(amapLocation.getProvince());
			}
			DCLocationModel.setCity(amapLocation.getCity());
			DCLocationModel.setDistrict(amapLocation.getDistrict());
			DCLocationModel.setRoad(amapLocation.getRoad());
			DCLocationModel.setPoiName(amapLocation.getPoiName());
			DCLocationModel.setCityCode(amapLocation.getCityCode());
			DCLocationModel.setAreaCode(amapLocation.getAdCode());
			// 保存本次获取到的位置信息
			DCLocationModel.setLastLocation(sp);
		} else {
			Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
		}
	}

	// /********************************************************************************************///
	//
	// / Velly请求相关 ///
	//
	// /********************************************************************************************///

	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;

	/**
	 * A singleton instance of the application class for easy access in other
	 * places
	 */
	private DefaultHttpClient mHttpClient;

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			// Create an instance of the Http client.
			// We need this in order to access the cookie store
			mHttpClient = new DefaultHttpClient();
			// create the request queue
			mRequestQueue = Volley.newRequestQueue(this, new HttpClientStack(mHttpClient));
		}
		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
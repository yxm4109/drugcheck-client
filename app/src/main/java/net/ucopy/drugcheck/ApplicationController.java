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
 
package net.ucopy.drugcheck;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import net.ucopy.drugcheck.model.manager.DCLocationManager;

import cn.bmob.v3.Bmob;

public class ApplicationController extends Application implements AMapLocationListener, OnClickListener {

	/**
	 * Log or request TAG
	 */
	public static final String TAG = ApplicationController.class.getSimpleName();

    private static ApplicationController appInstance;

	@Override
	public void onCreate() {
		super.onCreate();
        Bmob.initialize(this, "e8a02b540c61bf9a071e0a7969c34814");
        appInstance = this;
		initLocation();

		CrashHandler crashHandler =CrashHandler.getInstance();
		crashHandler.init(this);

	}

    public static ApplicationController getAppContext(){
        return appInstance;
    }

	// /********************************************************************************************///
	//
	// / Locatin位置信息相关 ///
	//
	// /********************************************************************************************///
	private LocationManagerProxy mLocationManagerProxy;

	private void initLocation() {
		// 加载上次缓存的位置信息
		DCLocationManager.getInstance.getLastLocation();

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
//			// 定位成功回调信息，设置相关消息
//			DCLocationModel.setLatitude(amapLocation.getLatitude() + "");
//			DCLocationModel.setLongtude(amapLocation.getLongitude() + "");
//			DCLocationModel.setProvider(amapLocation.getProvider());
//			DCLocationModel.setDate(new Date(amapLocation.getTime()));
//			DCLocationModel.setAddress(amapLocation.getAddress());
//			DCLocationModel.setCountry(amapLocation.getCountry());
//			if (amapLocation.getProvince() == null) {
//				DCLocationModel.setProvince(null);
//			} else {
//				DCLocationModel.setProvince(amapLocation.getProvince());
//			}
//			DCLocationModel.setCity(amapLocation.getCity());
//			DCLocationModel.setDistrict(amapLocation.getDistrict());
//			DCLocationModel.setRoad(amapLocation.getRoad());
//			DCLocationModel.setPoiName(amapLocation.getPoiName());
//			DCLocationModel.setCityCode(amapLocation.getCityCode());
//			DCLocationModel.setAreaCode(amapLocation.getAdCode());
			// 保存本次获取到的位置信息
			DCLocationManager.getInstance.setLastLocation(amapLocation.getCityCode(), amapLocation.getPoiName());
		} else {
			Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
		}
	}


}
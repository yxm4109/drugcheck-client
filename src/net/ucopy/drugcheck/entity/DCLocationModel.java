package net.ucopy.drugcheck.entity;

import java.util.Date;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DCLocationModel {
	private static String latitude;// 纬度
	private static String longtude;// 经度
	private static Date date;
	private static String provider;// 位置提供方式:lbs
	private static String address;// 位置描述
	private static String country;// 国家
	private static String province;// 省
	private static String city="";// 城市
	private static String district;// 区
	private static String road;// 街道
	private static String poiName;// poi名称
	private static String cityCode;// 城市编码
	private static String areaCode;// 区域编码

	public static void getLastLocation(SharedPreferences sp) {
		city = sp.getString("city", null);
		poiName = sp.getString("poiName", null);
	}

	public static void setLastLocation(SharedPreferences sp) {
		Editor e = sp.edit();
		e.putString("city", city);
		e.putString("poiName", poiName);
		e.commit();
	}

	public static Date getDate() {
		return date;
	}

	public static void setDate(Date date) {
		DCLocationModel.date = date;
	}

	public static String getLatitude() {
		return latitude;
	}

	public static void setLatitude(String latitude) {
		DCLocationModel.latitude = latitude;
	}

	public static String getLongtude() {
		return longtude;
	}

	public static void setLongtude(String longtude) {
		DCLocationModel.longtude = longtude;
	}

	public static String getProvider() {
		return provider;
	}

	public static void setProvider(String provider) {
		DCLocationModel.provider = provider;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		DCLocationModel.address = address;
	}

	public static String getCountry() {
		return country;
	}

	public static void setCountry(String country) {
		DCLocationModel.country = country;
	}

	public static String getProvince() {
		return province;
	}

	public static void setProvince(String province) {
		DCLocationModel.province = province;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		DCLocationModel.city = city;
	}

	public static String getDistrict() {
		return district;
	}

	public static void setDistrict(String district) {
		DCLocationModel.district = district;
	}

	public static String getRoad() {
		return road;
	}

	public static void setRoad(String road) {
		DCLocationModel.road = road;
	}

	public static String getPoiName() {
		return poiName;
	}

	public static void setPoiName(String poiName) {
		DCLocationModel.poiName = poiName;
	}

	public static String getCityCode() {
		return cityCode;
	}

	public static void setCityCode(String cityCode) {
		DCLocationModel.cityCode = cityCode;
	}

	public static String getAreaCode() {
		return areaCode;
	}

	public static void setAreaCode(String areaCode) {
		DCLocationModel.areaCode = areaCode;
	}

}

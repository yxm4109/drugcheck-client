package net.ucopy.drugcheck.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Weh on 15/10/19.
 */
public class SearchAction extends BmobObject{

    String date;//时间
    String barCode;//电子监管码
    String poiName;//poi名称，位置信息的一种
    String cityName;//城市名称
    String imei;//imei编号

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


}

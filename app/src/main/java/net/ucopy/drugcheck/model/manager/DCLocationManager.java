package net.ucopy.drugcheck.model.manager;

import android.content.Context;
import android.content.SharedPreferences;

import net.ucopy.drugcheck.ApplicationController;
import net.ucopy.drugcheck.entity.DCLocationModel;


/**
 * Created by Weh on 2015/10/18.
 */
public enum DCLocationManager {

    getInstance;

    SharedPreferences sp = ApplicationController.getAppContext().getSharedPreferences("DCLocationManager", Context.MODE_PRIVATE);

    DCLocationModel dcLocationModel = new DCLocationModel();

    public DCLocationModel getLastLocation() {
        dcLocationModel.setCity(sp.getString("cityCode", null));
        dcLocationModel.setPoiName( sp.getString("poiName", null));
        return dcLocationModel;
    }

    public void setLastLocation(String cityCode,String poiName) {
        SharedPreferences.Editor e = sp.edit();
        e.putString("cityCode", cityCode);
        e.putString("poiName", poiName);
        e.commit();
    }



}

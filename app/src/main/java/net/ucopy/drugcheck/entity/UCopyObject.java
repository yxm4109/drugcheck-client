package net.ucopy.drugcheck.entity;

import android.content.Context;

import net.ucopy.drugcheck.ApplicationController;

import cn.bmob.v3.BmobObject;

/**
 * Created by meituan on 15/10/28.
 * net.ucopy.drugcheck.entity
 */
public class UCopyObject extends BmobObject {


    @Override
    public void save(Context context) {
        super.save(context);
    }

    public void save() {
        super.save(ApplicationController.getAppContext());
    }

}

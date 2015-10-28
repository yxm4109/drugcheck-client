package net.ucopy.drugcheck.model.manager;

import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import net.ucopy.drugcheck.ApplicationController;
import net.ucopy.drugcheck.Db4oHelper;
import net.ucopy.drugcheck.entity.DrugInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.model.manager
 */
public enum DrugInfoManager {

    getInstance;

    Object lock = new Object();

    public DrugInfo getDrugInfo(String barCode) {
        return null;
    }

    public DrugInfo getDrugInfoFromDB(String barCode) {
        DrugInfo res = null;
        ObjectContainer container = Db4oHelper.getInstance.getConnection();
        if (container != null) {
            DrugInfo drugInfo = new DrugInfo();
            drugInfo.setBarCode(barCode);

            ObjectSet<DrugInfo> objectSet = container.queryByExample(drugInfo);

            if (objectSet.size() > 0) {
                res = objectSet.get(0);
            }
        }
        return res;
    }

    private DrugInfo drugInfo;

    private void setDrugInfo(DrugInfo drugInfo) {
        this.drugInfo = drugInfo;
    }

    public DrugInfo getDrugInfoFromNet(String barCode) {

        final DrugInfo drugInfo;

        BmobQuery<DrugInfo> bmobQuery = new BmobQuery<>();

        synchronized (lock) {

            bmobQuery.getObject(ApplicationController.getAppContext(), "6b6c11c537", new GetListener<DrugInfo>() {
                @Override
                public void onSuccess(DrugInfo object) {
                    setDrugInfo(object);
                    lock.notify();
                }

                @Override
                public void onFailure(int code, String msg) {

                    Log.e("查询失败：", msg);
                }
            });

            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return this.drugInfo;
    }

    public void saveDrugInfoToDB(DrugInfo drugInfo) {

        Db4oHelper.save(drugInfo);

    }

    public void saveDrugInfoToNet(DrugInfo drugInfo) {
        drugInfo.save();
    }

    public DrugInfo doSearch(String barCode) {
        return null;
    }

}

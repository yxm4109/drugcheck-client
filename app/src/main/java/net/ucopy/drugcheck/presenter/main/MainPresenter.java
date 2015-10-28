package net.ucopy.drugcheck.presenter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.ucopy.drugcheck.ApplicationController;
import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.entity.SearchAction;
import net.ucopy.drugcheck.model.manager.DrugInfoManager;
import net.ucopy.drugcheck.tools.ViewUtil;
import net.ucopy.drugcheck.view.DrugInfoActivity;
import net.ucopy.drugcheck.view.mainactivity.MainActivity;

import java.util.List;

/**
 * Created by Weh on 2015/10/18.
 */
public class MainPresenter implements IMainPresenter {

    MainActivity mainActivity;

    @Override
    public <T extends Activity> void onCreate(T t) {
        mainActivity = (MainActivity) t;
    }

    public void onDestroy() {
        mainActivity = null;
    }


    public List<SearchAction> getSearchActions() {
        return null;
    }

    @Override
    public DrugInfo getDrugInfo(String barCode) {
        return null;
    }

    @Override
    public void doSearch(final String barCode) {
        DrugInfo drugInfo;

        drugInfo = DrugInfoManager.getInstance.getDrugInfoFromDB(barCode);

        if (drugInfo == null) {

            new Thread() {
                @Override
                public void run() {
                    DrugInfo subDrugInfo = DrugInfoManager.getInstance.getDrugInfoFromNet(barCode);
                    if (subDrugInfo == null) {
                        subDrugInfo = DrugInfoManager.getInstance.doSearch(barCode);
                        if (subDrugInfo != null) {
                            subDrugInfo.save();
                        }
                    }
                    handleResult(subDrugInfo);
                    return;

                }
            }.start();


        } else {
            handleResult(drugInfo);
            return;
        }
    }

    private void handleResult(DrugInfo drugInfo) {

        mainActivity.stopProgressDialog();

        if (null == drugInfo) {
            ViewUtil.toast(ApplicationController.getAppContext(), "search fail!");
        } else {

            Intent intent = new Intent();

            Bundle bundle = new Bundle();
            bundle.putSerializable(DrugInfoActivity.DRUG_INFO_SHOW, drugInfo);

            intent.putExtras(bundle);
            intent.setClass(mainActivity, DrugInfoActivity.class);
            mainActivity.jumpActivity(intent);
        }
    }

}

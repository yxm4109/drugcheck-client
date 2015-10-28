package net.ucopy.drugcheck.view.mainactivity;

import android.content.Intent;

/**
 * Created by meituan on 15/10/25.
 * net.ucopy.drugcheck.view.mainactivity
 */
public interface IMainActivity {

    void showProgressDialog();

    void stopProgressDialog();

    void jumpActivity(Intent intent);

}

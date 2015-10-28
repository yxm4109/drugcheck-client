package net.ucopy.drugcheck.presenter;

import android.app.Activity;

/**
 * Created by meituan on 15/10/25.
 * net.ucopy.drugcheck.presenter
 */
public interface IBasePresenter {

    <T extends Activity> void onCreate(T t);

    void onDestroy();

}

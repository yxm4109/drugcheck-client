package net.ucopy.drugcheck.presenter.inputcode;

import android.app.Activity;

import net.ucopy.drugcheck.view.inputcode.IInputCodeActivity;

import java.util.List;

/**
 * Created by Weh on 2015/10/18.
 */
public class InputCodePresenter implements IInputCodePresenter {

    private IInputCodeActivity activity;


    public InputCodePresenter(IInputCodeActivity activity) {
        this.activity = activity;
    }


    @Override
    public <T extends Activity> void onCreate(T t) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public List<String> getHistory(String str) {
        return null;
    }

    @Override
    public void saveToHistory(String str) {

    }


}

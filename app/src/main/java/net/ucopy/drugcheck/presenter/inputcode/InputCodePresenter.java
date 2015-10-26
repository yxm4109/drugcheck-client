package net.ucopy.drugcheck.presenter.inputcode;

import net.ucopy.drugcheck.view.inputcode.IInputCodeActivity;

import java.util.List;

/**
 * Created by Weh on 2015/10/18.
 */
public class InputCodePresenter implements IInputcodePresenter{

    private IInputCodeActivity activity;


    public InputCodePresenter(IInputCodeActivity activity){
    this.activity = activity;
    }


    @Override
    public void onCreate() {

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

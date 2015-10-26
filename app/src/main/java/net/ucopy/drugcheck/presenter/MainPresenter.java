package net.ucopy.drugcheck.presenter;

import net.ucopy.drugcheck.entity.SearchAction;
import net.ucopy.drugcheck.view.mainactivity.MainActivity;

import java.util.List;

/**
 * Created by Weh on 2015/10/18.
 */
public class MainPresenter {

    MainActivity mainActivity;

    public void onCreate(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void onDestroy(){
        mainActivity = null;
    }

    public void startScanCode(){

    }

    public String onScannedCode(){
        return "";
    }

    public void gotoDrugInfo(String code){

    }

    public List<SearchAction> getSearchActions(){
        return  null;
    }



}

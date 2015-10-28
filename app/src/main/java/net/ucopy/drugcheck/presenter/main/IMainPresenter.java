package net.ucopy.drugcheck.presenter.main;

import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.entity.SearchAction;
import net.ucopy.drugcheck.presenter.IBasePresenter;

import java.util.List;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.presenter.main
 */
public interface IMainPresenter extends IBasePresenter {

    List<SearchAction> getSearchActions();

    DrugInfo getDrugInfo(String barCode);

    void doSearch(String barCode);


}

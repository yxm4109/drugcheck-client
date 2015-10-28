package net.ucopy.drugcheck.presenter.inputcode;

import net.ucopy.drugcheck.presenter.IBasePresenter;

import java.util.List;

/**
 * Created by meituan on 15/10/25.
 * net.ucopy.drugcheck.presenter.inputcode
 */
public interface IInputCodePresenter extends IBasePresenter {

    List<String> getHistory(String str);

    void saveToHistory(String str);

}

package net.ucopy.drugcheck.model.manager;

import net.ucopy.drugcheck.entity.SearchAction;

import java.util.List;

/**
 * Created by meituan on 15/10/26.
 * net.ucopy.drugcheck.model.manager
 */
public enum SearchActionManager {

    getInstance;

    /**
     * 检索所有的SearchAction
     *
     * @return
     */
    public List<SearchAction> getSearchActions() {
        return getSearchActions("");
    }

    /**
     * 根据{@link SearchAction#barCode}为前缀检索
     *
     * @param str
     * @return 以 str 为前缀的所有barcode
     */
    public List<String> getSearchBarCode(String str) {
        return null;
    }

    /**
     * 根据{@link SearchAction#barCode}为前缀检索
     *
     * @param str
     * @return
     */
    public List<SearchAction> getSearchActions(String str) {
        return null;
    }

    /**
     * 将一次成功的SearchAction存入到数据库
     *
     * @param searchAction
     */
    public void saveSearchAction(SearchAction searchAction) {

    }


}

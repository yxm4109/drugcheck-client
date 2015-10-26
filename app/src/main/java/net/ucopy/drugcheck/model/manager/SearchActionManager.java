package net.ucopy.drugcheck.model.manager;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import net.ucopy.drugcheck.Db4oHelper;
import net.ucopy.drugcheck.entity.SearchAction;

import java.util.ArrayList;
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

        List <SearchAction> searchActions = getSearchActions(str);

        List<String> res = new ArrayList<>();

        if (searchActions != null)
        {
            for (SearchAction searchAction:searchActions ){
                res.add(searchAction.getBarCode());
            }
        }

        return res;
    }

    /**
     * 根据{@link SearchAction#barCode}为前缀检索
     *
     * @param str
     * @return
     */
    public List<SearchAction> getSearchActions(final String str) {

        ObjectContainer objectContainer = Db4oHelper.getInstance.getConnection();

        if (objectContainer == null){
            return null;
        }

        List <SearchAction> searchActions = objectContainer.query(new Predicate<SearchAction>() {
            public boolean match(SearchAction searchAction) {
                return searchAction.getBarCode().contains(str);
            }
        });

        Db4oHelper.getInstance.releaseConnection(objectContainer);

        return searchActions;
    }

    /**
     * 将一次成功的SearchAction存入到数据库
     *
     * @param searchAction
     */
    public void saveSearchAction(SearchAction searchAction) {

        ObjectContainer objectContainer = Db4oHelper.getInstance.getConnection();

        if (objectContainer == null || searchAction == null){
            return ;
        }

        objectContainer.store(searchAction);
        objectContainer.commit();

        Db4oHelper.getInstance.releaseConnection(objectContainer);

    }

}

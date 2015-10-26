/**
 * //=======\\//=======\\
 * ||        ||         ||
 * ||        ||         ||
 * ||        ||         || //======   ||         /=====\     ||
 * ||        ||         || ||         ||        ||     ||    ||
 * ||        ||         || ||         ||        ||     ||    ||====\\
 * ||        ||         || ||         ||        ||     ||    ||    ||
 * ||        ||         || \\======   \\=====    \======\\   \\====//
 * ------------------------------------------------------------------
 */
package net.ucopy.drugcheck.view.inputcode;

import android.content.Intent;
import android.database.AbstractCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.model.manager.SearchActionManager;
import net.ucopy.drugcheck.presenter.inputcode.IInputCodePresenter;
import net.ucopy.drugcheck.presenter.inputcode.InputCodePresenter;
import net.ucopy.drugcheck.tools.ViewUtil;
import net.ucopy.drugcheck.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yw     E-mail:yxm4109@foxmail.com 
 * @date 2015-5-24 下午3:04:40 
 * @version 1.0
 * 说  明： 
 */
public class InputCodeActivity extends BaseActivity implements IInputCodeActivity {


    SearchView sv;

    SearchCursor cursor = new SearchCursor();

    SimpleCursorAdapter adapter;

    IInputCodePresenter inputCodePresenter;

    private static final String SV_BARCODE_CLO = "tb_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputCodePresenter = new InputCodePresenter(this);
        inputCodePresenter.onCreate();

        sv = (SearchView) findViewById(R.id.sv_inputcode_searchview);
        sv.setIconifiedByDefault(false);

        sv.setSubmitButtonEnabled(true);

        sv.setQueryHint(getResources().getString(R.string.please_input_ele_sup_code));

        adapter = new SimpleCursorAdapter(InputCodeActivity.this,
                R.layout.item_search_suggestion, cursor, new String[]{SV_BARCODE_CLO},
                new int[]{R.id.tv_search_suggestion});

        sv.setSuggestionsAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String str) {
                cursor.flushData(SearchActionManager.getInstance.getSearchBarCode(str));
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String str) {

                if (str.length() != 20) {
                    ViewUtil.toast(InputCodeActivity.this, "长度不正确,应该是20位的");
                    return false;
                } else {
                    handlerResult(str);
                }
                return true;
            }

        });

        sv.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                handlerResult(cursor.getData(position));
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        inputCodePresenter.onDestroy();
    }


    private  void handlerResult(String str){
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", str);
        resultIntent.putExtras(bundle);
        InputCodeActivity.this.setResult(RESULT_OK, resultIntent);
        finish();
    }

    /**
     * 得到布局文件
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_inputcode;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    public class SearchCursor extends AbstractCursor{
        List<String> data = new ArrayList<>();

        public void flushData(List<String> newData){
            data.clear();
            data.addAll(newData);
        }

        public String getData(int pos){
            return data.get(pos);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String[] getColumnNames() {
            return new String[]{"_id",SV_BARCODE_CLO};
        }

        @Override
        public String getString(int column) {
            return data.get(mPos);
        }

        @Override
        public short getShort(int column) {
            return 0;
        }

        @Override
        public int getInt(int column) {
            return 0;
        }

        @Override
        public long getLong(int column) {
            return 0;
        }

        @Override
        public float getFloat(int column) {
            return 0;
        }

        @Override
        public double getDouble(int column) {
            return 0;
        }

        @Override
        public boolean isNull(int column) {
            return false;
        }
    }


}
 
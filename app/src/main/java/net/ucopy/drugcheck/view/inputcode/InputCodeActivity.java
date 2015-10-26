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
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.presenter.inputcode.IInputcodePresenter;
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
    ListView lv;

    Cursor cursor;

    SimpleCursorAdapter adapter;

    IInputcodePresenter inputcodePresenter;

    private static final String SV_BARCODE_CLO = "tb_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputcodePresenter = new InputCodePresenter(this);
        inputcodePresenter.onCreate();
        cursor = new MatrixCursor(new String[]{SV_BARCODE_CLO});

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

                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String str) {

                if (str.length() != 20) {
                    ViewUtil.toast(InputCodeActivity.this, "长度不正确");
                    return false;
                } else {
                    Intent resultIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", str);
                    resultIntent.putExtras(bundle);
                    InputCodeActivity.this.setResult(RESULT_OK, resultIntent);
                    finish();
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
                cursor.moveToPosition(position);
                String str = cursor.getString(cursor.getColumnIndex("tb_name"));

                return false;
            }
        });

    }


    private Cursor getCursor(String str) {

         return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        inputcodePresenter.onDestroy();
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

    @Override
    public List<String> getHistroys() {
        return null;
    }

    public class SearchCursor extends AbstractCursor{
        List<String> data = new ArrayList<>();

        public void flushData(List<String> newData){
            data.clear();
            data.addAll(newData);
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String[] getColumnNames() {
            return new String[0];
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
 
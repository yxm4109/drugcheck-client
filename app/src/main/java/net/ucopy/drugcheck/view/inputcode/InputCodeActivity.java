/**
  //=======\\//=======\\
  ||        ||         ||
  ||        ||         ||
  ||        ||         || //======   ||         /=====\     ||
  ||        ||         || ||         ||        ||     ||    ||
  ||        ||         || ||         ||        ||     ||    ||====\\
  ||        ||         || ||         ||        ||     ||    ||    ||
  ||        ||         || \\======   \\=====    \======\\   \\====//
  ------------------------------------------------------------------
                    
 */
package net.ucopy.drugcheck.view.inputcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.List;


/** 
 * @author yw     E-mail:yxm4109@foxmail.com 
 * @date 2015-5-24 下午3:04:40 
 * @version   1.0
 * 说  明： 
 */
public class InputCodeActivity extends BaseActivity implements  IInputCodeActivity{


    SearchView sv ;
    ListView lv ;
    Cursor cursor = null;

    SimpleCursorAdapter adapter;

    IInputcodePresenter inputcodePresenter ;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

        inputcodePresenter = new InputCodePresenter(this);
        inputcodePresenter.onCreate();

        sv = (SearchView) findViewById(R.id.sv_inputcode_searchview);
        sv.setIconifiedByDefault(false);

        sv.setSubmitButtonEnabled(true);

        sv.setQueryHint("查询");


        cursor = this.getTestCursor("");

        adapter = new SimpleCursorAdapter(this,
                R.layout.item_search_suggestion, cursor, new String[] { "tb_name" },
                new int[] { R.id.tv_search_suggestion });



//        sv.setSuggestionsAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String str) {
//                getTestCursor(str);
//                adapter = new SimpleCursorAdapter(InputCodeActivity.this,
//                        R.layout.item_search_suggestion, cursor, new String[] { "tb_name" },
//                        new int[] { R.id.tv_search_suggestion });
//
//                sv.setSuggestionsAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String str) {

              if (str.length()!=20){
                  ViewUtil.toast(InputCodeActivity.this,"长度不正确");
                  return false;
              }else {
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

    @Override
    protected  void onDestroy(){
        super.onDestroy();

        inputcodePresenter.onDestroy();
    }

    //添加suggestion需要的数据
    public Cursor getTestCursor(String str) {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                this.getFilesDir() + "/my.db3", null);


        try {

            String insertSql = "insert into tb_test values (null,?,?)";



            String querySql = "select * from tb_test where tb_name like '"+str+"%'";

            cursor = db.rawQuery(querySql, null);


        } catch (Exception e) {

            String sql = "create table tb_test (_id integer primary key autoincrement,tb_name varchar(20),tb_age integer)";

            db.execSQL(sql);

            String insertSql = "insert into tb_test values (null,?,?)";

            String querySql = "select * from tb_test";

            cursor = db.rawQuery(querySql, null);
        }

        return cursor;
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
}
 
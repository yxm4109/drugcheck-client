package net.ucopy.drugcheck.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.model.communicate.DoSearch;
import net.ucopy.drugcheck.zxinglib.view.MipcaActivityCapture;

import java.util.ArrayList;


public class MainActivity extends Activity implements OnClickListener {
    //
    private static final String TAG = MainActivity.class.getSimpleName();

    // 扫描请求码
    private final static int SCANNIN_GREQUEST_CODE = 1;
    // 输入请求码
    private final static int INPUT_GREQUEST_CODE = 2;

    private int CURR_SELECTED_BUTTON = 0;
    // 扫描成功，震动提示
    private Vibrator mVibrator;

    // 核心处理逻辑类
    private DoSearch mDoSearch = null;

    // 扫描按钮
    private Button mBtnStartScan = null;
    private Button mBtnInfos = null;
    private Button mBtnTips = null;
    // 输入条码的et
    private TextView mTvInpuCode = null;
    private ListView mListView = null;

    private LinearLayout mLinearLayout=null;

    private StatusManager mStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        init();
        setListener();

        setAdapter();




    }

    private void setAdapter() {
//        CURR_SELECTED_BUTTON = mStatusManager.getSelectedButton();

        changeSelectedList();



    }

    public void init() {
//        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        mBtnStartScan = (Button) findViewById(R.id.btn_main_scanStart);
//        mBtnInfos = (Button) findViewById(R.id.btn_info_main_activity);
//        mBtnTips = (Button) findViewById(R.id.btn_tips_main_activity);
//        mTvInpuCode = (TextView) findViewById(R.id.tv_main_codeInput);
//        mListView = (ListView) findViewById(R.id.lv_infos_main_activity);
//        mLinearLayout= (LinearLayout) findViewById(R.id.ll_infos_tips_main);
//
//        mBtnStartScan = new StatusManager();

    }

    public void setListener() {
        mBtnStartScan.setOnClickListener(this);
        mTvInpuCode.setOnClickListener(this);
        mBtnInfos.setOnClickListener(this);
        mBtnTips.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mDoSearch.cancelNetwork();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case SCANNIN_GREQUEST_CODE:
                    mVibrator.vibrate(500);

                    // String format = bundle.getString("format");
                    // Log.e(TAG, code);
                    // Log.e(TAG, format);
                    // eleSupCode ="81056020290075626833";
                    // if (BuildConfig.DEBUG) {
                    // Log.e(TAG, code);
                    // }
                    // mDoSearch.setEleSupCode(code);
                    // mDoSearch.asyncControl(asyncSrc.SCAN_BARCODE);

                case INPUT_GREQUEST_CODE:
                    Bundle bundle = data.getExtras();
                    String code = bundle.getString("result");
//                    if (BuildConfig.DEBUG) {
//                        Log.e(TAG, code);
//                    }

                    mDoSearch = new DoSearch(this, code);
                    mDoSearch.run();

                    break;
            }
        }

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        switch (v.getId()) {
            case R.id.btn_main_scanStart:
                intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;

            case R.id.et_inputcode_input:
                intent.setClass(MainActivity.this, InputCodeActivity.class);
                startActivityForResult(intent, INPUT_GREQUEST_CODE);
                break;

            case R.id.btn_info_main_activity:

                if (CURR_SELECTED_BUTTON == R.id.btn_info_main_activity) {
                    return;
                } else {
                    CURR_SELECTED_BUTTON = R.id.btn_info_main_activity;

                   // CURR_SELECTED_BUTTON_TYPE = InfosAndTipsManager.dataType.INFOS;
                    changeSelectedList();
                }
                break;

            case R.id.btn_tips_main_activity:
                if (CURR_SELECTED_BUTTON == R.id.btn_tips_main_activity) {
                    return;
                } else {
                    CURR_SELECTED_BUTTON = R.id.btn_tips_main_activity;
                    //CURR_SELECTED_BUTTON_TYPE = InfosAndTipsManager.dataType.TIPS;
                    changeSelectedList();
                }
                break;
            default:
                break;
        }

    }

    class StatusManager {

        private SharedPreferences sp;
        SharedPreferences.Editor editor;

        private final String SELECTED_BUTTON = "SELECTED_BUTTON";

        public StatusManager() {
            sp = getSharedPreferences("statusManager", Context.MODE_PRIVATE);
            editor = sp.edit();

        }

//        public int getSelectedButton() {
//            return sp.getInt(SELECTED_BUTTON, R.id.btn_info_main_activity);
//        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void setSelectedButton(int i) {
            editor.putInt(SELECTED_BUTTON, i);
            editor.apply();
        }

    }


    public void changeSelectedList() {



    }


    public class InfosAdapter extends BaseAdapter {

        private ArrayList<Object> datas ;

        private LayoutInflater inflater;

        InfosAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        InfosAdapter(Context context, ArrayList<Object> data) {
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = inflater.inflate(R.layout.infos_item, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) findViewById(R.id.iv_item_infos);
                holder.title = (TextView) view
                        .findViewById(R.id.tv_title_item_infos);

                holder.summary = (TextView) view
                        .findViewById(R.id.tv_summary_item_infos);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }


            return view;
        }

        class ViewHolder {
            ImageView image;
            TextView title;
            TextView summary;
        }

    }

}

package net.ucopy.drugcheck.view.mainactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.model.manager.DebugManager;
import net.ucopy.drugcheck.model.communicate.DoSearch;
import net.ucopy.drugcheck.view.inputcode.InputCodeActivity;
import net.ucopy.drugcheck.view.base.BaseActivity;
import net.ucopy.drugcheck.zxinglib.view.MipcaActivityCapture;


public class MainActivity extends BaseActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 得到布局文件
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mBtnStartScan = (Button) findViewById(R.id.btn_main_scanStart);

        mTvInpuCode = (TextView) findViewById(R.id.tv_main_codeInput);
        mListView = (ListView) findViewById(R.id.lv_infos_main_activity);

    }

    @Override
    public void setListener() {
        mBtnStartScan.setOnClickListener(this);
        mTvInpuCode.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case SCANNIN_GREQUEST_CODE:
                    mVibrator.vibrate(500);

                case INPUT_GREQUEST_CODE:

                default:
                    Bundle bundle = data.getExtras();
                    String code = bundle.getString("result");
                    DebugManager.i(TAG,"code:"+code);
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

            case R.id.tv_main_codeInput:
                intent.setClass(MainActivity.this, InputCodeActivity.class);
                startActivityForResult(intent, INPUT_GREQUEST_CODE);
                break;

            default:
                break;
        }

    }




}

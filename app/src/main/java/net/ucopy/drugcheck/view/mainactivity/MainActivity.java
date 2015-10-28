package net.ucopy.drugcheck.view.mainactivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.presenter.main.IMainPresenter;
import net.ucopy.drugcheck.presenter.main.MainPresenter;
import net.ucopy.drugcheck.tools.DebugManager;
import net.ucopy.drugcheck.tools.ViewUtil;
import net.ucopy.drugcheck.view.base.BaseActivity;
import net.ucopy.drugcheck.view.inputcode.InputCodeActivity;
import net.ucopy.drugcheck.zxinglib.view.MipcaActivityCapture;


public class MainActivity extends BaseActivity implements IMainActivity {
    //
    private static final String TAG = MainActivity.class.getSimpleName();

    // 扫描请求码
    private final static int SCANNIN_GREQUEST_CODE = 1;
    // 输入请求码
    private final static int INPUT_GREQUEST_CODE = 2;

    private final static int SHOW_PROGRESS_DIALOG = 3;

    private final static int STOP_PROGRESS_DIALOG = 4;

    private final static int JUMP_ACTIVITY = 5;

    // 扫描成功，震动提示
    private Vibrator mVibrator;

    // 扫描按钮
    private Button mBtnStartScan = null;

    // 输入条码的et
    private TextView mTvInpuCode = null;

    private ListView mListView = null;

    private ProgressDialog progressDialog;

    IMainPresenter mainPresenter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case SHOW_PROGRESS_DIALOG:
                    ViewUtil.showProcessDialog(progressDialog, "Searching", "Waiting");
                    break;
                case STOP_PROGRESS_DIALOG:
                    progressDialog.cancel();
                    break;
                case JUMP_ACTIVITY:
                    startActivity((Intent) msg.obj);
                    break;
            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);

        mainPresenter = new MainPresenter();
        mainPresenter.onCreate(this);

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
        mainPresenter.onDestroy();
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
                    DebugManager.i(TAG, "code:" + code);
                    mainPresenter.doSearch(code);

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


    @Override
    public void showProgressDialog() {
        Message message = handler.obtainMessage();
        message.what = SHOW_PROGRESS_DIALOG;
        handler.sendMessage(message);
    }

    @Override
    public void stopProgressDialog() {
        Message message = handler.obtainMessage();
        message.what = STOP_PROGRESS_DIALOG;
        handler.sendMessage(message);
    }

    @Override
    public void jumpActivity(Intent intent) {
        Message message = handler.obtainMessage();
        message.what = JUMP_ACTIVITY;
        message.obj = intent;
        handler.sendMessage(message);

    }
}

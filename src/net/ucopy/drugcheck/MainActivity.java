package net.ucopy.drugcheck;

import net.ucopy.drugcheck.communicate.DoSearch;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	//
	private static final String TAG = MainActivity.class.getSimpleName();

	// 扫描请求码
	private final static int SCANNIN_GREQUEST_CODE = 1;
	// 输入请求码
	private final static int INPUT_GREQUEST_CODE = 2;

	// 扫描成功，震动提示
	private Vibrator mVibrator;

	// 核心处理逻辑类
	private DoSearch mDoSearch = null;

	// 扫描按钮
	private Button mBtnStartScan = null;
	// 输入条码的et
	private TextView mTvInpuCode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		setListener();

	}

	public void init() {
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		mBtnStartScan = (Button) findViewById(R.id.btn_main_scanStart);

		mTvInpuCode = (TextView) findViewById(R.id.tv_main_codeInput);

	}

	public void setListener() {
		mBtnStartScan.setOnClickListener(this);
		mTvInpuCode.setOnClickListener(this);
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
				if (BuildConfig.DEBUG) {
					Log.e(TAG, code);
				}

				mDoSearch = new DoSearch(this, code);
				mDoSearch.run();

				break;
			}
		}

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (v == mBtnStartScan) {
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		} else if (v == mTvInpuCode) {
			intent.setClass(MainActivity.this, InputCodeActivity.class);
			startActivityForResult(intent, INPUT_GREQUEST_CODE);
		}

	}

}

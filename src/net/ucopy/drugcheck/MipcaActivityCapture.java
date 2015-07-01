/**
 * http://blog.csdn.net/xiaanming/article/details/10163203
 */

package net.ucopy.drugcheck;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mining.app.zxing.camera.CameraManager;
import com.mining.app.zxing.decoding.CaptureActivityHandler;
import com.mining.app.zxing.decoding.InactivityTimer;
import com.mining.app.zxing.view.ViewfinderView;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends Activity implements Callback {

	private CaptureActivityHandler captureActivityHandler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

//		Button mButtonBack = (Button) findViewById(R.id.button_back);
//		mButtonBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				MipcaActivityCapture.this.finish();
//
//			}
//		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@SuppressWarnings("deprecation")
    @Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (captureActivityHandler != null) {
			captureActivityHandler.quitSynchronously();
			captureActivityHandler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 处理扫描结果
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result) {
		inactivityTimer.onActivity();
		String sResult = result.getText();
		String sBarcodeFormat = result.getBarcodeFormat().toString();

		if (sResult.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		} else {
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", sResult);
			bundle.putString("format", sBarcodeFormat);
			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK, resultIntent);
		}
		MipcaActivityCapture.this.finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (captureActivityHandler == null) {
			captureActivityHandler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return captureActivityHandler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

}

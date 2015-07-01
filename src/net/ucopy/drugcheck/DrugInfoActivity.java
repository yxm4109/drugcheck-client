package net.ucopy.drugcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.ucopy.drugcheck.component.LabelContentView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

@SuppressLint("InflateParams") public class DrugInfoActivity extends Activity {

	private LabelContentView eleSupCodeLCV = null;
	private ImageView drugImgIV = null;
	private ListView drugInfoLV = null;
	private LabelContentView drugStateLCV = null;

	public static final String DRUG_BASEINFO_TAG = "durgbaseinfo";
	public static final String DRUG_CODE_TAG = "drugcode";
	public static final String DRUG_IMG_TAG = "drugimg";
	public static final String DRUG_STATE_TAG = "drugstate";

	public static final int DRUG_IMG_WIDTH = 222;
	public static final int DRUG_IMG_HEIGTH = 206;

	ArrayList<Entry<String, String>> baseInfoDatas = null;
	String drugCode = null;
	String drugImgURL = null;
	String drugState = null;

	Intent intent;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_druginfo);

		intent = getIntent();
		drugCode = intent.getStringExtra(DRUG_CODE_TAG);
		drugImgURL = intent.getStringExtra(DRUG_IMG_TAG);
		drugState = intent.getStringExtra(DRUG_STATE_TAG);
		baseInfoDatas = (ArrayList<Entry<String, String>>) intent.getSerializableExtra(DRUG_BASEINFO_TAG);
		init();
	}

	private void init() {

		eleSupCodeLCV = (LabelContentView) findViewById(R.id.lcv_druginfo_ele_sup_code);															  
		eleSupCodeLCV.setLabel("电子监管码");
		eleSupCodeLCV.setContent(drugCode);

		drugImgIV = (ImageView) findViewById(R.id.iv_druginfo_drugImg);
		setDrugImg();

		drugInfoLV = (ListView) findViewById(R.id.lv_druginfo_infos);
		drugInfoLV.setAdapter(new DrugInfoAdapter(baseInfoDatas));

		drugStateLCV = (LabelContentView) findViewById(R.id.lcv_druginfo_state);
		drugStateLCV.setLabel("当前状态");
		drugStateLCV.setContent(drugState);

	}

	private void setDrugImg() {
		if (drugImgURL=="") {			
			drugImgIV.setImageResource(R.drawable.nodrugimg_jpg);			
			return;
		}		
		ImageRequest ir = new ImageRequest(drugImgURL, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				drugImgIV.setImageBitmap(response);
			}
		}, DRUG_IMG_WIDTH, DRUG_IMG_HEIGTH, Config.RGB_565, null);
		RequestQueue mQueue = Volley.newRequestQueue(this);
		mQueue.add(ir);
	}

	class DrugInfoAdapter extends BaseAdapter {

		List<Entry<String, String>> datas = null;
		Context context;
		private LayoutInflater mInflater ;

		public DrugInfoAdapter(List<Entry<String, String>> datas) {
			super();
			this.datas = datas;
			this.mInflater= getLayoutInflater();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.lv_druginfo_item, null);
				holder = new ViewHolder();
				holder.lcv = (LabelContentView) convertView.findViewById(R.id.lcv_druginfo_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Entry<String, String> data = datas.get(position);
			holder.lcv.setLabel(data.getKey());
			holder.lcv.setContent(data.getValue());
			return convertView;
		}

	}

	public static class ViewHolder {
		public LabelContentView lcv;
	}

}

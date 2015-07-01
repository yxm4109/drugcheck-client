package net.ucopy.drugcheck;

import java.util.ArrayList;

import net.ucopy.drugcheck.tools.KeyValueEntry;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InfosFragment extends Fragment {

	public static final int INDEX = 0;
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.infos_list, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		((ListView) listView).setAdapter(new ImageAdapter(getActivity()));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private static class ImageAdapter extends BaseAdapter {

		private static final ArrayList<KeyValueEntry<String, String>> datas = new TestData().tipsList;

		private LayoutInflater inflater;

		ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = inflater.inflate(R.layout.item_tips, parent, false);
				holder = new ViewHolder();
				holder.title = (TextView) view.findViewById(R.id.item_tips_title);

				holder.summary = (TextView) view.findViewById(R.id.item_tips_summary);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.title.setText(datas.get(position).getKey());

			holder.summary.setText(datas.get(position).getValue());

			return view;
		}
	}

	static class ViewHolder {
		TextView title;
		TextView summary;
	}

}
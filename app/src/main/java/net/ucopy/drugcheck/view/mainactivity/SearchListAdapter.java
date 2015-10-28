package net.ucopy.drugcheck.view.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.ucopy.drugcheck.R;
import net.ucopy.drugcheck.entity.SearchAction;

import java.util.ArrayList;

/**
 * Created by meituan on 15/10/24.
 * net.ucopy.drugcheck.view.mainactivity
 */

public class SearchListAdapter extends BaseAdapter {

    private ArrayList<SearchAction> datas;

    private LayoutInflater inflater;

    public SearchListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public SearchListAdapter(Context context, ArrayList<SearchAction> data) {
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
            holder.image = (ImageView) view.findViewById(R.id.iv_item_infos);
            holder.title = (TextView) view
                    .findViewById(R.id.tv_title_item_infos);

            holder.summary = (TextView) view
                    .findViewById(R.id.tv_summary_item_infos);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(datas.get(position).getBarCode());

        holder.title.setText(datas.get(position).getBarCode());

        holder.title.setText(datas.get(position).getBarCode());


        return view;
    }

    class ViewHolder {
        ImageView image;
        TextView title;
        TextView summary;
    }

}




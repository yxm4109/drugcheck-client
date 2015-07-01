package net.ucopy.drugcheck.component;

import net.ucopy.drugcheck.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleView extends LinearLayout {

	private TextView labelTV;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.activity_title, this, true);
		labelTV = (TextView) findViewById(R.id.tv_druginfo_item_label_eleSupCode);
	}

	public void setText(String text) {
		labelTV.setText(text);
	}

}

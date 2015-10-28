package net.ucopy.drugcheck.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.ucopy.drugcheck.R;

public class LabelContentView extends LinearLayout {

    private TextView labelTV;
    private TextView contentTV;

    public LabelContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.label_content_item, this, true);
        labelTV = (TextView) findViewById(R.id.tv_druginfo_item_label_eleSupCode);
        contentTV = (TextView) findViewById(R.id.tv_druginfo_item_eleSupCode);
    }


    public void setLabel(String label) {
        labelTV.setText(label);
    }

    /**
     * 设置显示的文字
     */
    public void setContent(String content) {
        contentTV.setText(content);
    }


}

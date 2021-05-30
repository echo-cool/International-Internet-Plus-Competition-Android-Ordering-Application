package com.app.myapplication.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;

public class ChildViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private TextView childRightText;

    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final NotificationBean dataBean, final int pos){

        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
        childRightText = (TextView) view.findViewById(R.id.child_right_text);
        childLeftText.setText(dataBean.getChildLeftTxt());
        childRightText.setText(dataBean.getChildRightTxt());

    }
}
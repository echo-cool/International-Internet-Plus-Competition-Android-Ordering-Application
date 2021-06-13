package com.app.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.app.myapplication.R;

public class PopSelect extends PopupWindow {
    private View conentView;
    public PopSelect(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.select_layout, null);
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);

        this.setWidth(w / 2 - 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h / 2 - 50);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();

        Button selfStudy = conentView.findViewById(R.id.selfstudy_btn);
        View spotMarkerView = context.findViewById(R.id.fourth_building_point);
        View third = context.findViewById(R.id.third_building_point);
        View lb = context.findViewById(R.id.library_point);
        selfStudy.setOnClickListener(new View.OnClickListener() {
            //                    @Override
            public void onClick(View v) {
                        spotMarkerView.setVisibility(View.VISIBLE);
                        third.setVisibility(View.VISIBLE);
                        lb.setVisibility(View.VISIBLE);
                        PopSelect.this.dismiss();
            }
        });
    }
}

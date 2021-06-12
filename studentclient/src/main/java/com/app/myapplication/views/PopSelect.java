package com.app.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
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

    }
}

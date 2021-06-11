package com.app.myapplication.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.app.myapplication.fragments.ShopOrderFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.app.beans.TextBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;

import java.util.LinkedList;
import java.util.List;

import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;


public class ForumAdapter extends BaseQuickAdapter<TextBean, BaseViewHolder>{
    List<TextBean> list;
    ForumAdapter self;
    Broccoli broccoli;
    //构造方法，传入数据
    public ForumAdapter(@Nullable  List<TextBean> data){
        super(R.layout.forum_item,data);
        this.list = list;
        broccoli = new Broccoli();
    }

    @Override
    protected void convert(BaseViewHolder helper, TextBean item) {

    }

}

package com.app.myapplication.adapters;

import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

public class FoodSimpleAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    private List<FoodBean> list;
    private FoodSimpleAdapter self;

    public FoodSimpleAdapter(@Nullable @org.jetbrains.annotations.Nullable List<FoodBean> data) {
        super(R.layout.item_food_simple,data);
        list=data;
        self=this;
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.tv_name,item.foodName);
        helper.setText(R.id.textView9,"x"+item.selectCount);
        helper.setText(R.id.tv_price,new DecimalFormat("0.0").format(item.foodPrice));
        if(item.foodImage!=null)
            helper.setImageBitmap(R.id.iv_food, BitmapFactory.decodeByteArray(item.foodImage, 0, item.foodImage.length));
    }

    public List<FoodBean> getList() {
        return list;
    }
}

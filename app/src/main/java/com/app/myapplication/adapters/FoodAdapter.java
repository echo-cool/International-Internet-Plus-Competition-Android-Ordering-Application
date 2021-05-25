package com.app.myapplication.adapters;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;

import java.util.List;

public class FoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    List<FoodBean> list;

    public FoodAdapter(@Nullable  List<FoodBean> data) {
        super(R.layout.item_food,data);
        list=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.tv_name,item.foodName);
        helper.setText(R.id.tv_summary,item.foodSummary);
        helper.setText(R.id.tv_price,""+item.foodPrice);
        helper.setText(R.id.tv_sale,item.foodSale+"");
        helper.setImageBitmap(R.id.iv_food,item.foodImage);
    }
}

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
import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;

import java.util.LinkedList;
import java.util.List;

import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

public class FoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    List<FoodBean> list;
    OnCountChange onCountChange;
    FoodAdapter self;
    Broccoli broccoli;
    AddWidget.OnAddWidgetClick addWidgetClick;


    public FoodAdapter(@Nullable  List<FoodBean> data) {
        super(R.layout.item_food,data);
        list=data;
        self=this;
        broccoli = new Broccoli();

    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.tv_name,item.foodName);
        helper.setText(R.id.tv_summary,item.foodSummary);
        helper.setText(R.id.tv_price,"¥ "+item.foodPrice);
        helper.setText(R.id.tv_sale,"日销:"+item.foodSale);
        helper.addOnClickListener(R.id.iv_food);
        //System.out.println("convert");
        if(item.foodImage==null){
            //broccoli.addPlaceholder(new PlaceholderParameter.Builder().setView(helper.getView(R.id.iv_food)).setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"), Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator())).build());

        }else {
            broccoli.removePlaceholder(helper.getView(R.id.iv_food));
            helper.setImageBitmap(R.id.iv_food, BitmapFactory.decodeByteArray(item.foodImage, 0, item.foodImage.length));
        }
        broccoli.show();
        //helper.setImageDrawable(R.id.iv_food,mContext.getDrawable(R.drawable.app_logo));
        AddWidget addWidget=((AddWidget)helper.getView(R.id.addwidget));
        addWidget.bindFoodBean(item);
        addWidget.setCount(item.selectCount);
        addWidgetClick=new AddWidget.OnAddWidgetClick() {
            @Override
            public void onClick() {
                self.notifyDataSetChanged();
                try {
                    onCountChange.onChange(item);

                }catch (NullPointerException ignore){}
            }
        };
        ((AddWidget)helper.getView(R.id.addwidget)).setOnAddWidgetClick(addWidgetClick);
    }

    public List<FoodBean> getList() {
        return list;
    }

    public List<FoodBean> getTypeFood(String type){
        List<FoodBean> list=new LinkedList<>();
        for(FoodBean i:this.list){
            if(i.foodType.equals(type)) list.add(i);
        }
        return list;
    }

    public void setOnCountChange(OnCountChange onCountChange){
        this.onCountChange=onCountChange;
    }

    public interface OnCountChange{
        void onChange(FoodBean item);
    }

    public AddWidget.OnAddWidgetClick getAddWidgetClick() {
        return addWidgetClick;
    }
}

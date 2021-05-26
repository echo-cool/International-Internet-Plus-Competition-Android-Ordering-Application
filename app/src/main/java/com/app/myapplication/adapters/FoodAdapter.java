package com.app.myapplication.adapters;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;

import java.util.LinkedList;
import java.util.List;

public class FoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
    List<FoodBean> list;
    OnCountChange onCountChange;
    FoodAdapter self;


    public FoodAdapter(@Nullable  List<FoodBean> data) {
        super(R.layout.item_food,data);
        list=data;
        self=this;

    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.tv_name,item.foodName);
        helper.setText(R.id.tv_summary,item.foodSummary);
        helper.setText(R.id.tv_price,""+item.foodPrice);
        helper.setText(R.id.tv_sale,item.foodSale+"");
        helper.setImageBitmap(R.id.iv_food,item.foodImage);
        AddWidget addWidget=((AddWidget)helper.getView(R.id.addwidget));
        addWidget.bindFoodBean(item);
        addWidget.setCount(item.selectCount);
        ((AddWidget)helper.getView(R.id.addwidget)).setOnAddWidgetClick(new AddWidget.OnAddWidgetClick() {
            @Override
            public void onClick() {
                self.notifyDataSetChanged();
                try {
                    onCountChange.onChange(item);
                }catch (NullPointerException ignore){}
            }
        });
        for(FoodBean i: getList()){
            System.out.println(i.foodName+"|||"+i.selectCount);
        }
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
}

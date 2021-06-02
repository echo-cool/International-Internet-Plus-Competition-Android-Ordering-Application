package com.example.restaurantclient.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.beans.OrderBean;
import com.example.restaurantclient.R;
import com.example.restaurantclient.views.OrderView;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    List<OrderBean> list;
    OrderAdapter _this;
    Context mContext;

    public OrderAdapter(Context context, @Nullable @org.jetbrains.annotations.Nullable List<OrderBean> data) {
        super(R.layout.view_order,data);
        list=data;
        _this=this;
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.text_title,item.title);
        helper.setText(R.id.text_info,item.info);
        helper.setText(R.id.text_content,item.content);
        helper.getView(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:将数据库上该订单的isConfirmed设置为true,如果设置成功将item的isConfirm属性设置为true,同时调用_this.notifyDataSetChange();
            }
        });
        if(item.isConfirmed){
            helper.getView(R.id.layout).setBackgroundColor(mContext.getResources().getColor(R.color.bluegreen));
            helper.getView(R.id.button).setEnabled(false);
        }

    }

    public void setList(List<OrderBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}

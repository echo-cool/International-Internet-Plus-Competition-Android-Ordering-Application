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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
                //TODO:将数据库上该订单的isConfirmed设置为true,如果设置成功将item的isConfirm属性设置为true,同时调用_this.notifyDataSetChange(),同时向用户端推送骑手已取餐消息，在消息表上给用户写一条已取餐消息;
                AVObject order = AVObject.createWithoutData("Order", item.id);
                order.put("isConfirmed", true);
                order.saveInBackground().subscribe(new Observer<AVObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NotNull AVObject avObject) {
                        _this.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(@NotNull Throwable e) {

                    }
                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
        helper.getView(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:将数据库上该订单的isEnded设置为true,如果设置成功,从list中删除这个item并同时调用_this.notifyDataSetChange(),同时向用户端推送已送达消息，同时在消息表上给用户写一条订单结束消息;
                AVObject order = AVObject.createWithoutData("Order", item.id);
                order.put("isEnded", true);
                order.saveInBackground().subscribe(new Observer<AVObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull AVObject avObject) {
                        _this.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


            }
        });
        if(item.isConfirmed){
            helper.getView(R.id.layout).setBackgroundColor(mContext.getResources().getColor(R.color.bluegreen));
            helper.getView(R.id.button).setEnabled(false);
        }

    }

    public void setList(List<OrderBean> list) {
        //刷新时使用，用于重设list
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<OrderBean> getList() {
        //用于livequery
        return list;
    }
}

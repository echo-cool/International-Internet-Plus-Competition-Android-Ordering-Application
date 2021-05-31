package com.app.myapplication.adapters;

import android.view.View;

import androidx.annotation.Nullable;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class NotificationAdapter extends BaseQuickAdapter<NotificationBean, BaseViewHolder> {
    List<NotificationBean> list;
    NotificationBean expand=null;
    int expandPosition=-1;

    public NotificationAdapter(@Nullable @org.jetbrains.annotations.Nullable List<NotificationBean> data) {
        super(R.layout.view_notification,data);
        this.list =data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationBean item) {
        helper.setText(R.id.textView10, item.title);
        helper.setText(R.id.textView11, item.summary);
        helper.setText(R.id.textView3, item.detail);
        if(expand==item)
            helper.getView(R.id.hidden_information).setVisibility(View.VISIBLE);
        else helper.getView(R.id.hidden_information).setVisibility(View.GONE);

    }

    public List<NotificationBean> getList() {
        return list;
    }

    public void setList(List<NotificationBean> list) {
        this.list = list;
    }

    public void addAll(List<NotificationBean> list){
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public NotificationBean getExpandNotificationBean(){
        return expand;
    }

    public void setExpandNotificationBean(NotificationBean notificationBean,int position){
        expand=notificationBean;
        expandPosition=position;
    }

    public void setExpandNotificationBean(NotificationBean notificationBean){
        expand=notificationBean;
    }

    public int getExpandPosition() {
        return expandPosition;
    }
}

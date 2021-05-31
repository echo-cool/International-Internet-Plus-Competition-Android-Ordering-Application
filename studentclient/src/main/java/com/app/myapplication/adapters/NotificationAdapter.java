package com.app.myapplication.adapters;

import android.view.View;

import androidx.annotation.Nullable;

import com.app.beans.NotificationCardBean;
import com.app.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class NotificationAdapter extends BaseQuickAdapter<NotificationCardBean, BaseViewHolder> {
    List<NotificationCardBean> list;

    public NotificationAdapter(@Nullable @org.jetbrains.annotations.Nullable List<NotificationCardBean> data) {
        super(R.layout.view_notification,data);
        this.list =data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationCardBean item) {
        helper.setText(R.id.textView10, item.title);
        helper.setText(R.id.textView11, item.summary);
        helper.setText(R.id.textView3, item.detail);
        if(item.isExpand)
            helper.getView(R.id.hidden_information).setVisibility(View.VISIBLE);
        else helper.getView(R.id.hidden_information).setVisibility(View.GONE);

    }

    public List<NotificationCardBean> getList() {
        return list;
    }

    public void addAll(List<NotificationCardBean> list){
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }


}

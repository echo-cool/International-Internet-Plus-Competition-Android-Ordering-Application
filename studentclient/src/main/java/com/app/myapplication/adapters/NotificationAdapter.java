package com.app.myapplication.adapters;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.app.utils.ViewUtils;
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
        //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        helper.setText(R.id.textView10, item.title);
        helper.setText(R.id.textView11, item.summary);
        helper.setText(R.id.textView3, item.detail);
        View view1=helper.getView(R.id.hidden_information);
        View view=helper.getView(R.id.notification_card);


        view1.setVisibility(View.VISIBLE);
        if(expand==item) {
            //view1.setVisibility(View.VISIBLE);
            view.getLayoutParams().height= ViewUtils.dip2px(mContext,264);
            view.setAlpha(1.0f);
        }
        else {
            view.getLayoutParams().height= ViewUtils.dip2px(mContext,88);
            view.setAlpha(0.8f);
            //view1.setVisibility(View.GONE);
        }
//        if(item== list.get(0)){
//            ConstraintLayout.LayoutParams layoutParams=(ConstraintLayout.LayoutParams) helper.getView(R.id.notification_card).getLayoutParams();
//            layoutParams.topMargin=128;
//        }

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

    public void setExpandPosition(int expandPosition) {
        this.expandPosition = expandPosition;
    }
}

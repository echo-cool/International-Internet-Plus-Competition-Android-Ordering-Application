package com.app.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beans.NotificationBean;
import com.app.myapplication.adapters.NotificationAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;
        recyclerView=findViewById(R.id.recyclerView17);
        notificationAdapter=new NotificationAdapter(new LinkedList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                notificationAdapter.getList().get(position).isExpand=!notificationAdapter.getList().get(position).isExpand;
                notificationAdapter.notifyDataSetChanged();
            }
        });
        load(testUtil());
        notificationAdapter.notifyDataSetChanged();
    }


    private void load(List<NotificationBean> list){
        notificationAdapter.addAll(list);
    }

    private List<NotificationBean> testUtil(){
        List<NotificationBean> list=new LinkedList<>();
        for(int i=0;i<=10;i++){
            list.add(new NotificationBean("title"+i, "emmmmmmm", "emmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"));
        }
        return list;
    }
}
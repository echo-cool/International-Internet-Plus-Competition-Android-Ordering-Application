package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class activity_notification extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<NotificationBean> dataBeanList;
    private NotificationBean dataBean;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        initData();

        mAdapter.setmOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                final String[] items = {"删除"};
                final NotificationBean notificationBean = dataBeanList.get( position );
                android.app.AlertDialog.Builder listDialog = new android.app.AlertDialog.Builder(activity_notification.this);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            mAdapter.removeData(position);
                        }
                    }
                });
                listDialog.show();
            }
        });

    }

    /**
     * 模拟数据
     */
    private void initData(){
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            dataBean = new NotificationBean();
            dataBean.setID(i+"");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("Message --"+i);
            dataBean.setParentRightTxt("Time--"+i);
            dataBean.setChildLeftTxt("Details--"+i);
            dataBean.setChildRightTxt("Content-"+i);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }


    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this,dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

}
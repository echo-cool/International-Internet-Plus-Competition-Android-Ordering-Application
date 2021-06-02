package com.example.restaurantclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.beans.OrderBean;
import com.example.restaurantclient.adapters.OrderAdapter;
import com.example.restaurantclient.ui.login.LoginActivity;

import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerOrder);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        orderAdapter=new OrderAdapter(this,new LinkedList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        orderAdapter.setList(testUtil());
    }

    public void login(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void load(){
        //TODO：按时间顺序拉取所有该账号的isEnded=false的订单信息，成功后调用orderAdapter的setList函数，
        //title随便取，info写请求人手机号这种格式139xxxx1600，content格式大概如下
        //地点：location\n
        //总价：price\n
        //内容：猪头x1，烧鸭饭x1，招牌鸡肉饭x1
    }

    public List<OrderBean> testUtil(){
        List<OrderBean> list=new LinkedList<>();
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
        list.get(3).isConfirmed=true;
        return list;
    }

}
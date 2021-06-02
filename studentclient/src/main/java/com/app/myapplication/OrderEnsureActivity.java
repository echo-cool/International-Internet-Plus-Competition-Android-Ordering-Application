package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.beans.FoodBean;
import com.app.beans.MerchantBean;
import com.app.myapplication.Home.NavigationActivity;
import com.app.myapplication.adapters.FoodSimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OrderEnsureActivity extends AppCompatActivity {
    private Context mContext;
    private ArrayList<FoodBean> foodBeans;
    private MerchantBean merchantBean;
    private FoodSimpleAdapter foodSimpleAdapter;
    private double price=0;
    private double packet_price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_order_ensure);
        Intent intent=getIntent();
        foodBeans= (ArrayList<FoodBean>) intent.getSerializableExtra("Foods");
        merchantBean= (MerchantBean) intent.getSerializableExtra("Shop");
        System.out.println("++++++++++++++++++++++++++++++++"+merchantBean.merchantOBJ);
        RecyclerView recyclerView=((RecyclerView)findViewById(R.id.simple_food_list));
        foodSimpleAdapter=new FoodSimpleAdapter(foodBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodSimpleAdapter);

        ((TextView)findViewById(R.id.canteen_name)).setText(merchantBean.mctName);
        for(FoodBean foodBean:foodBeans){
            price+=foodBean.foodPrice*foodBean.selectCount;
            packet_price+=0.5;
        }
        ((TextView)findViewById(R.id.text_price)).setText("¥"+(price+packet_price));
        ((TextView)findViewById(R.id.text_packet_price)).setText("另需打包费用：¥"+(price+packet_price));

    }

    public void button_pay(View view){
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        saveOrder(foodBeans,merchantBean);
    }

    public void saveOrder(ArrayList<FoodBean> foodBeans, MerchantBean merchantBean){
        HashMap<String, Object> foods = new HashMap<>();
        for (FoodBean foodBean: foodBeans
        ) {
            HashMap<String, Object> food = new HashMap<>();
            food.put("id", foodBean.cuisineOBJ.getObjectId());
            food.put("name", foodBean.foodName);
            food.put("selectCount", foodBean.selectCount);
            foods.put(foodBean.cuisineOBJ.getObjectId(), food);
        }
        // 构建对象
        AVObject todo = new AVObject("Order");
        // 为属性赋值
        todo.put("username", AVUser.getCurrentUser().getUsername());
        todo.put("user", AVUser.getCurrentUser());
        todo.put("merchantName", merchantBean.mctName);
        todo.put("merchant", merchantBean.merchantOBJ);
        todo.put("foods", foods);
        todo.put("Total_Price", price);
        // 将对象保存到云端
        todo.saveInBackground().subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVObject todo) {
                // 成功保存之后，执行其他逻辑
                System.out.println("保存成功。objectId：" + todo.getObjectId());
            }
            public void onError(Throwable throwable) {
                // 异常处理
                findViewById(R.id.loading).setVisibility(View.GONE);
                System.out.println(throwable.toString());
            }
            public void onComplete() {
                Intent intent=new Intent(mContext, NavigationActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
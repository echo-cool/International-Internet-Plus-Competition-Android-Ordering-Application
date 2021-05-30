package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.beans.FoodBean;
import com.app.beans.MerchantBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderEnsureActivity extends AppCompatActivity {
    private ArrayList<FoodBean> foodBeans;
    private MerchantBean merchantBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ensure);
        Intent intent=getIntent();
        foodBeans= (ArrayList<FoodBean>) intent.getSerializableExtra("Foods");
        merchantBean= (MerchantBean) intent.getSerializableExtra("Shop");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+foodBeans.size());
        for(FoodBean foodBean:foodBeans){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+foodBean.foodName+"||||"+foodBean.selectCount);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+merchantBean.mctName);
    }

    public void uploadSettlement(String shopId,ArrayList<FoodBean> foodBeans){

    }
}
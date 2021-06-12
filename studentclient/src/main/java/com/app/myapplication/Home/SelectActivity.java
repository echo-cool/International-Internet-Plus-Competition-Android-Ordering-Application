package com.app.myapplication.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.app.beans.DevicesBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.DevicesAdapter;
import com.app.myapplication.adapters.MerchantAdapter;

import java.util.LinkedList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    DevicesAdapter dvAdapter;
    RecyclerView dvView;

    androidx.appcompat.widget.Toolbar back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        dvView = findViewById(R.id.devices_view);
        dvView.setLayoutManager(new LinearLayoutManager(this));
        back = findViewById(R.id.details_toolbar);
        setSupportActionBar(back);
        back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        dvAdapter = new DevicesAdapter(new LinkedList<>());
        dvView.setAdapter(dvAdapter);
//        back = findViewById(R.id.details_toolbar)
//        setSupportActionBar(back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        dvAdapter.addData(new DevicesBean("1","四教",123));
        dvAdapter.addData(new DevicesBean("1","一教",123));
        dvAdapter.addData(new DevicesBean("1","三教",123));
        dvAdapter.addData(new DevicesBean("1","图书馆",123));

    }


}
package com.app.myapplication.merchant;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.beans.MerchantBean;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;
import com.app.myapplication.adapters.MerchantAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MerchantActivity extends AppCompatActivity {

    MerchantAdapter mctAdapter;
    RecyclerView mctView;
    List<MerchantBean> temp = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        mctView = findViewById(R.id.merchant_recycle);
        mctView.setLayoutManager(new LinearLayoutManager(this));
        mctView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 15;
            }
        });

        test();

        mctAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MerchantActivity.this , ShopActivity.class);
                Bundle b = new Bundle();
                MerchantBean  mb=  mctAdapter.getList().get(position);
                b.putString("name",mb.mctName);
                b.putString("id",mb.id);
                i.putExtras(b);
                startActivity(i);
            }
        });

        mctAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载请求，结束
                        //mctAdapter.loadMoreEnd();
                        //加载请求，加载
                        mctAdapter.addData(temp);
                        mctAdapter.loadMoreComplete();
                        //加载失败
                        //mctAdapter.loadMoreFail();
                    }
                }, 3000);
            }
        }, mctView);
    }

    public void load(List<MerchantBean> mct){
        mctAdapter = new MerchantAdapter(mct);
        mctView.setAdapter(mctAdapter);
    }



    private void test(){
        List<MerchantBean> mct =new LinkedList<>();
        mct.add(new MerchantBean("111","好吃不贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mct.add(new MerchantBean("222","好贵"));
        mctAdapter = new MerchantAdapter(mct);
        mctView.setAdapter(mctAdapter);
        temp.add(new MerchantBean("111","好吃不贵"));
        temp.add(new MerchantBean("111","好吃不贵"));
        temp.add(new MerchantBean("111","好吃不贵"));
        temp.add(new MerchantBean("111","好吃不贵"));
        temp.add(new MerchantBean("111","好吃不贵"));
        temp.add(new MerchantBean("111","好吃不贵"));
    }
}
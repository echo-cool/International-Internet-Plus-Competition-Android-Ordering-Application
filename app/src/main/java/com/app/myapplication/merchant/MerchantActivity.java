package com.app.myapplication.merchant;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.beans.FoodBean;
import com.app.beans.MerchantBean;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;
import com.app.myapplication.adapters.MerchantAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MerchantActivity extends AppCompatActivity {

    MerchantAdapter mctAdapter;
    RecyclerView mctView;
    Queue<MerchantBean> temp = new LinkedList<>();
    Boolean isFin = false;


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


        mctAdapter = new MerchantAdapter(new LinkedList<>());
        mctView.setAdapter(mctAdapter);

        mctAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MerchantActivity.this , ShopActivity.class);
                Bundle b = new Bundle();
                MerchantBean  mb=  mctAdapter.getList().get(position);
                i.putExtra("shop",mctAdapter.getList().get(position));
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
                        if(!isFin) {
                            if (temp == null) {
                                mctAdapter.loadMoreFail();
                            } else {
                                List<MerchantBean> merchants =new LinkedList<>();
                                for(int i=0;i<10;i++){
                                    if(!temp.isEmpty()){
                                    merchants.add(temp.poll());}
                                }
                                mctAdapter.addData(merchants);
                                mctAdapter.loadMoreComplete();
                            }
                        }else {
                            if(temp.isEmpty()){
                                mctAdapter.loadMoreEnd();
                            }else {
                                List<MerchantBean> merchants =new LinkedList<>();
                                for(int i=0;i<10;i++){
                                    if(!temp.isEmpty()){
                                        merchants.add(temp.poll());}
                                }
                                mctAdapter.addData(merchants);
                                mctAdapter.loadMoreComplete();
                            }

                        }
                    }
                }, 3000);
            }
        }, mctView);
        loadMerchantList("北京工业大学");
    }

    public void loadMerchantList(String school){
        AVQuery<AVObject> query = new AVQuery<>("Restaurant");
        //食堂是否在本校
        query.whereEqualTo("Location",school);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> list) {
                List<MerchantBean> merchantList = new LinkedList<>();
                //public MerchantBean(String id, String name)
                for(AVObject avObject: list){
                    merchantList.add(new MerchantBean(avObject.getObjectId(),avObject.getString("Name")));
                }
                load(merchantList);
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });
    }

    public void load(List<MerchantBean> mct){
        mctAdapter.addData(mct);
    }
    public void loadMore(List<MerchantBean> mct){
        //加入更多数据，list末尾为null判断所有数据加载完成
        if(mct. get(mct.size()-1) ==null){
            isFin = true;
            mct.remove(mct.size()-1);
            for(MerchantBean mb:mct){
                temp.offer(mb);
            }
        }else {
            for (MerchantBean mb : mct) {
                temp.offer(mb);
            }
        }
    }


//        mct.add(new MerchantBean("111","好吃不贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        mct.add(new MerchantBean("222","好贵"));
//        load(mct);
//        List<MerchantBean> mct1 =new LinkedList<>();
//        for(int i=0;i<35;i++){
//            mct1.add(new MerchantBean("111","好吃不贵"));
//        }
//
//
//        loadMore(mct1);
//        List<MerchantBean> mct2 =new LinkedList<>();
//        mct2.add(new MerchantBean("333","怎么能这么难吃"));
//        mct2.add(null);
//        loadMore(mct2);
    }

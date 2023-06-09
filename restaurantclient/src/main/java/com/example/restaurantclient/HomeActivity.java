package com.example.restaurantclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.Models.RequestListener;
import com.example.beans.OrderBean;
import com.example.restaurantclient.adapters.OrderAdapter;
import com.example.restaurantclient.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVException;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import cn.leancloud.json.JSONObject;
import cn.leancloud.livequery.AVLiveQuery;
import cn.leancloud.livequery.AVLiveQueryEventHandler;
import cn.leancloud.livequery.AVLiveQuerySubscribeCallback;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    OrderAdapter orderAdapter;
    AVLiveQuery liveQuery;
    RequestListener requestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerOrder);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        orderAdapter=new OrderAdapter(this,new LinkedList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        initLiveQuery();
        requestListener=new RequestListener() {
            @Override
            public void success(List data) {
                orderAdapter.setList(data);
            }

            @Override
            public void processing(int now, int size, List data) {

            }

            @Override
            public void failed(String reason) {

            }
        };


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLiveQuery();
//                orderAdapter.notifyDataSetChanged();
            }
        });

//        orderAdapter.setList(testUtil());

    }

    public void login(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void initLiveQuery(){
        AVQuery<AVObject> query = new AVQuery<>("Order");
        query.whereEqualTo("isEnded", false);
        AVObject user = AVObject.createWithoutData("_User", AVUser.getCurrentUser() == null ? "60afa0a3dd770475f266d21f": AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("Owner",user);

        liveQuery = AVLiveQuery.initWithQuery(query);
        liveQuery.setEventHandler(new AVLiveQueryEventHandler() {
            @Override
            public void onObjectCreated(AVObject newTodo) {
                load(new RequestListener() {
                    @Override
                    public void success(List data) {
                        orderAdapter.setList(data);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void processing(int now, int size, List data) {

                    }

                    @Override
                    public void failed(String reason) {

                    }
                });
            }

            @Override
            public void onObjectUpdated(AVObject avObject, List<String> updateKeyList) {
                load(new RequestListener() {
                    @Override
                    public void success(List data) {
                        orderAdapter.setList(data);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void processing(int now, int size, List data) {

                    }

                    @Override
                    public void failed(String reason) {

                    }
                });

            }

            @Override
            public void onObjectEnter(AVObject avObject, List<String> updateKeyList) {
                load(new RequestListener() {
                    @Override
                    public void success(List data) {
                        orderAdapter.setList(data);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void processing(int now, int size, List data) {

                    }

                    @Override
                    public void failed(String reason) {

                    }
                });

            }

            @Override
            public void onObjectLeave(AVObject avObject, List<String> updateKeyList) {
                load(new RequestListener() {
                    @Override
                    public void success(List data) {
                        orderAdapter.setList(data);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void processing(int now, int size, List data) {

                    }

                    @Override
                    public void failed(String reason) {

                    }
                });

            }

            @Override
            public void onObjectDeleted(String objectId) {
                load(new RequestListener() {
                    @Override
                    public void success(List data) {
                        orderAdapter.setList(data);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void processing(int now, int size, List data) {

                    }

                    @Override
                    public void failed(String reason) {

                    }
                });

            }
        });
        liveQuery.subscribeInBackground(new AVLiveQuerySubscribeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 订阅成功
                    load(requestListener);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    public void logOut(View view){
        AVUser.logOut();
        initLiveQuery();
        this.onResume();
    }

    public void load(RequestListener listener){
        //TODO：按时间顺序拉取所有该账号的isEnded=false的订单信息，成功后调用orderAdapter的setList函数，
        //title随便取，info写请求人手机号这种格式139xxxx1600，content格式大概如下
        //地点：location\n
        //总价：price\n
        //内容：猪头x1，烧鸭饭x1，招牌鸡肉饭x1
        AVQuery<AVObject> query = new AVQuery<>("Order");
        query.whereEqualTo("isEnded", false);
        query.orderByDescending("updatedAt");
        AVObject user = AVObject.createWithoutData("_User", AVUser.getCurrentUser() == null ? "60afa0a3dd770475f266d21f": AVUser.getCurrentUser().getObjectId());

        //System.out.println(AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("Owner",user);

        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> data) {
                LinkedList<OrderBean> result = new LinkedList<>();
                for (AVObject res: data) {
                    String title = res.getObjectId();
                    String info = res.getString("username");
                    String location = res.getString("Location");
                    JSONObject foods = res.getJSONObject("foods");
                    Number price = res.getNumber("TotalPrice");
                    String finalcontent = "";
                    for (String i: foods.keySet()){
                        JSONObject s = foods.getJSONObject(i);
                        String temp = s.getString("name")+"x"+s.getIntValue("selectCount")+" ";
                        finalcontent+=temp;
                    }

                    OrderBean orderBean = new OrderBean(title,title,info, "总价：" + price.toString() + "\n收货地点：" + location + "\n订单详情：" + finalcontent,res.getAVObject("user"));


                    orderBean.isConfirmed=res.getBoolean("isConfirmed");
                    orderBean.isEnded=res.getBoolean("isEnded");
                    result.add(orderBean);
                }
                listener.success(result);
                orderAdapter.setList(result);

            }
            public void onError(Throwable throwable) {
                listener.failed(throwable.toString());
                System.out.println("-----------------------------"+throwable.toString());
            }
            public void onComplete() {
                orderAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    public List<OrderBean> testUtil(){
//        List<OrderBean> list=new LinkedList<>();
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.add(new OrderBean("1","asfsda","sdgsdgs","sdfgdsfhsfd\nsfdsgffdgsfd\nsfdgsfdgsdf\nsgfdsgdfsgsdgdfgsdfgfdsgdsgdfgsdfgsdfg"));
//        list.get(3).isConfirmed=true;
//        return list;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AVUser.getCurrentUser()==null){
            ((TextView)findViewById(R.id.User_title)).setText("未登录");
        }else{
            ((TextView)findViewById(R.id.User_title)).setText("欢迎"+AVUser.getCurrentUser().getUsername());
        }
    }
}
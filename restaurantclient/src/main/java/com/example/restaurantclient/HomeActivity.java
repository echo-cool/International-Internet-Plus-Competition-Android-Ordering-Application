package com.example.restaurantclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.beans.OrderBean;
import com.example.restaurantclient.adapters.OrderAdapter;
import com.example.restaurantclient.ui.login.LoginActivity;

import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVException;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import cn.leancloud.livequery.AVLiveQuery;
import cn.leancloud.livequery.AVLiveQueryEventHandler;
import cn.leancloud.livequery.AVLiveQuerySubscribeCallback;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    public void initLiveQuery(){
        AVQuery<AVObject> query = new AVQuery<>("Order");
        query.whereEqualTo("isEnded", false);
        AVLiveQuery liveQuery = AVLiveQuery.initWithQuery(query);
        liveQuery.setEventHandler(new AVLiveQueryEventHandler() {
            @Override
            public void onObjectCreated(AVObject newTodo) {
                System.out.println(newTodo);
            }

            @Override
            public void onObjectUpdated(AVObject avObject, List<String> updateKeyList) {

            }

            @Override
            public void onObjectEnter(AVObject avObject, List<String> updateKeyList) {

            }

            @Override
            public void onObjectLeave(AVObject avObject, List<String> updateKeyList) {

            }

            @Override
            public void onObjectDeleted(String objectId) {

            }
        });
        liveQuery.subscribeInBackground(new AVLiveQuerySubscribeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 订阅成功
                }
            }
        });
    }

    public void logOut(View view){
        AVUser.logOut();
        this.onResume();
    }

    public void load(){
        //TODO：按时间顺序拉取所有该账号的isEnded=false的订单信息，成功后调用orderAdapter的setList函数，
        //title随便取，info写请求人手机号这种格式139xxxx1600，content格式大概如下
        //地点：location\n
        //总价：price\n
        //内容：猪头x1，烧鸭饭x1，招牌鸡肉饭x1
        AVQuery<AVObject> query = new AVQuery<>("Order");
        query.whereEqualTo("isEnded", false);
        query.include("Restaurant");
        query.include("user");
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> data) {
                for (AVObject res: data
                     ) {
                    String title = res.getObjectId();
                    String info = res.getAVObject("user").getString("mobilePhoneNumber");
                    String location = res.getString("Location");
                    Number price = res.getNumber("TotalPrice");
                }
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });
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
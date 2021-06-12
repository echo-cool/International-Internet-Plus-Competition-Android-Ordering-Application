package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.beans.FoodBean;
import com.app.beans.MerchantBean;
import com.app.myapplication.Home.NavigationActivity;
import com.app.myapplication.adapters.FoodSimpleAdapter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OrderEnsureActivity extends AppCompatActivity {

    public static List<FoodBean> foodBeanList;

    private long minTime;
    private long selectTime;
    private Context mContext;
    private ArrayList<FoodBean> foodBeans;
    private MerchantBean merchantBean;
    private FoodSimpleAdapter foodSimpleAdapter;
    private double price=0;
    private double packet_price=0;
    private AVObject merchantOBJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("======================================0");
        super.onCreate(savedInstanceState);

        mContext=this;
        setContentView(R.layout.activity_order_ensure);
        Intent intent=getIntent();
        minTime=System.currentTimeMillis()+1000*1800;
        selectTime=minTime;
        ((TextView)findViewById(R.id.textTime)).setText(new SimpleDateFormat("HH:mm").format(minTime));
        foodBeans=new ArrayList<>();
        foodBeans.addAll(foodBeanList);
        foodBeanList=null;
        System.out.println("======================================1");
        merchantBean= (MerchantBean) intent.getSerializableExtra("Shop");
        this.merchantOBJ = merchantBean.getMerchantOBJ();
        RecyclerView recyclerView=((RecyclerView)findViewById(R.id.simple_food_list));
        foodSimpleAdapter=new FoodSimpleAdapter(foodBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodSimpleAdapter);
        ((TextView)findViewById(R.id.canteen_name)).setText(merchantBean.mctName);
        System.out.println("======================================2");
        for(FoodBean foodBean:foodBeans){
            price+=foodBean.foodPrice*foodBean.selectCount;
            packet_price+=1;
        }
        System.out.println("======================================3");
        ((TextView)findViewById(R.id.text_price)).setText("¥"+(price+packet_price));
        ((TextView)findViewById(R.id.text_packet_price)).setText("另需打包费用：¥"+(packet_price));
        System.out.println("======================================4");

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
            foods.put(foodBean.getId(), food);
        }
        // 构建对象
        AVObject todo = new AVObject("Order");
        // 为属性赋值

        String location = "堂食";
        todo.put("Location", location);
        todo.put("username", AVUser.getCurrentUser().getUsername());
        todo.put("user", AVUser.getCurrentUser());
        todo.put("Restaurant", this.merchantOBJ);
        todo.put("foods", foods);
        todo.put("TotalPrice", price);
        todo.put("Owner",merchantOBJ.getAVObject("Owner"));
        todo.put("InstallationID", AVInstallation.getCurrentInstallation().getInstallationId());
        AVUser user = AVUser.getCurrentUser();
        if(user != null){
            String InstallationID = AVInstallation.getCurrentInstallation().getInstallationId();
            user.put("InstallationID", InstallationID);
            user.saveInBackground();
        }
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    public void chooseTime(View view){
        TimePickerDialog timeDialog=new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        ((TextView)findViewById(R.id.textTime)).setText(new SimpleDateFormat("HH:mm").format(millseconds));
                        selectTime=millseconds;
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确认")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(minTime)
                .setMaxMillseconds(minTime + 3600*1000*6)
                .setCurrentMillseconds(selectTime)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.HOURS_MINS)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(14)
                .build();
        timeDialog.show(getSupportFragmentManager(),"dialog");
    }
}
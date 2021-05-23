package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import cn.leancloud.AVOSCloud;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        test();

        // 提供 this、App ID、App Key、Server Host 作为参数
        // 注意这里千万不要调用 cn.leancloud.core.AVOSCloud 的 initialize 方法，否则会出现 NetworkOnMainThread 等错误。
        //AVOSCloud.initialize(this, "aXtS1Ar4i3CRKa6oc72bp5Ro-gzGzoHsz", "KD4f0t7AfuBKztLIC8GeATaR", "https://axts1ar4.lc-cn-n1-shared.com");
    }

    public void test(){
        Activity activity=this;
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.runOnUiThread(()->{
                activity.setContentView(R.layout.activity_main);
            });
        }).start();
    }
}

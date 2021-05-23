package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        test();

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

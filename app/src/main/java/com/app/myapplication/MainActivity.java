package com.app.myapplication;

import android.app.Activity;
import android.os.Bundle;

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
                activity.setContentView(R.layout.view_home_navigation);
            });
        }).start();
    }
}

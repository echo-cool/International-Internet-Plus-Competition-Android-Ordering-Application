package com.app.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.app.myapplication.Home.NavigationActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();

//        //继承AppCompatActivity去掉标题栏
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        //设置成全屏，这种设置会出现短暂的状态栏，
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
//                WindowManager.LayoutParams. FLAG_FULLSCREEN);
////        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Activity _this=this;
        new Handler().postDelayed(()->{
            _this.startActivity(new Intent(this, NavigationActivity.class));
            _this.finish();
        },0);
    }

    private void setWindow(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

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

package com.app.myapplication.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.myapplication.R;
import com.app.myapplication.adapters.NavigationFragmentAdapter;
import com.gauravk.bubblenavigation.IBubbleNavigation;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class NavigationActivity extends AppCompatActivity {
    List<Fragment> fragments = new ArrayList<Fragment>();
    Fragment user,home,forum,notification;
    ViewPager2 vp2;
    IBubbleNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initPager();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setWindow(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }
    private void changeTab(int position) {
        //滑动页面 改变底部导航栏
        switch(position){
            case 0:
                bottomNavigation.setCurrentActiveItem(0);
                break;
            case 1:
                bottomNavigation.setCurrentActiveItem(1);
                break;
            case 2:
                bottomNavigation.setCurrentActiveItem(2);
                break;
            case 3:
                bottomNavigation.setCurrentActiveItem(3);
                break;
        }
    }

    private void initPager() {
        //所有初始化
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                vp2.setCurrentItem(position);
            }
        });
        vp2 = findViewById(R.id.myViewPager);
        //后期修改实例化fragment部分
        user = UserFragment.newInstance("","");
        notification = new NotificationFragment();
        forum = new ForumFragment();
        home = new HomepageFragment();
        fragments.add(home);
        fragments.add(notification);
        fragments.add(forum);
        fragments.add(user);
        NavigationFragmentAdapter viewPageAdapter = new NavigationFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        vp2.setAdapter(viewPageAdapter);
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }



}
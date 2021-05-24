package com.app;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 提供 this、App ID、App Key、Server Host 作为参数
        // 注意这里千万不要调用 cn.leancloud.core.AVOSCloud 的 initialize 方法，否则会出现 NetworkOnMainThread 等错误。
        // 在 AVOSCloud.initialize() 之前调用
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);

        AVOSCloud.initialize(this, "aXtS1Ar4i3CRKa6oc72bp5Ro-gzGzoHsz", "KD4f0t7AfuBKztLIC8GeATaR", "https://axts1ar4.lc-cn-n1-shared.com");

    }
}


package com.app;

import android.app.Application;
import android.app.NativeActivity;

import com.app.Models.Cuisine;
import com.app.Models.Cuisine_Type;
import com.app.myapplication.Home.NavigationActivity;
import com.app.myapplication.MainActivity;
import com.app.myapplication.ShopActivity;
import com.app.utils.TestUtils;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import cn.leancloud.push.PushService;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 提供 this、App ID、App Key、Server Host 作为参数
        // 注意这里千万不要调用 cn.leancloud.core.AVOSCloud 的 initialize 方法，否则会出现 NetworkOnMainThread 等错误。
        // 在 AVOSCloud.initialize() 之前调用
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
        AVOSCloud.initialize(this, "aXtS1Ar4i3CRKa6oc72bp5Ro-gzGzoHsz", "KD4f0t7AfuBKztLIC8GeATaR", "https://axts1ar4.lc-cn-n1-shared.com");
        AVObject.registerSubclass(Cuisine_Type.class);
        AVObject.registerSubclass(Cuisine.class);
        //TestUtils.test1("60aa42ef6d8bee18f6112967");
        PushService.setDefaultChannelId(this, "1");
        AVInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(AVObject avObject) {
                // 关联 installationId 到用户表等操作。
                String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                AVObject user = AVUser.getCurrentUser();
                if(user != null) {
                    user.put("InstallationID", installationId);
                    user.saveInBackground().subscribe(new Observer<AVObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull AVObject avObject) {
                            System.out.println("user InstallationID 保存成功");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
                System.out.println("保存成功：" + installationId );
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("保存失败，错误信息：" + e.getMessage());
            }
            @Override
            public void onComplete() {
            }
        });
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, NavigationActivity.class);



    }

}


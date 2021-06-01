package com.app.myapplication.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserInfoView extends LinearLayout {
    public UserInfoView(@NonNull @NotNull Context context) {
        super(context);
        inflate(context, R.layout.view_user_info,this);
    }

    public UserInfoView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_user_info, this);
    }

    public void setUserImage(Drawable d){
        findViewById(R.id.user_avatar).setBackground(d);
    }
    public void setUserName(String s){
        ((TextView)findViewById(R.id.user_username)).setText(s);
    }
    public void setUserProfile(String s){
        ((TextView)findViewById(R.id.user_profile)).setText(s);
    }


}

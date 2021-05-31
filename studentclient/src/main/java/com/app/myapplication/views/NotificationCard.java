package com.app.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

public class NotificationCard extends ConstraintLayout {
    private Context mContext;
    public NotificationCard(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_notification, this);
        this.mContext=context;
        findViewById(R.id.notification_card).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                open();
            }
        });
    }

    public void open(){
        ConstraintLayout view=findViewById(R.id.hidden_information);
        view.setVisibility(VISIBLE);
    }
}

package com.example.restaurantclient.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.restaurantclient.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class OrderView extends ConstraintLayout {
    public OrderView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_notification,this);
    }

    public void setTitle(String title){
        ((TextView)findViewById(R.id.text_title)).setText(title);
    }

    public void setInfo(String info){
        ((TextView)findViewById(R.id.text_info)).setText(info);
    }

    public void setContent(String content){
        ((TextView)findViewById(R.id.text_content)).setText(content);
    }

    public void confirmed(){
        findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.bluegreen));
        ((Button)findViewById(R.id.button)).setEnabled(false);
    }

}

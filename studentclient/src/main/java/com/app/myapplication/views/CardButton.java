package com.app.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

public class CardButton extends ConstraintLayout {
    public CardButton(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_card_button,this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CardButton);
        Drawable icon=array.getDrawable(R.styleable.CardButton_icon);
        Drawable background=array.getDrawable(R.styleable.CardButton_background);
        setIcon(icon);
        setBackground(background);
        array.recycle();
    }

    public void setIcon(Drawable drawable){
        ((ImageView)findViewById(R.id.icon)).setImageDrawable(drawable);
    }

    public void setIcon(int resId){
        ((ImageView)findViewById(R.id.icon)).setImageResource(resId);
    }

    public void setBackground(Drawable drawable){
        findViewById(R.id.card).setBackground(drawable);
    }

    public void setBackgroundColor(int color){
        findViewById(R.id.card).setBackgroundColor(color);
    }


}

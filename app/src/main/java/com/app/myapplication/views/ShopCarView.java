package com.app.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class ShopCarView extends ConstraintLayout {

    private Context mContext;

    public ShopCarView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_car,this);
        mContext=context;
    }

    public void setPrice(double price){
        if(price!=0)
            ((TextView)findViewById(R.id.textView7)).setText(new DecimalFormat("0.0").format(price));
        else
            ((TextView)findViewById(R.id.textView7)).setText("0");
    }
}

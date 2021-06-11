package com.app.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class ShopCarView extends ConstraintLayout {

    private Context mContext;
    private OnClickListener onClickListener;

    public ShopCarView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_car,this);
        mContext=context;
        findViewById(R.id.textView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener!=null)
                    onClickListener.onSettleClick();
            }
        });
        findViewById(R.id.floatingActionButton2).setOnClickListener((View view)->{
            onClickListener.onShopCarClick();
        });
    }

    public void setPrice(double price){
        if(price!=0)
            ((TextView)findViewById(R.id.textView7)).setText(new DecimalFormat("0.0").format(price));
        else
            ((TextView)findViewById(R.id.textView7)).setText("0");
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public interface OnClickListener{
        void onSettleClick();

        void onShopCarClick();
    }
}

package com.app.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.myapplication.R;

import org.jetbrains.annotations.NotNull;

public class MarginView extends ConstraintLayout {

    public MarginView(@NonNull @NotNull Context context) {
        super(context);
        inflate(context, R.layout.item_margin,this);
    }
    public MarginView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.item_margin,this);
    }
}

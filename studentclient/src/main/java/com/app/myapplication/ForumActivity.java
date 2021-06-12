package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.utils.ViewUtils;

public class ForumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtils.setStatusBarColor(this,getColor(R.color.crimson));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
    }
}
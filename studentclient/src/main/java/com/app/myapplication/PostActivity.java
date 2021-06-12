package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.utils.ViewUtils;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtils.setStatusBarColor(this,getColor(R.color.forum_page));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }
}
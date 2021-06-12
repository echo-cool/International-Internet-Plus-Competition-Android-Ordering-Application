package com.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.app.utils.ViewUtils;

import java.util.Timer;
import java.util.TimerTask;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtils.setStatusBarColor(this,getColor(R.color.forum_page));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        openKeyBoard(findViewById(R.id.editTextTextPersonName2));
    }

    public void openKeyBoard(EditText editText){

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }
                       },
                200);


    }
}
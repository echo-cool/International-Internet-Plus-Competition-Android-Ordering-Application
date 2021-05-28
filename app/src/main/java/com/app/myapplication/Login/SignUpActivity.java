package com.app.myapplication.Login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.Models.SignUpListener;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;

import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SignUpActivity extends Activity implements View.OnClickListener {
    private View mInputLayout;
    private LinearLayout mName, mPsw, mPswRep;
    private TextView mBtnSignUp, retLog;
    private float mWidth, mHeight;

    private View progress;
    private EditText pswText,pswRepText,nameText;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mInputLayout = findViewById(R.id.input_sign);
        mName = findViewById(R.id.input_layout_name_sign);
        mPsw= findViewById(R.id.input_layout_psw_sign);
        mBtnSignUp = findViewById(R.id.main_btn_sign);
        mPswRep = findViewById(R.id.input_layout_pasRep);
        progress = findViewById(R.id.layout_progress_sign);
        pswText = findViewById(R.id.psw_sign);
        pswRepText = findViewById(R.id.psw_sign_rep);
        mBtnSignUp.setOnClickListener(this);
        nameText = findViewById(R.id.name_sign);
        //retLog=findViewById(R.id.return_login);
        inflater = LayoutInflater.from(this);
        retLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void inputAnimator(final View view, float w, float h) {



        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recovery();
                    }
                }, 2000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }


    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        mPswRep.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    public void SignUp(String username, String password, SignUpListener listener){
        AVUser user = new AVUser();

// 等同于 user.put("username", "Tom")
        user.setUsername(username);
        user.setPassword(password);


        user.signUpInBackground().subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVUser user) {
                // 注册成功
                System.out.println("注册成功。objectId：" + user.getObjectId());
                listener.SignUpSuccess(user);
            }
            public void onError(Throwable throwable) {
                // 注册失败（通常是因为用户名已被使用）
                listener.SignUpFailed(throwable.toString());
            }
            public void onComplete() {}
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        SignUpActivity this_ = this;
        if(TextUtils.isEmpty(nameText.getText())){
            nameText.setHint("用户名不能为空");
        }
        else if(TextUtils.isEmpty(pswText.getText())){
            pswText.setHint("新密码不能为空");
        }
        else if(TextUtils.isEmpty(pswRepText.getText())){
            pswRepText.setHint("请确认密码");
        }
        else if(!matchFormat(pswText.getText().toString())){
            pswText.setText("");
            pswText.setHint("至少8位，含大小写字母数字、字符");
        }
        else if(!pswText.getText().toString().equals(pswRepText.getText().toString())){
            pswRepText.setText("");
            pswRepText.setHint("两次输入不相同");
        }
        else{
            mWidth = mBtnSignUp.getMeasuredWidth();
            mHeight = mBtnSignUp.getMeasuredHeight();
            mName.setVisibility(View.INVISIBLE);
            mPsw.setVisibility(View.INVISIBLE);
            mPswRep.setVisibility(View.INVISIBLE);
            inputAnimator(mInputLayout, mWidth, mHeight);
            SignUp(nameText.getText().toString(), pswRepText.getText().toString(), new SignUpListener() {
                @Override
                public void SignUpSuccess(AVUser avUser) {
                    System.out.println("SignUpSuccess");
                    AlertDialog alertDialog;
                    AlertDialog.Builder alertDialog_builder=new AlertDialog.Builder(this_);
                    alertDialog_builder.setTitle("注册成功！");
                    alertDialog_builder.setMessage("是否进入登陆页面？");
                    alertDialog_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(this_, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    alertDialog_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog=alertDialog_builder.create();
                    alertDialog.show();
                }

                @Override
                public void SignUpFailed(String reason) {
                    System.out.println("SignUpFailed");
                    AlertDialog alertDialog;
                    AlertDialog.Builder alertDialog_builder=new AlertDialog.Builder(this_);
                    alertDialog_builder.setTitle("注册失败！");
                    alertDialog_builder.setMessage(reason);
                    alertDialog_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog=alertDialog_builder.create();
                    alertDialog.show();
                }
            });

        }
    }

    private boolean matchFormat(String psw){
        final String PW_PATTERN ="^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        if(psw.matches(PW_PATTERN)){
            return true;
        }else
            return false;
    }


}
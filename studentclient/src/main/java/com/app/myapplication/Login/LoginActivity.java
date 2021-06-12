package com.app.myapplication.Login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.app.Models.LoginListener;
import com.app.myapplication.Home.NavigationActivity;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import cn.leancloud.push.PushService;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView mBtnLogin,goSign;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mContext=this;

        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_username);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_password);
        goSign= findViewById(R.id.go_signup);
        mBtnLogin.setOnClickListener(this);

        goSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(i);
            }
        });
        AVUser currentUser = AVUser.getCurrentUser();
//        if (currentUser != null) {
//            // 跳到首页
//            System.out.println("已经登陆成功。");
//            Intent intent = new Intent(this, ShopActivity.class);
//            startActivity(intent);
//        } else {
//            // 显示注册或登录页面
//            // pass
//        }
    }
    public void login(String username, String password, LoginListener listener){
        AVUser.logIn(username, password).subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVUser user) {
                // 登录成功
                listener.LoginSuccess(user);
            }
            public void onError(Throwable throwable) {
                // 登录失败（可能是密码错误）
                listener.LoginFailed(throwable.toString());
            }
            public void onComplete() {
                ((Activity)mContext).finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        LoginActivity this_ = this;
        EditText usernameView = findViewById(R.id.input_layout).findViewById(R.id.editText_account);
        EditText passwordView = findViewById(R.id.input_layout).findViewById(R.id.editText_password);
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
//        mWidth = mBtnLogin.getMeasuredWidth();
//        mHeight = mBtnLogin.getMeasuredHeight();
//        mName.setVisibility(View.INVISIBLE);
//        mPsw.setVisibility(View.INVISIBLE);
//        inputAnimator(mInputLayout, mWidth, mHeight);
        login(username, password, new LoginListener() {
            @Override
            public void LoginSuccess(AVUser avUser) {
                System.out.println("LoginSuccess ");
                String InstallationID = AVInstallation.getCurrentInstallation().getInstallationId();
                avUser.put("InstallationID", InstallationID);
                avUser.saveInBackground();
                PushService.setDefaultChannelId(this_, "1");
                AVInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<AVObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(AVObject avObject) {
                        // 关联 installationId 到用户表等操作。
                        String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                        AVObject user = AVUser.getCurrentUser();
                        if(user != null) {
                            user.put("InstallationID", installationId);
                            user.saveInBackground().subscribe(new Observer<AVObject>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull AVObject avObject) {
                                    System.out.println("user InstallationID 保存成功");
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                        }
                        System.out.println("保存成功：" + installationId );
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("保存失败，错误信息：" + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
                // 设置默认打开的 Activity
//                PushService.setDefaultPushCallback(this_, NavigationActivity.class);
//
//
//                AlertDialog alertDialog;
//                AlertDialog.Builder alertDialog_builder=new AlertDialog.Builder(this_);
//                alertDialog_builder.setTitle("登陆成功！");
//                alertDialog_builder.setMessage("是否进入点餐页面？");
//                alertDialog_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(this_, ShopActivity.class);
//                        startActivity(intent);
//                    }
//                });
//                alertDialog_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialog=alertDialog_builder.create();
//                alertDialog.show();

            }

            @Override
            public void LoginFailed(String reason) {
                System.out.println("LoginFailed");
                passwordView.setError("登陆失败！");
                AlertDialog alertDialog;
                AlertDialog.Builder alertDialog_builder=new AlertDialog.Builder(this_);
                alertDialog_builder.setTitle("登陆失败！");
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

    /**
     * �ָ���ʼ״̬
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
}
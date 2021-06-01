package com.example.restaurantclient.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.restaurantclient.Models.LoginListener;
import com.example.restaurantclient.data.LoginRepository;
import com.example.restaurantclient.data.Result;
import com.example.restaurantclient.data.model.LoggedInUser;
import com.example.restaurantclient.R;

import cn.leancloud.AVUser;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    public void login(String username, String password, LoginListener listener){
        AVUser.logIn(username, password).subscribe(new io.reactivex.Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVUser user) {
                // 登录成功
                listener.LoginSuccess(user);
            }
            public void onError(Throwable throwable) {
                // 登录失败（可能是密码错误）
                listener.LoginFailed(throwable.toString());
            }
            public void onComplete() {}
        });
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
//        Result<LoggedInUser> result = loginRepository.login(username, password);
        login(username, password, new LoginListener() {
            @Override
            public void LoginSuccess(AVUser avUser) {
                System.out.println("LoginSuccess");
                System.out.println(avUser.getObjectId());
                loginResult.setValue(new LoginResult(new LoggedInUserView(avUser.getUsername())));
            }

            @Override
            public void LoginFailed(String reason) {
                System.out.println("LoginFailed");
                System.out.println(reason);
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
package com.app.Models;

import cn.leancloud.AVUser;

public interface LoginListener {
    void LoginSuccess(AVUser avUser);
    void LoginFailed(String reason);
}

package com.example.Models;

import cn.leancloud.AVUser;

public interface SignUpListener {
    void SignUpSuccess(AVUser avUser);
    void SignUpFailed(String reason);
}

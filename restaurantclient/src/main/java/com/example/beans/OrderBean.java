package com.example.beans;

import cn.leancloud.AVObject;

public class OrderBean {
    public String id;
    public String title;
    public String info;
    public String content;
    public boolean isConfirmed;
    public boolean isEnded;
    public String userObject;

    public OrderBean(String id,String title,String info,String content,AVObject user){
        this.id=id;
        this.title=title;
        this.info=info;
        this.content=content;
        setUserObject(user);
    }

    public void setUserObject(AVObject userObject) {
        this.userObject = userObject.toString();
    }

    public AVObject getUserObject() {
        return AVObject.parseAVObject(userObject);
    }
}

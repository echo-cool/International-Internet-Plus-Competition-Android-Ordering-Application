package com.example.beans;

public class OrderBean {
    public String id;
    public String title;
    public String info;
    public String content;
    public boolean isConfirmed;
    public boolean isEnded;

    public OrderBean(String id,String title,String info,String content){
        this.id=id;
        this.title=title;
        this.info=info;
        this.content=content;
    }

}

package com.app.beans;

public class NotificationBean {
    public String title;
    public String summary;
    public String detail;
    public boolean isExpand=false;

    public NotificationBean(String title, String summary, String detail) {
        this.title = title;
        this.summary = summary;
        this.detail = detail;
    }
}

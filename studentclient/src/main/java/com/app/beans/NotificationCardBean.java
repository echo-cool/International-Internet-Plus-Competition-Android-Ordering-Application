package com.app.beans;

public class NotificationCardBean {
    public String title;
    public String summary;
    public String detail;
    public boolean isExpand=false;

    public NotificationCardBean(String title, String summary, String detail) {
        this.title = title;
        this.summary = summary;
        this.detail = detail;
    }
}

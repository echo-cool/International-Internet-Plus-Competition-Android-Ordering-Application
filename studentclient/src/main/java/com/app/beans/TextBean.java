package com.app.beans;

import org.w3c.dom.Text;

import java.io.Serializable;

public class TextBean implements Serializable {
    public String tag1;
    public String tag2;


    public TextBean (String t1, String t2){
        tag1 = t1;
        tag2 = t2;

    }
}

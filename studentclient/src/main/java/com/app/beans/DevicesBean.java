package com.app.beans;

import java.io.Serializable;

public class DevicesBean implements Serializable {
    public String id;
    public String device_name;
    public int population;

    public DevicesBean(String id, String name, int pop){
        this.id = id;
        device_name = name;
        population = pop;
    }
}

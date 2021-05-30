package com.app.myapplication.adapters;

import android.view.ViewGroup;

public abstract class View {
    public abstract android.view.View getSubView(int position, android.view.View convertView, ViewGroup parent);

    public abstract Object getTag(int addwidget);
}

package com.example.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ListContainer extends ConstraintLayout {
    private Context mContext;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    public ListContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate(mContext, R.layout.view_listcontainer, this);

        recyclerView1=findViewById(R.id.recyclerView1);
        recyclerView2=findViewById(R.id.recyclerView2);

    }
}

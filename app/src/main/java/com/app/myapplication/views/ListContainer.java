package com.app.myapplication.views;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.app.myapplication.DetailActivity;
import com.app.beans.TypeBean;
import com.app.utils.BaseUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.app.myapplication.R;
import com.app.myapplication.adapters.FoodAdapter;
import com.app.myapplication.adapters.TypeAdapter;
import com.app.beans.FoodBean;
//import com.example.myapplication.detail.DetailActivity;
//import com.example.myapplication.utils.BaseUtils;
import com.app.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class ListContainer extends LinearLayout {

	Context mContext;
	public RecyclerView recyclerView1;
	public RecyclerView recyclerView2;
	public TypeAdapter typeAdapter;
	public FoodAdapter foodAdapter;

	public ListContainer(Context context) {
		super(context);
	}


	public ListContainer(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflate(context,R.layout.view_listcontainer,this);
		mContext=context;
		recyclerView1=findViewById(R.id.recycler1);
		recyclerView2=findViewById(R.id.recycler2);
	}

	public void load(List<FoodBean> flist, List<TypeBean> tlist){
		typeAdapter=new TypeAdapter(tlist);
		foodAdapter=new FoodAdapter(flist);

		recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerView1.setAdapter(typeAdapter);

		recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerView2.setAdapter(foodAdapter);

	}
}
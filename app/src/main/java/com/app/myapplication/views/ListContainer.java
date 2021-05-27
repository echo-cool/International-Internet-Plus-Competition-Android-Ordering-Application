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

import androidx.annotation.NonNull;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
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

	public void load(List<FoodBean> foodBeanList){
//		for(FoodBean foodBean:foodBeanList){
//			foodBean.FoodTypeBean = new TypeBean(foodBean.foodType);
//		}
//		foodAdapter=new FoodAdapter(foodBeanList);
//		List<TypeBean> typeBeanLinkedList=new LinkedList<>();
//		for(FoodBean foodBean:foodBeanList){
//			Boolean flag = true;
//			for(TypeBean typeBean:typeBeanLinkedList){
//				if(typeBean.name.equals(foodBean.foodType))
//					flag = false;
//			}
//			if(flag)
//				typeBeanLinkedList.add(new TypeBean(foodBean.foodType));
//		}
		foodAdapter=new FoodAdapter(foodBeanList);
		List<TypeBean> typeBeanLinkedList=new LinkedList<>();
		for(FoodBean foodBean:foodBeanList){
			if(typeBeanLinkedList.size()==0){
				typeBeanLinkedList.add(new TypeBean(foodBean.foodType));
				continue;
			}
			if(!typeBeanLinkedList.get(typeBeanLinkedList.size()-1).name.equals(foodBean.foodType)){
				typeBeanLinkedList.add(new TypeBean(foodBean.foodType));
			}
		}

		typeAdapter=new TypeAdapter(typeBeanLinkedList);
		recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerView1.setAdapter(typeAdapter);
		recyclerView1.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				if (recyclerView2.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					typeAdapter.setPosition(position);
					String type = typeAdapter.getList().get(position).name;
					for (int ii = 0; ii < foodAdapter.getList().size(); ii++) {
						FoodBean foodBean = foodAdapter.getList().get(ii);
						if (foodBean.foodType.equals(type)) {
							moveToPosition(ii);
							break;
						}
					}
				}
			}
		});
		recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerView2.setAdapter(foodAdapter);
		recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int n=((LinearLayoutManager)recyclerView2.getLayoutManager()).findFirstVisibleItemPosition();
				String type=foodAdapter.getList().get(n).foodType;
				typeAdapter.moveToType(type);
			}
		});
		foodAdapter.setOnCountChange(new FoodAdapter.OnCountChange() {
			@Override
			public void onChange(FoodBean item) {
				//异步计算
				new Thread(()->{
					String type= item.foodType;
					int ii=0;
					for(FoodBean i: foodAdapter.getList()){
						if(i.foodType.equals(type)){
							ii+=i.selectCount;
						}
					}
					final int count=ii;
					TypeBean typeBean=typeAdapter.getItem(typeAdapter.getTypePosition(type));
					((Activity)mContext).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							typeBean.count=count;
							typeAdapter.notifyDataSetChanged();
						}
					});
				}).start();

			}
		});

	}

	private void moveToPosition(int n) {
		//先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
		LinearLayoutManager linearLayoutManager=(LinearLayoutManager) recyclerView2.getLayoutManager();
		int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
		int lastItem = linearLayoutManager.findLastVisibleItemPosition();
		//然后区分情况
		if (n <= firstItem) {
			//当要置顶的项在当前显示的第一个项的前面时
			recyclerView2.smoothScrollToPosition(n);
		} else if (n <= lastItem) {
			//当要置顶的项已经在屏幕上显示时
			int top = recyclerView2.getChildAt(n - firstItem).getTop();
			recyclerView2.smoothScrollBy(0, top);
		} else {
			//当要置顶的项在当前显示的最后一项的后面时
			recyclerView2.smoothScrollToPosition(n);
			//这里这个变量是用在RecyclerView滚动监听里面的
			//move = true;
		}
	}
}
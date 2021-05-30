package com.app.myapplication.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.app.beans.FoodBean;
import com.app.myapplication.R;

public class AddWidget extends LinearLayout {

	OnAddWidgetClick onAddWidgetClick;
	private int clickCount=0;
	FoodBean foodBean;


	public AddWidget(@NonNull Context context) {
		super(context);
	}

	public AddWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		AddWidget self=this;
		inflate(context,R.layout.view_addwidget,this);
		this.findViewById(R.id.imageView_add).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				foodBean.changeSelectCount(1);
				try {

					onAddWidgetClick.onClick();
				}catch (NullPointerException ignore){}
			}

		});
		this.findViewById(R.id.imageView_remove).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				foodBean.changeSelectCount(-1);
				try {
					onAddWidgetClick.onClick();
				}catch (NullPointerException ignore){}
			}
		});
	}

	public int getClickCount(){
		int c=clickCount;
		clickCount=0;
		return c;
	}

	public void bindFoodBean(FoodBean foodBean){
		this.foodBean=foodBean;
	}



	public void setOnAddWidgetClick(OnAddWidgetClick onAddWidgetClick){
		this.onAddWidgetClick=onAddWidgetClick;
	}

	public interface OnAddWidgetClick{
		void onClick();
	}


	public void setCount(int count){
		TextView textView=findViewById(R.id.textView_count);
		if(count==0) {
			findViewById(R.id.imageView_remove).setVisibility(INVISIBLE);
			textView.setVisibility(INVISIBLE);
		}else{
			textView.setVisibility(VISIBLE);
			findViewById(R.id.imageView_remove).setVisibility(VISIBLE);
			textView.setText(""+count);
		}

	}
}

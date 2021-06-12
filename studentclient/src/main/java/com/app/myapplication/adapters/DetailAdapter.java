package com.app.myapplication.adapters;


import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

public class DetailAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
	private AddWidget.OnAddWidgetClick onAddClick;

	public DetailAdapter(@Nullable List<FoodBean> data, AddWidget.OnAddWidgetClick onAddClick) {
		super(R.layout.item_detail, data);
		this.onAddClick = onAddClick;
	}

	@Override
	protected void convert(BaseViewHolder helper, FoodBean item) {
		helper.setText(R.id.textView6, item.foodName)
				.setText(R.id.textView7, item.foodSale)
				.setText(R.id.textView8, ""+item.foodPrice)
				.setImageBitmap(R.id.imageView2, BitmapFactory.decodeByteArray(item.foodImage,0,item.foodImage.length))
				.setText(R.id.foodSummary,item.foodSummary)
		;
		AddWidget addWidget = helper.getView(R.id.detail_addwidget);
		//addWidget.setData(onAddClick,item);
	}
}

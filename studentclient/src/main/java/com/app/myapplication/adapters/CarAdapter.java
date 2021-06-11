package com.app.myapplication.adapters;


import androidx.annotation.Nullable;

import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.AddWidget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.math.BigDecimal;
import java.util.List;

public class CarAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
	private AddWidget.OnAddWidgetClick onAddClick;
	List<FoodBean> list;
	private CarAdapter self;
	private FoodAdapter foodAdapter;
	private TypeAdapter typeAdapter;

	public CarAdapter(@Nullable List<FoodBean> data, AddWidget.OnAddWidgetClick onAddClick) {
		super(R.layout.item_car, data);
		this.onAddClick = onAddClick;
		list=data;
		self=this;
	}

	@Override
	protected void convert(BaseViewHolder helper, FoodBean item) {
		helper.setText(R.id.car_name, item.foodName)
				.setText(R.id.car_price, "¥ "+item.foodPrice)
		;
		AddWidget addWidget = helper.getView(R.id.car_addwidget);
		addWidget.bindFoodBean(item);
		addWidget.setCount(item.selectCount);
		addWidget.setOnAddWidgetClick(new AddWidget.OnAddWidgetClick() {
			@Override
			public void onClick() {
				self.notifyDataSetChanged();
				try {
					foodAdapter.notifyDataSetChanged();
					foodAdapter.onCountChange.onChange(item);
					//onCountChange.onChange(item);

				}catch (NullPointerException ignore){}
			}
		});

	}

	public List<FoodBean> getList() {
		return list;

	}

	public void setList(List<FoodBean> list) {
		this.list.clear();
		this.list.addAll(list);
	}

	public void setFoodAdapter(FoodAdapter foodAdapter) {
		this.foodAdapter = foodAdapter;
	}

	public void setTypeAdapter(TypeAdapter typeAdapter) {
		this.typeAdapter = typeAdapter;
	}
}

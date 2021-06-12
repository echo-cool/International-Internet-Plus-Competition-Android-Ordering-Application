package com.app.myapplication.adapters;


import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.app.beans.TypeBean;
import com.app.myapplication.R;


import java.util.HashMap;
import java.util.List;

public class TypeAdapter extends BaseQuickAdapter<TypeBean, BaseViewHolder> {

	private List<TypeBean> list;
	private int position=0;


	public TypeAdapter(@Nullable List<TypeBean> list) {
		super(R.layout.item_type, list);
		this.list = list;

	}

	public void update() {

		notifyDataSetChanged();
	}


	@Override
	protected void convert(BaseViewHolder helper, TypeBean item) {
		helper.setText(R.id.tv_name, item.getName());
		if(item.equals(list.get(position))){
			helper.setBackgroundColor(R.id.item_main,ContextCompat.getColor(mContext,R.color.white));
		}else {
			helper.setBackgroundColor(R.id.item_main,ContextCompat.getColor(mContext,R.color.detail_divider));
		}
		if(item.count!=0){
			helper.setText(R.id.item_badge,""+item.count);
			helper.setVisible(R.id.item_badge,true);

		}else{
			helper.setVisible(R.id.item_badge,false);
		}
	}

	public void setPosition(int position){
		if(position!=this.position){
			this.position=position;
			notifyDataSetChanged();
		}
	}


	public void moveToType(String type){
		int old=position;
		for(int i=0;i<list.size();i++){
			if(list.get(i).name.equals(type)){
				position=i;
				break;
			}
		}

		if(old!=position){

			notifyDataSetChanged();
		}
	}


	public int getTypePosition(String type){
		int old=position;
		for(int i=0;i<list.size();i++){
			if(list.get(i).name.equals(type)){
				position=i;
				break;
			}
		}

		return position;
	}

	@Nullable
	@org.jetbrains.annotations.Nullable
	@Override
	public TypeBean getItem(int position) {
		return super.getItem(position);
	}

	public List<TypeBean> getList() {
		return list;
	}

}


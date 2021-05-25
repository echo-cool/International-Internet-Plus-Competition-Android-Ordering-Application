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
			helper.setBackgroundColor(R.id.item_main,ContextCompat.getColor(mContext,R.color.lightgray));
		}
	}



	private void moveToPosition(int i) {

	}

}

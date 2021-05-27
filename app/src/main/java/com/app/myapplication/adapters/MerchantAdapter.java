package com.app.myapplication.adapters;

import com.app.beans.MerchantBean;
import com.app.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

public class MerchantAdapter extends BaseQuickAdapter<MerchantBean, BaseViewHolder> {
    List<MerchantBean> list;

    public MerchantAdapter(@Nullable List<MerchantBean> data) {
        super(R.layout.item_merchant,data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantBean item) {
        helper.setText(R.id.merchant_name,item.mctName);
    }
    public List<MerchantBean> getList() {
        return list;
    }


}

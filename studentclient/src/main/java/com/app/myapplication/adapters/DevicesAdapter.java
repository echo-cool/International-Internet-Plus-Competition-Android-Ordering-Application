package com.app.myapplication.adapters;

import com.app.beans.DevicesBean;
import com.app.beans.MerchantBean;
import com.app.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;


public class DevicesAdapter extends BaseQuickAdapter<DevicesBean, BaseViewHolder> {
    List<DevicesBean> list;
    public DevicesAdapter(List<DevicesBean> data) {
        super(R.layout.device_details,data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesBean item) {
        helper.setText(R.id.dv_name,item.device_name);
        helper.setText(R.id.dv_pop,"预计人数："+item.population+"人");
    }
    public List<DevicesBean> getList() {
        return list;
    }
}

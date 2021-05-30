package com.example.restaurantclient.adapters;


import com.example.beans.NotificationBean;

public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(NotificationBean bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(NotificationBean bean);
}
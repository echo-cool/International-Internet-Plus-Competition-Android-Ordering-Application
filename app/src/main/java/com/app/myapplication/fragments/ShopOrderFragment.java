package com.app.myapplication.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.Models.Cuisine;
import com.app.Models.RequestListener;
import com.app.beans.FoodBean;
import com.app.beans.TypeBean;
import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;
import com.app.myapplication.views.ListContainer;
import com.app.utils.BaseUtils;

import java.util.Comparator;
import java.util.List;

public class ShopOrderFragment extends Fragment {

    private ShopOrderViewModel mViewModel;

    public static ShopOrderFragment newInstance() {
        return new ShopOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_order_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
        // TODO: Use the ViewModel

        BaseUtils.getDatas(new RequestListener() {
            Boolean finished = false;
            @Override
            public void success(List FoodData) {
//                if(finished)
                //((ListContainer)getActivity().findViewById(R.id.listcontainer)).load(FoodData);
            }

            @Override
            public void processing(int now, int size, List data) {
                if(now + 1 == size) {
                    data.sort(new Comparator() {
                        @Override
                        public int compare(Object o, Object t1) {
                            return ((FoodBean)o).foodType.compareTo(((FoodBean)t1).foodType);
                        }
                    });
                    ((ListContainer) getActivity().findViewById(R.id.listcontainer)).load(data);
                }
            }


            @Override
            public void failed(String reason) {

            }
        });
//        BaseUtils.getTypes(new RequestListener<TypeBean>() {
//            @Override
//            public void success(List<TypeBean> data) {
//
//
//            }
//            @Override
//            public void failed(String reason) {
//                System.out.println("ERROR");
//            }
//        });

    }



}
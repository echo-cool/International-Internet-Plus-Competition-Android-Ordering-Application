package com.app.myapplication.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.views.ListContainer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShopOrderFragment extends Fragment {

    private ShopOrderViewModel mViewModel;
    private ListContainer listContainer;
    private String id;
    private OnLoadListener onLoadListener;

//    public static ShopOrderFragment newInstance() {
//        return new ShopOrderFragment();
//    }

    public ShopOrderFragment(String id){
        this.id=id;
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
        listContainer=getActivity().findViewById(R.id.listcontainer);
        loadFoodList(id);

//        BaseUtils.getDatas(new RequestListener() {
//            Boolean finished = false;
//            @Override
//            public void success(List FoodData) {
////                if(finished)
//                //((ListContainer)getActivity().findViewById(R.id.listcontainer)).load(FoodData);
//            }
//
//            @Override
//            public void processing(int now, int size, List data) {
//                if(now + 1 == size) {
//                    data.sort(new Comparator() {
//                        @Override
//                        public int compare(Object o, Object t1) {
//                            return ((FoodBean)o).foodType.compareTo(((FoodBean)t1).foodType);
//                        }
//                    });
//                    ((ListContainer) getActivity().findViewById(R.id.listcontainer)).load(data);
//                }
//            }
//
//
//            @Override
//            public void failed(String reason) {
//
//            }
//        });
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


    public void loadFoodList(String id){
        AVQuery<AVObject> query = new AVQuery<>("Cuisine");
        query.include("Type");
        AVObject pointer = AVObject.createWithoutData("Restaurant", id);
        query.whereEqualTo("Restaurant",pointer);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> list) {
                Map<AVObject,List<FoodBean>> map=new LinkedHashMap<>();
                //public FoodBean(int id,String name,String type,String summary,int sale,double foodPrice)
                for (AVObject avObject : list) {
                    //map.containsKey(avObject.getAVObject("Cuisine_Type").getString("Name"))
                    //map.put(avObject.getAVObject("Cuisine_Type").get("Name"),new FoodBean(avObject.getObjectId(),avObject.get("Name")));
                    if(!map.containsKey(avObject.getAVObject("Type"))){
                        System.out.println(avObject);
                        map.put(avObject.getAVObject("Type"),new LinkedList<>());
                    }
                    map.get(avObject.getAVObject("Type")).add(new FoodBean(avObject.getObjectId(),avObject.getString("Name"),avObject.getAVObject("Type").getString("Name"),avObject.getString("Description"),avObject.getInt("Daysales"),avObject.getDouble("foodPrice")));
                }
                List<FoodBean> foodBeans=new LinkedList<>();
                for(AVObject avObject: map.keySet()){
                    foodBeans.addAll(map.get(avObject));
                }
//                getActivity().runOnUiThread(()->{
                    listContainer.load(foodBeans);
//                });
            }
            public void onError(Throwable throwable) {
                if(onLoadListener !=null){
                    onLoadListener.onError();
                }
            }
            public void onComplete() {
                if(onLoadListener !=null){
                    onLoadListener.onSuccess();
                }
            }
        });
    }


    public Bitmap loadImage(String id){
        //
        AVQuery<AVObject> query = new AVQuery<>("Cuisine");
        query.include("Type");
        AVObject pointer = AVObject.createWithoutData("Restaurant", id);
        query.whereEqualTo("Restaurant",pointer);
        List<AVObject>  avObjects=query.find();


        return null;
    }

    public ListContainer getListContainer() {
        return listContainer;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        void onSuccess();

        void onError();
    }



}
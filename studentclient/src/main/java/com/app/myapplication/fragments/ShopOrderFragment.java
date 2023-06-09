package com.app.myapplication.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.Models.Cuisine;
import com.app.Models.ImageListener;
import com.app.Models.RequestListener;
import com.app.beans.FoodBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.FoodAdapter;
import com.app.myapplication.views.ListContainer;
import com.app.utils.BaseUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private FoodAdapter foodAdapter;
    private Context mContext;

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
        mContext=this.getActivity();
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
    public static void loadImage(String url, ImageListener listener){
        final Bitmap[] bitmap = new Bitmap[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    listener.success(bitmap[0]);
                    is.close();
                } catch (IOException e) {
                    listener.failed(e.toString());
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void loadImage(FoodBean foodBean){
        String url = foodBean.Image_url;
        if(url != null){
            loadImage(url, new ImageListener() {
                @Override
                public void success(Bitmap data) {
                    foodBean.foodImage = BaseUtils.getBytes(data);
                }
                @Override
                public void failed(String reason) {
                }
            });
        }

    }


    public void loadFoodList(String id){
        AVQuery<AVObject> query = new AVQuery<>("Cuisine");
        query.include("Type");
        query.include("Main_Photo");
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
                    FoodBean food = new FoodBean(avObject.getObjectId(),avObject.getString("Name"),avObject.getAVObject("Type").getString("Name"),avObject.getString("Description"),avObject.getInt("Daysales"),avObject.getDouble("foodPrice"));
                    food.setCuisineOBJ((Cuisine) avObject);
                    String food_url = avObject.getAVFile("Main_Photo").getUrl();
                    food.setImage_url(food_url);
                    loadImage(food_url, new ImageListener() {
                        @Override
                        public void success(Bitmap data) {
                            food.foodImage = BaseUtils.getBytes(data);
                            //System.out.println("...............................................");
                            ((Activity)mContext).runOnUiThread(()->{
                                listContainer.foodAdapter.notifyDataSetChanged();
                            });
                        }

                        @Override
                        public void failed(String reason) {
                            System.out.println(reason);

                        }
                    });
                    map.get(avObject.getAVObject("Type")).add(food);
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
package com.app.utils;


import android.content.Context;
import android.text.style.IconMarginSpan;
import android.util.Log;


import com.app.Models.Cuisine;
import com.app.Models.Cuisine_Type;
import com.app.Models.RequestListener;
import com.app.beans.FoodBean;
import com.app.beans.TypeBean;
import com.app.myapplication.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;


public class BaseUtils {
	public static ArrayList<String> food_name = new ArrayList<>() ;
	public static ArrayList<String> classes = new ArrayList<>() ;
	public static double[] prices={9,12.5,8,14.5,15,8,16,15,8,20};
	public static int[] sales={21,8,15,7,7,3,19,10,14,13,11,11,11};

	private static void getClasses(RequestListener listener){

		ArrayList res = new ArrayList();
		AVQuery<Cuisine_Type> query = AVQuery.getQuery(Cuisine_Type.class);
		query.findInBackground().subscribe(new Observer<List<Cuisine_Type>>() {
			@Override
			public void onSubscribe(@NonNull Disposable d) {
			}
			@Override
			public void onNext(@NonNull List<Cuisine_Type> cuisine_types) {
				listener.success(cuisine_types);
			}

			@Override
			public void onError(@NonNull Throwable e) {
				listener.failed("Error");
			}
			@Override
			public void onComplete() {
			}
		});
	}


	public static void getTypes(RequestListener listener) {
		ArrayList<TypeBean> tList = new ArrayList<>();
		getClasses(new RequestListener<Cuisine_Type>() {
			@Override
			public void success(List<Cuisine_Type> data) {

				for (int i = 0; i < data.size(); i++) {
					TypeBean typeBean = new TypeBean();
					typeBean.setName(data.get(i).getNAME());
					tList.add(typeBean);
					classes.add(data.get(i).getNAME());
				}
				listener.success(tList);
			}
			@Override
			public void failed(String reason) {
				listener.failed("ERROR Processing");
			}
		});


	}
	private static void getFoods(RequestListener listener){
		ArrayList res = new ArrayList();
		AVQuery<Cuisine> query = AVQuery.getQuery(Cuisine.class);

		query.findInBackground().subscribe(new Observer<List<Cuisine>>() {
			@Override
			public void onSubscribe(@NonNull Disposable d) {
			}
			@Override
			public void onNext(@NonNull List<Cuisine> cuisine_types) {
				listener.success(cuisine_types);
			}

			@Override
			public void onError(@NonNull Throwable e) {
				listener.failed("Error");
			}
			@Override
			public void onComplete() {
			}
		});
	}

	public static void getDatas(RequestListener listener) {
		ArrayList<FoodBean> fList = new ArrayList<>();
		getFoods(new RequestListener<Cuisine>() {
			@Override
			public void success(List<Cuisine> data) {
				for (int i = 0; i < data.size(); i++) {
					FoodBean foodBean = new FoodBean();
					foodBean.id=i;
					foodBean.foodName=data.get(i).getNAME();
					foodBean.foodPrice= (double) (data.get(i).getfoodPrice());
					foodBean.foodSale= (int) data.get(i).getStock();
					foodBean.selectCount=0;
					String Type_ID = data.get(i).getType().getObjectId();
					AVQuery<Cuisine_Type> query = AVQuery.getQuery(Cuisine_Type.class);
					query.getInBackground(Type_ID).subscribe(new Observer<Cuisine_Type>() {
						@Override
						public void onSubscribe(@NonNull Disposable d) {

						}
						@Override
						public void onNext(@NonNull Cuisine_Type cuisine_type) {
							foodBean.foodType=(cuisine_type.getNAME());
							System.out.println(foodBean.foodType);
							int resID =R.drawable.shop_image_loading;
							fList.add(foodBean);
						}

						@Override
						public void onError(@NonNull Throwable e) {

						}

						@Override
						public void onComplete() {
							listener.success(fList);
						}
					});
				}

			}
			@Override
			public void failed(String reason) {

			}
		});
	}

	public static List<FoodBean> getDetails(List<FoodBean> fList) {
		ArrayList<FoodBean> flist = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			if (fList.size() > i * 10) {
				flist.add(fList.get(i * 10 - 1));
				flist.add(fList.get(i * 10));
			} else {
				break;
			}
		}
		return flist;
	}

	protected static <T extends AVObject> String prettyJSON(List<T> objects) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (AVObject object : objects) {
			sb.append(prettyJSON(object));
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	protected static String prettyJSON(AVObject object) {
		String  jsonObject = object.toJSONString();
		return jsonObject;
	}
	protected static void log(String format, @Nullable Object... objects) {
		final String msg = String.format(format, objects);
		Log.d("LOG", msg);
	}



}

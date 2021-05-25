package com.app.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.beans.FoodBean;
import com.app.myapplication.R;
//import com.app.myapplication.adapters.CarAdapter;
import com.app.myapplication.fragments.ShopCommentsFragment;
import com.app.myapplication.fragments.ShopOrderFragment;
import com.app.myapplication.fragments.TestShopOrderFragment;
//import com.app.myapplication.views.ShopCarView;
import com.app.myapplication.views.AddWidget;
//import com.app.myapplication.views.ShopCarView;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//
//public class ShopActivity extends BaseActivity implements AddWidget.OnAddClick {
//    public static final int chose_puhoto=2;
//
//    public static final String CAR_ACTION = "handleCar";
//    public static final String CLEARCAR_ACTION = "clearCar";
//    private CoordinatorLayout rootview;
//    public BottomSheetBehavior behavior;
//    public View scroll_container;
//    private Fragment firstFragment;
//    public static CarAdapter carAdapter;
//    private ShopCarView shopCarView;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_shop;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        System.out.println("11111111111111111111111111111");
//        initViews();
//        IntentFilter intentFilter = new IntentFilter(CAR_ACTION);
//        intentFilter.addAction(CLEARCAR_ACTION);
//        registerReceiver(broadcastReceiver, intentFilter);
//        ////
////        CoordinatorLayout.LayoutParams params1=(CoordinatorLayout.LayoutParams)findViewById(R.id.appbar).getLayoutParams();
////        CoordinatorLayout.LayoutParams params2=(CoordinatorLayout.LayoutParams)findViewById(R.id.ll_cut).getLayoutParams();
////        CoordinatorLayout.LayoutParams params3=(CoordinatorLayout.LayoutParams)findViewById(R.id.shopcontainer).getLayoutParams();
////        CoordinatorLayout.LayoutParams params4=(CoordinatorLayout.LayoutParams)findViewById(R.id.scroll_container).getLayoutParams();
////        params1.setBehavior(new com.example.canteenapp.behaviors.AppBarBehavior());
//        //params2.setBehavior(new );
////        params3.setBehavior(new com.example.canteenapp.behaviors.ShopContainerBehavior());
//        //params4.setBehavior(new );
//
//
//
//        Activity activity=this;
//
//
//
//
//
//
//        Activity context=this;
//\
//
//
//
//
//
//
//    }
//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            switch (intent.getAction()) {
//                case CAR_ACTION:
//                    FoodBean foodBean = (FoodBean) intent.getSerializableExtra("foodbean");
//                    FoodBean fb = foodBean;
//                    int p = intent.getIntExtra("position", -1);
//                    if (p >= 0 && p < firstFragment.getFoodAdapter().getItemCount()) {
//                        fb = firstFragment.getFoodAdapter().getItem(p);
//                        fb.setSelectCount(foodBean.getSelectCount());
//                        firstFragment.getFoodAdapter().setData(p, fb);
//                    } else {
//                        for (int i = 0; i < firstFragment.getFoodAdapter().getItemCount(); i++) {
//                            fb = firstFragment.getFoodAdapter().getItem(i);
//                            if (fb.getId() == foodBean.getId()) {
//                                fb.setSelectCount(foodBean.getSelectCount());
//                                firstFragment.getFoodAdapter().setData(i, fb);
//                                break;
//                            }
//                        }
//                    }
//                    dealCar(fb);
//                    break;
//                case CLEARCAR_ACTION:
//                    clearCar();
//                    break;
//            }
//            if (CAR_ACTION.equals(intent.getAction())) {
//
//            }
//        }
//    };
//
//    private void initViews() {
//        rootview = (CoordinatorLayout) findViewById(R.id.rootview);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        initViewpager();
//        initShopCar();
//    }
//
//    private void initShopCar() {
//        behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));
//        shopCarView = (ShopCarView) findViewById(R.id.car_mainfl);
//        View blackView = findViewById(R.id.blackview);
//        shopCarView.setBehavior(behavior, blackView);
//        RecyclerView carRecView = (RecyclerView) findViewById(R.id.car_recyclerview);
////		carRecView.setNestedScrollingEnabled(false);
//        carRecView.setLayoutManager(new LinearLayoutManager(mContext));
//        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
//        carAdapter = new CarAdapter(new ArrayList<FoodBean>(), this);
//        carAdapter.bindToRecyclerView(carRecView);
//    }
//
//    private void initViewpager() {
//        scroll_container = findViewById(R.id.scroll_container);
//        ScrollIndicatorView mSv = (ScrollIndicatorView) findViewById(R.id.indicator);
//        ColorBar colorBar = new ColorBar(mContext, ContextCompat.getColor(mContext, R.color.colorPrimary), 6,
//                ScrollBar.Gravity.BOTTOM);
//        colorBar.setWidth(ViewUtils.dip2px(mContext, 30));
//        mSv.setScrollBar(colorBar);
//        mSv.setSplitAuto(false);
//        mSv.setOnTransitionListener(new OnTransitionTextListener().setColor(ContextCompat.getColor(mContext, R.color.colorPrimary),
//                ContextCompat.getColor(mContext, R.color.black)));
//        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mSv, mViewPager);
//        firstFragment = new FirstFragment();
//        ViewpagerAdapter myAdapter = new ViewpagerAdapter(getSupportFragmentManager());
//        indicatorViewPager.setAdapter(myAdapter);
//    }
//
//    private class ViewpagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
//        private LayoutInflater inflater;
//        private int padding;
//        private String[] tabs = new String[]{getResources().getString(R.string.title_dishes),getResources().getString(R.string.title_comments)};
//
//        ViewpagerAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//            inflater = LayoutInflater.from(mContext);
//            padding = ViewUtils.dip2px(mContext, 20);
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @Override
//        public View getViewForTab(int position, View convertView, ViewGroup container) {
//            convertView = inflater.inflate(R.layout.item_textview, container, false);
//            TextView textView = (TextView) convertView;
//            textView.setText(tabs[position]); //名称
//            textView.setPadding(padding, 0, padding, 0);
//            return convertView;
//        }
//
//        @Override
//        public Fragment getFragmentForPage(int position) {
//            switch (position) {
//                case 0:
//                    return firstFragment;
//            }
//            return new SecondFragment();
//        }
//    }
//
//    @Override
//    public void onAddClick(View view, FoodBean fb) {
//        dealCar(fb);
//        ViewUtils.addTvAnim(view, shopCarView.carLoc, mContext, rootview);
//        findViewById(R.id.car_mainfl).setVisibility(View.VISIBLE);
//    }
//
//
//    @Override
//    public void onSubClick(FoodBean fb) {
//        dealCar(fb);
//    }
//
//    private void dealCar(FoodBean foodBean) {
//        HashMap<String, Long> typeSelect = new HashMap<>();//更新左侧类别badge用
//        BigDecimal amount = new BigDecimal(0.0);
//        int total = 0;
//        boolean hasFood = false;
//        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//            firstFragment.getFoodAdapter().notifyDataSetChanged();
//        }
//        List<FoodBean> flist = carAdapter.getData();
//        int p = -1;
//        for (int i = 0; i < flist.size(); i++) {
//            FoodBean fb = flist.get(i);
//            if (fb.getId() == foodBean.getId()) {
//                fb = foodBean;
//                hasFood = true;
//                if (foodBean.getSelectCount() == 0) {
//                    p = i;
//                } else {
//                    carAdapter.setData(i, foodBean);
//                }
//            }
//            total += fb.getSelectCount();
//            if (typeSelect.containsKey(fb.getType())) {
//                typeSelect.put(fb.getType(), typeSelect.get(fb.getType()) + fb.getSelectCount());
//            } else {
//                typeSelect.put(fb.getType(), fb.getSelectCount());
//            }
//            amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
//        }
//        if (p >= 0) {
//            carAdapter.remove(p);
//        } else if (!hasFood && foodBean.getSelectCount() > 0) {
//            carAdapter.addData(foodBean);
//            if (typeSelect.containsKey(foodBean.getType())) {
//                typeSelect.put(foodBean.getType(), typeSelect.get(foodBean.getType()) + foodBean.getSelectCount());
//            } else {
//                typeSelect.put(foodBean.getType(), foodBean.getSelectCount());
//            }
//            amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
//            total += foodBean.getSelectCount();
//        }
//        shopCarView.showBadge(total);
//        firstFragment.getTypeAdapter().updateBadge(typeSelect);
//        shopCarView.updateAmount(amount);
//    }
//
//    public void expendCut(View view) {
//        float cty = scroll_container.getTranslationY();
//        if (!ViewUtils.isFastClick()) {
//            ViewAnimator.animate(scroll_container)
//                    .translationY(cty, cty == 0 ? AppBarBehavior.cutExpHeight : 0)
//                    .decelerate()
//                    .duration(100)
//                    .start();
//        }
//    }
//
//    public void clearCar(View view) {
//        ViewUtils.showClearCar(mContext, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                clearCar();
//            }
//        });
//    }
//
//    private void clearCar() {
//        List<FoodBean> flist = carAdapter.getData();
//        for (int i = 0; i < flist.size(); i++) {
//            FoodBean fb = flist.get(i);
//            fb.setSelectCount(0);
//        }
//        carAdapter.setNewData(new ArrayList<FoodBean>());
//        firstFragment.getFoodAdapter().notifyDataSetChanged();
//        shopCarView.showBadge(0);
//        firstFragment.getTypeAdapter().updateBadge(new HashMap<String, Long>());
//        shopCarView.updateAmount(new BigDecimal(0.0));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
//        //System.exit(0);
//    }
//
//    public void toShopDetail(View view) {
//        firstFragment.getFoodAdapter().notifyItemChanged(0);
////        ShopInfoContainer shopInfoContainer = (ShopInfoContainer) findViewById(R.id.shopcontainer);
////        if (android.os.Build.VERSION.SDK_INT > 20) {
////            startActivity(new Intent(mContext, ShopDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation(ShopActivity.this, shopInfoContainer.iv_shop, "transitionShopImg").toBundle());
////        } else {
////            startActivity(new Intent(mContext, ShopDetailActivity.class));
////        }
//
//    }
//
//
//
//
//}

























public class ShopActivity extends AppCompatActivity implements TestShopOrderFragment.OnFragmentInteractionListener {

    public static final int chose_puhoto=2;

    public static final String CAR_ACTION = "handleCar";
    public static final String CLEARCAR_ACTION = "clearCar";
    private CoordinatorLayout rootview;
    public BottomSheetBehavior behavior;
    public View scroll_container;
    private Fragment firstFragment;
//    public static CarAdapter carAdapter;
   // private ShopCarView shopCarView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setViewPager();
    }


    private void setViewPager(){
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager2 viewPager2=findViewById(R.id.pager);
        final Fragment[] fragments={new ShopOrderFragment(),new ShopCommentsFragment()};
        final String[] strings={getString(R.string.title_order),getString(R.string.title_comments)};
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {


                return fragments[position];
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(strings[position])
        ).attach();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package com.app.myapplication.Home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.NotificationAdapter;
import com.app.myapplication.animator.NoAlphaAnimator;
import com.app.myapplication.views.MarginView;
import com.app.utils.ViewUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private NotificationAdapter nAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    View tempView;
    private AppBarLayout appBarLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment userFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.user_page));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final NestedScrollView nestedScrollView=getActivity().findViewById(R.id.scroll_2);
        appBarLayout=getActivity().findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                System.out.println("=========================="+verticalOffset);
                recyclerView.setAlpha((float) getAlpha(verticalOffset));
                getActivity().findViewById(R.id.user_avatar).getLayoutParams().height=500+(verticalOffset);
                getActivity().findViewById(R.id.user_avatar).getLayoutParams().width=500+(verticalOffset);
                getActivity().findViewById(R.id.user_avatar).requestLayout();
            }
        });


        mContext=this.getActivity();
        recyclerView=this.getActivity().findViewById(R.id.notification_list);
        nAdapter =new NotificationAdapter(new LinkedList<>());
        //nAdapter.setHeaderView(new MarginView(mContext));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(nAdapter);
        recyclerView.setItemAnimator(new NoAlphaAnimator());
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);


        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int i=nAdapter.getExpandPosition();
                View cardView=view.findViewById(R.id.notification_card);
                View new_hidden=view.findViewById(R.id.hidden_information);
                int duration=500;


                if(i!=-1&&i!=position) {
                    //同时关闭一个展开一个
                    nAdapter.setExpandNotificationBean(nAdapter.getList().get(position), position);
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator.setDuration(duration);
                    //new_hidden.setVisibility(View.VISIBLE);
                    //tempView.findViewById(R.id.hidden_information).setVisibility(View.GONE);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float h =  (float)valueAnimator.getAnimatedValue();
                            //动态更新view的高度
                            cardView.getLayoutParams().height = ViewUtils.dip2px(mContext,88*(h*2+1));
                            cardView.setAlpha(0.8f+h/5);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).leftMargin=ViewUtils.dip2px(mContext,24-12*h);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).rightMargin=ViewUtils.dip2px(mContext,24-12*h);
                            cardView.requestLayout();


                            tempView.getLayoutParams().height=ViewUtils.dip2px(mContext,88*3-88*(h*2));
                            tempView.setAlpha(1.0f-h/5);
                            ((ConstraintLayout.LayoutParams)tempView.getLayoutParams()).leftMargin=ViewUtils.dip2px(mContext,12+12*h);
                            ((ConstraintLayout.LayoutParams)tempView.getLayoutParams()).rightMargin=ViewUtils.dip2px(mContext,12+12*h);
                            tempView.requestLayout();
                        }
                    });
                    valueAnimator.start();
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                            tempView=cardView;
                        }
                    });

                }else if(i==-1){
                    nAdapter.setExpandNotificationBean(nAdapter.getList().get(position), position);

                    tempView=cardView;
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator.setDuration(duration);
                    //new_hidden.setVisibility(View.VISIBLE);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float h =  (float)valueAnimator.getAnimatedValue();
                            //动态更新view的高度
                            cardView.getLayoutParams().height = ViewUtils.dip2px(mContext,88*(h*2+1));
                            cardView.setAlpha(0.8f+h/5);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).leftMargin=ViewUtils.dip2px(mContext,24-12*h);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).rightMargin=ViewUtils.dip2px(mContext,24-12*h);
                            cardView.requestLayout();
                        }
                    });
                    valueAnimator.start();
                }else{
                    nAdapter.setExpandNotificationBean(null);
                    nAdapter.setExpandPosition(-1);

                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator.setDuration(duration);
                    //new_hidden.setVisibility(View.GONE);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float h =  (float)valueAnimator.getAnimatedValue();
                            //动态更新view的高度
                            cardView.getLayoutParams().height = ViewUtils.dip2px(mContext,88*3-88*(h*2));
                            cardView.setAlpha(1.0f-h/5);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).leftMargin=ViewUtils.dip2px(mContext,12+12*h);
                            ((ConstraintLayout.LayoutParams)cardView.getLayoutParams()).rightMargin=ViewUtils.dip2px(mContext,12+12*h);
                            cardView.requestLayout();
                        }
                    });
                    valueAnimator.start();
                }

            }
        });

        refresh();
        //load(testUtil());
    }

    private void load(List<NotificationBean> list){
        nAdapter.getList().clear();
        nAdapter.addAll(list);
        nAdapter.notifyDataSetChanged();
    }

    private void loadNotificationList(String receiverId){
        //TODO:完成拉取消息回调，记得拉去发给该用户的消息别拉到别人的消息了。success后调用load函数传入list，不论成功还是失败结尾都要调用一下swipeRefreshLayout.setRefreshing(false);
        AVQuery<AVObject> query = new AVQuery<>("All_notification");
        query.include("receiver");
        AVObject pointer = AVObject.createWithoutData("receiver", receiverId);
        query.whereEqualTo("receiver",pointer);
        query.orderByDescending("updatedAt");
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> list) {
                // list 是包含满足条件的 All_notification 对象的数组
                System.out.println("-----------------------------"+list.size());
                List<NotificationBean> notificationList = new LinkedList<>();
                // public NotificationBean(String title, String summary, String detail)
                for(AVObject avObject : list){
                    NotificationBean notificationBean = new NotificationBean(avObject.getString("title"), avObject.getString("summary"), avObject.getString("detail"));
                    notificationList.add(notificationBean);
                }
                load(notificationList);
            }
            public void onError(Throwable throwable) {
                //swipeRefreshLayout.setRefreshing(false);
            }
            public void onComplete() {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private List<NotificationBean> loadMore(NotificationBean notificationBean){
        //TODO:拉取我传入的notificationBean之后的消息，！！！这个不准写成回调查询！！！，如果返回null代表查询失败，如果返回的list的最后一个值为null代表查完了，如果返回list的最后一个值是bean则代表还没查完。
        return null;
    }

    private List<NotificationBean> testUtil(){
        List<NotificationBean> list=new LinkedList<>();
        for(int i=0;i<=10;i++){
            list.add(new NotificationBean("title"+i, "emmmmmmm", "emmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"));
        }
        return list;
    }


    private void refresh(){
        tempView=null;
        nAdapter.setExpandPosition(-1);
        nAdapter.setExpandNotificationBean(null);
        loadNotificationList(AVUser.getCurrentUser() == null ? "60afa0a3dd770475f266d21f": AVUser.getCurrentUser().getObjectId());
    }

    public double getAlpha(int i){
        return -1f*i/360;
    }

}
package com.app.myapplication.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.NotificationAdapter;
//import com.app.myapplication.adapters.RecyclerAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private NotificationAdapter nAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=this.getActivity();
        recyclerView=this.getActivity().findViewById(R.id.recyclerView17);
        nAdapter =new NotificationAdapter(new LinkedList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(nAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int i=nAdapter.getExpandPosition();
                if(nAdapter.getList().get(position)!=nAdapter.getExpandNotificationBean())
                    nAdapter.setExpandNotificationBean(nAdapter.getList().get(position),position);
                else nAdapter.setExpandNotificationBean(null);
                //nAdapter.notifyDataSetChanged();
                nAdapter.notifyItemChanged(i);
                nAdapter.notifyItemChanged(position);

            }
        });

        swipeRefreshLayout=((SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });



        //load(testUtil());
        swipeRefreshLayout.setRefreshing(true);
        refresh();
        //nAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    private void load(List<NotificationBean> list){
        nAdapter.getList().clear();
        nAdapter.addAll(list);
        nAdapter.notifyDataSetChanged();
    }

    private void loadNotificationList(){
        //TODO:完成拉取消息回调，记得拉去发给该用户的消息别拉到别人的消息了。success后调用load函数传入list，不论成功还是失败结尾都要调用一下swipeRefreshLayout.setRefreshing(false);
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
        load(testUtil());
    }



}
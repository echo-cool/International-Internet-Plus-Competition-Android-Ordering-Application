package com.app.myapplication.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;
import com.app.myapplication.adapters.RecyclerAdapter;
import com.example.myapplication.activity_notification;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<NotificationBean> dataBeanList;
    private NotificationBean dataBean;
    private RecyclerAdapter mAdapter;
    private Context mContext;

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
        this.mContext=this.getActivity();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycle_view14);
        initData();

        mAdapter.setmOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                final String[] items = {"删除"};
                final NotificationBean notificationBean = dataBeanList.get( position );
                android.app.AlertDialog.Builder listDialog = new android.app.AlertDialog.Builder(mContext);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            mAdapter.removeData(position);
                        }
                    }
                });
                listDialog.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.activity_notification, container, false);
    }
    private void initData(){
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            dataBean = new NotificationBean();
            dataBean.setID(i+"");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("Message --"+i);
            dataBean.setParentRightTxt("Time--"+i);
            dataBean.setChildLeftTxt("Details--"+i);
            dataBean.setChildRightTxt("Content-"+i);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }


    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter = new RecyclerAdapter(this.getContext(),dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

}
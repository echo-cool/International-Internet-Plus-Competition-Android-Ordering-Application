package com.app.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.beans.NotificationBean;
import com.app.myapplication.R;

import java.util.List;
//
//public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnLongClickListener {
//
//    private Context context;
//    private List<NotificationBean> dataBeanList;
//    private LayoutInflater mInflater;
//    private OnScrollListener mOnScrollListener;
//    public OnItemClickLitener mOnItemClickLitener;
//
//    public RecyclerAdapter(){
//
//    }
//
//
//    public RecyclerAdapter(Context context, List<NotificationBean> dataBeanList) {
//        this.context = context;
//        this.dataBeanList = dataBeanList;
//        this.mInflater = LayoutInflater.from(context);
//    }
//
//    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
//        this.mOnItemClickLitener = mOnItemClickLitener;
//    }
//
//    @Override
//    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = null;
//        switch (viewType){
//            case NotificationBean.PARENT_ITEM:
//                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
//                return new ParentViewHolder(context, view);
//            case NotificationBean.CHILD_ITEM:
//                view = mInflater.inflate(R.layout.recycleview_item_child, parent, false);
//                return new ChildViewHolder(context, view);
//            default:
//                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
//                return new ParentViewHolder(context, view);
//        }
//    }
//
//    /**
//     * 根据不同的类型绑定View
//     * @param holder
//     * @param position
//     */
//    @Override
//    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        switch (getItemViewType(position)){
//            case NotificationBean.PARENT_ITEM:
//                ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
//                parentViewHolder.bindView(dataBeanList.get(position), position, itemClickListener);
//                break;
//            case NotificationBean.CHILD_ITEM:
//                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
//                childViewHolder.bindView(dataBeanList.get(position), position);
//                break;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataBeanList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return dataBeanList.get(position).getType();
//    }
//
//    private ItemClickListener itemClickListener = new ItemClickListener() {
//
//        @Override
//        public void onExpandChildren(NotificationBean bean) {
//            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
//            NotificationBean children = getChildDataBean(bean);//获取要展示的子布局数据对象，注意区分onHideChildren方法中的getChildBean()。
//            if (children == null) {
//                return;
//            }
//            add(children, position + 1);//在当前的item下方插入
//            if (position == dataBeanList.size() - 2 && mOnScrollListener != null) { //如果点击的item为最后一个
//                mOnScrollListener.scrollTo(position + 1);//向下滚动，使子布局能够完全展示
//            }
//        }
//
//        @Override
//        public void onHideChildren(NotificationBean bean) {
//            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
//            NotificationBean children = bean.getChildBean();//获取子布局对象
//            if (children == null) {
//                return;
//            }
//            remove(position + 1);//删除
//            if (mOnScrollListener != null) {
//                mOnScrollListener.scrollTo(position);
//            }
//        }
//    };
//
////    public void onItemLongClick(View view, final int position) {
////        final String[] items = {"删除"};
////        final NotificationBean fruite = dataBeanList.get( position );
////        android.app.AlertDialog.Builder listDialog = new android.app.AlertDialog.Builder(this);
////        listDialog.setItems(items, new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                if (i == 0) {
////                    //  delateTemple(fruite.getFruitrName());
////                    dataBeanList.remove(position);
////                }
////            }
////        });
////        listDialog.show();
////    }
//
//
//    /**
//     * 在父布局下方插入一条数据
//     * @param bean
//     * @param position
//     */
//    public void add(NotificationBean bean, int position) {
//        dataBeanList.add(position, bean);
//        notifyItemInserted(position);
//    }
//
//    /**
//     *移除子布局数据
//     * @param position
//     */
//    protected void remove(int position) {
//        dataBeanList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    /**
//     * 确定当前点击的item位置并返回
//     * @param uuid
//     * @return
//     */
//    protected int getCurrentPosition(String uuid) {
//        for (int i = 0; i < dataBeanList.size(); i++) {
//            if (uuid.equalsIgnoreCase(dataBeanList.get(i).getID())) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * 封装子布局数据对象并返回
//     * 注意，此处只是重新封装一个DataBean对象，为了标注Type为子布局数据，进而展开，展示数据
//     * 要和onHideChildren方法里的getChildBean()区分开来
//     * @param bean
//     * @return
//     */
//    private NotificationBean getChildDataBean(NotificationBean bean){
//        NotificationBean child = new NotificationBean();
//        child.setType(1);
//        child.setParentLeftTxt(bean.getParentLeftTxt());
//        child.setParentRightTxt(bean.getParentRightTxt());
//        child.setChildLeftTxt(bean.getChildLeftTxt());
//        child.setChildRightTxt(bean.getChildRightTxt());
//        return child;
//    }
//
////    @Override
////    public boolean onLongClick(View view) {
////        //回调长按事件
//////        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) view.getTag(R.id.recycle_view14);
//////        if (mOnItemClickLitener != null) {
//////            this.mOnItemClickLitener.onItemLongClick(view, holder.getAdapterPosition());
//////        }
//////        return false;
////    }
//
//    public void removeData(int position) {
//        dataBeanList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//
//    /**
//     * 滚动监听接口
//     */
//    public interface OnScrollListener{
//        void scrollTo(int pos);
//    }
//
//    public void setOnScrollListener(OnScrollListener onScrollListener){
//        this.mOnScrollListener = onScrollListener;
//    }
//
//    //自定义点击接口类
//    public interface OnItemClickLitener {
//        void onItemLongClick(View view, int position);
//    }
//}
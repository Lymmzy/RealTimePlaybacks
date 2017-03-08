package com.lym.realtimeplayback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYM on 2017/2/22.
 */

public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mDatas;
    protected final Context mContext;
    protected int mLayoutResId;
    private OnItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public BaseAdapter(List<T> datas, Context context, int layoutResId) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
        return new BaseViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H) holder, item);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    protected abstract void convert(H holder, T item);

    public T getItem(int position) {
        return position >= mDatas.size() ? null : mDatas.get(position);
    }

    public void refreshData(List<T> datas) {
        if(datas!=null&&datas.size()>0) {
            clearData();
            addData(datas);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void addData(List<T> newDatas) {
        addData(0, newDatas);
    }

    public void addData(int position, List<T> newDatas) {
        if (mDatas != null && mDatas.size() >= 0) {
            mDatas.addAll(newDatas);
            notifyItemRangeChanged(position, newDatas.size());
        }
    }

    public void clearData() {
        mDatas.clear();
        notifyItemRangeRemoved(0, mDatas.size());
        notifyDataSetChanged();
    }
}

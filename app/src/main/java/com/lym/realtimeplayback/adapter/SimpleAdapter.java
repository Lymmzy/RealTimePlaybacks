package com.lym.realtimeplayback.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by LYM on 2017/2/22.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(List<T> datas,Context context, int layoutResId){
        super(datas,context,layoutResId);
    }
}

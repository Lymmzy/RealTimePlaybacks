package com.lym.realtimeplayback.http;

import android.content.Context;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LYM on 2017/2/17.
 */

public abstract class spotsCallback<T> extends BaseCallBack<T> {
    private Context mContext;
    private SpotsDialog mDialog;
    public spotsCallback(Context mContext){
        this.mContext=mContext;
        mDialog =new SpotsDialog(mContext,"加载中");
    }

    public void showDialog(){
        mDialog.show();
    }

    public void dismissDialog(){
        if(mDialog!=null) {
            mDialog.dismiss();
        }
    }

    public void setMessage(String message){
        mDialog.setMessage(message);
    }

    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }
}

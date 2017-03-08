package com.lym.realtimeplayback.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LYM on 2017/2/16.
 */

public class OkHttpHelper {
    private OkHttpHelper() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static OkHttpClient okHttpClient;
    private static OkHttpHelper mInstance;
    private Handler mHandler;
    private Gson mGson;

    static {
        mInstance = new OkHttpHelper();
    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    public void doRequest(final Request request, final BaseCallBack callBack) {
        callBack.onRequestBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailure(callBack, request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                if (response.isSuccessful()) {
                    if (callBack.mType == String.class) {
                        callBackSuccess(callBack, response, resultStr);
                    } else {
                        try {
                            Object object = mGson.fromJson(resultStr, callBack.mType);
                            callBackSuccess(callBack, response, object);
                        } catch (JsonSyntaxException e) {
                            callBackError(callBack, response, response.code(), null);
                        }
                    }
                } else {
                    Log.i("LYM_TAG", "失败了");
                    callBackError(callBack, response, response.code(), null);
                }
                callBack.onResponse(response);
            }
        });

    }

    public void get(String url, BaseCallBack callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        doRequest(request, callback);
    }

    public void post(String url, Map<String, Object> params, BaseCallBack callback) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request, callback);
    }

    public Request buildRequest(String url, Map<String, Object> params, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody body = buildFormData(params);
            builder.post(body);

        }
        return builder.build();

    }

    private RequestBody buildFormData(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack baseCallBack, final Response response, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onSuccess(response, object);
            }
        });
    }

    private void callBackError(final BaseCallBack baseCallBack, final Response response, final int code, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onError(response, code, e);
            }
        });
    }

    private void callBackFailure(final BaseCallBack baseCallBack, final Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onFailure(request, e);
            }
        });
    }

    enum HttpMethodType {
        GET, POST
    }
}

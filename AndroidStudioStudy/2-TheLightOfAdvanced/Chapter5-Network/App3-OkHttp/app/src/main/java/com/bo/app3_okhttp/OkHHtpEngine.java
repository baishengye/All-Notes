package com.bo.app3_okhttp;

import android.content.Context;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Reader;

public class OkHHtpEngine {
    private static OkHHtpEngine instance;
    private final Context context;

    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHHtpEngine(Context context) {
        this.context=context;

        File sdCache = context.getExternalCacheDir();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        handler = new Handler();

    }

    public static OkHHtpEngine getInstance(Context context) {
        if(instance==null){
            synchronized (OkHttpClient.class){
                if(instance==null){
                    instance=new OkHHtpEngine(context);
                }
            }
        }
        return instance;
    }

    /**
     * 异步GET请求
     * @param url
     * @param callback
     */
    public void getAsyncHttp(String url,ResultCallback callback){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        dealResult(call,callback);
    }

    private void dealResult(Call call, ResultCallback callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                sendFailedCallback(call.request(),e,callback);
            }

            private void sendFailedCallback(Request request, IOException e, ResultCallback callback) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            callback.onError(request,e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                sendSuccessCallback(response,callback);
            }

            private void sendSuccessCallback(Response response, ResultCallback callback) {
                handler.post(new Runnable() {
                    //handler会把runnable中的代码post到主线程中运行，而不是在子线程中运行
                    @Override
                    public void run() {
                        if(callback!=null){
                            try {
                                callback.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

}

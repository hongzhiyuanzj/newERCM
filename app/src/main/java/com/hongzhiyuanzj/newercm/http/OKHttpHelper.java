package com.hongzhiyuanzj.newercm.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hongzhiyuanzj on 2017/4/24.
 */
public class OKHttpHelper {
    private static OKHttpHelper instance;
    private Handler mHandler;

    private OKHttpHelper(){
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OKHttpHelper getInstance(){
        if(instance==null){
            synchronized (OKHttpHelper.class){
                if(instance==null){
                    instance = new OKHttpHelper();
                }
            }

        }
        return instance;
    }


    public  void postAsycHttp(String url, Map<String, String> datas, final HttpUtils.HttpCallback callback){
        Log.e("URL",url);
        Log.e("param", datas.toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if(datas!=null) {
            for (String key : datas.keySet()) {
                builder.add(key, datas.get(key));
            }
        }
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    final String result = response.body().string();
                    Log.e("response", result);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }




}

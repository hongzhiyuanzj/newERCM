package com.hongzhiyuanzj.newercm.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hongzhiyuanzj on 2017/4/22.
 */
public class MyApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fresco.initialize(this);

    }

    public static Context getContext(){
        return context;
    }

}

package com.hongzhiyuanzj.newercm.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.entity.BookDetail;
import com.hongzhiyuanzj.newercm.ui.BookDetailActivity;

import java.util.Stack;

/**
 * Created by hongzhiyuanzj on 2017/4/22.
 */
public class MyApplication extends Application{
    private static MyApplication instance;
    private static Context context;
    private Stack<BaseActivity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        Fresco.initialize(this);
        createActivityStack();
    }
    private void createActivityStack(){

        activityStack = new Stack<>();
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return context;
    }


    public void pushActivity(BaseActivity baseActivity){

        activityStack.push(baseActivity);
    }

    public void popActivity(){
        activityStack.pop();
    }

    public void changeState(){
        for(BaseActivity activity: activityStack){
            if(activity instanceof BookDetailActivity){
                ((BookDetailActivity) activity).getTalk();
            }
            if(activity instanceof MainActivity){
                ((MainActivity) activity).initUI();
            }
        }

    }
}

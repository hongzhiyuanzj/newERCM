package com.hongzhiyuanzj.newercm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.application.MyApplication;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.view.LoadView;

import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private String Tag = getClass().getSimpleName();

    private LoadView loadView;

    protected View getLoadView(){
        return loadView.getLoadView();
    }

    protected void startLoading(){
        loadView.startLoading();
    }

    protected void stopLoading(){
        loadView.stopLoading();
    }

    protected FrameLayout.LayoutParams getLayoutParams(){ return loadView.getLayoutParams(); }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getApplication()).pushActivity(this);
        loadView = new LoadView(this);
    }


    protected void setLoadView(FrameLayout frameLayout){
        frameLayout.addView(getLoadView(), getLayoutParams());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication)getApplication()).popActivity();
    }
}

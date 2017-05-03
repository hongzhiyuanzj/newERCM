package com.hongzhiyuanzj.newercm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongzhiyuanzj.newercm.view.LoadView;

import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private LoadView loadView;

    public View getLoadView(){
        return loadView.getLoadView();
    }

    protected void startLoading(){
        loadView.startLoading();
    }

    protected void stopLoading(){
        loadView.stopLoading();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadView = new LoadView(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //session判断是否已经异地登录
    }

}

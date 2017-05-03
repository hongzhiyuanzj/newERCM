package com.hongzhiyuanzj.newercm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public class LoadView {

    private View loadView;
    private Context context;
    private boolean isVisiable;

    public LoadView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_load,null);
    }
    public void startLoading(){
        loadView.setVisibility(View.VISIBLE);
    }
    public void stopLoading(){
        loadView.setVisibility(View.GONE);
    }

    public View getLoadView(){
        return loadView;
    }
}

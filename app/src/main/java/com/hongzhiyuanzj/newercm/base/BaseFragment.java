package com.hongzhiyuanzj.newercm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongzhiyuanzj.newercm.view.LoadView;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public class BaseFragment extends Fragment{
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
    public void onAttach(Context context) {
        super.onAttach(context);
        loadView = new LoadView(context);
    }
}

package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class RecommendFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        ButterKnife.bind(this,view);
        return view;
    }
}

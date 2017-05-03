package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class LibraryFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, null);
        ButterKnife.bind(this, view);
        return view;
    }
}

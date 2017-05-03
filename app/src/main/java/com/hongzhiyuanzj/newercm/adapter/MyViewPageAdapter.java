package com.hongzhiyuanzj.newercm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.application.MyApplication;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class MyViewPageAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private FragmentManager fm;

    public MyViewPageAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}

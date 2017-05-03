package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.ui.LoginActivity;
import com.hongzhiyuanzj.newercm.util.Prefer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class MineFragment extends BaseFragment{

    private Handler handler = new Handler();
    @BindView(R.id.logout)Button logout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.logout)
    public void logout(){
        if(Prefer.isLogin()) {
            Prefer.setLogin(false);
            ((MainActivity)getActivity()).changePage(0);
        }else {
            LoginActivity.start(getContext());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init(){
        if(Prefer.isLogin()){
            logout.setText("退出登录");
        }else{
            logout.setText("登录");
        }
    }
}

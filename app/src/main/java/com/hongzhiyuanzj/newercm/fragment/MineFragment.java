package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.application.MyApplication;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.ui.ApplyActivity;
import com.hongzhiyuanzj.newercm.ui.LoginActivity;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class MineFragment extends BaseFragment{

    private Handler handler = new Handler();
    @BindView(R.id.logout)Button logout;
    @BindView(R.id.data_manage_img)ImageView data_img;
    @BindView(R.id.my_recommend_img)ImageView recommend;
    @BindView(R.id.apply_recognize_img)ImageView apply;
    @BindView(R.id.safe_setting_img)ImageView safe;
    @BindView(R.id.name)TextView name;
    @BindView(R.id.account)TextView account;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this,view);
        data_img.setImageDrawable(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_face_black_24dp), getResources().getColor(R.color.yellow)));
        recommend.setImageDrawable(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_favorite_black_24dp), getResources().getColor(R.color.red)));
        apply.setImageDrawable(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_touch_app_black_24dp), getResources().getColor(R.color.blue)));
        safe.setImageDrawable(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_new_releases_black_24dp), getResources().getColor(R.color.green)));
        init();
        return view;
    }
    @OnClick(R.id.logout)
    public void logout(){
        if(Prefer.isLogin()) {
            Prefer.setLogin(false);
            ((MainActivity)getActivity()).changePage(0);
            MyApplication.getInstance().changeState();
        }else {
            LoginActivity.start(getContext());
        }
    }

    public void init(){


        if(logout==null){
            return;
        }
        if(Prefer.isLogin()){
            logout.setText("退出登录");
            name.setText(Prefer.getUsername());
            account.setText(Prefer.getUserId());
        }else{
            logout.setText("登录");
            name.setText("未登录");
        }
    }

    @OnClick({R.id.data_container, R.id.recommend_container,R.id.apply_container, R.id.safe_container})
    public void click(View view){
        switch (view.getId()){
            case R.id.data_container:
                if(Utils.checkLogin((BaseActivity) getActivity())){
                    Utils.showToast("资料管理");
                }

                break;
            case R.id.recommend_container:
                if(Utils.checkLogin((BaseActivity) getActivity())){
                    Utils.showToast("我的荐购");
                }

                break;
            case R.id.apply_container:
                if(Utils.checkLogin((BaseActivity) getActivity())){
                    ApplyActivity.start(getContext());
                }
                break;
            case R.id.safe_container:
                if(Utils.checkLogin((BaseActivity) getActivity())){
                    Utils.showToast("安全设置");
                }
                break;
        }
    }

}

package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.AppURL;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/22.
 */
public class WebLoginActivity extends ToolbarActivity{

    @BindView(R.id.headphoto)ImageView headphoto;
    @BindView(R.id.nickname)TextView nickname;
    @BindView(R.id.tips)TextView tips;
    @BindView(R.id.web_login)Button web_login;


    public static final String WEB_LOGIN_INFO = "web_login_info";

    public static void start(Context context, String data){
        Intent intent = new Intent(context, WebLoginActivity.class);
        intent.putExtra(WEB_LOGIN_INFO, data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weblogin);
        ButterKnife.bind(this);
        setTitle(R.string.scanner_result);
        nickname.setText(Prefer.getUsername());
        tips.setText("你的账号于"+new SimpleDateFormat("MM月dd日hh时mm分").format(System.currentTimeMillis())+"申请在电脑登录");
    }

    @OnClick(R.id.web_login)
    public void webLogin(){
        String result = getIntent().getStringExtra(WEB_LOGIN_INFO);
        String id = result.replace("http://ercms.iegreen.net:80/client/Login?id=","");
        Log.e("id", id);
        HttpUtils.loginToWeb(id, Prefer.getUsername(), Prefer.getPassword(), new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    Utils.showToast(R.string.login_success);
                    finish();

                }else{
                    Utils.showToast(R.string.login_fail);
                }
            }
        });
    }
}

package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.MD5;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/26.
 */
public class RegisterActivity extends ToolbarActivity implements View.OnFocusChangeListener{

    @BindView(R.id.username)EditText username;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.mobile)EditText mobile;
    @BindView(R.id.certify)EditText certify;
    @BindView(R.id.register)Button register;
    @BindView(R.id.get_code)TextView get_code;


    public static void start(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(R.string.register);
        mobile.setCompoundDrawables(Utils.setDrawableTint(mobile.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.textPrimary)),null,null,null);
        username.setCompoundDrawables(Utils.setDrawableTint(username.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.textPrimary)),null,null,null);
        password.setCompoundDrawables(Utils.setDrawableTint(password.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.textPrimary)),null,null,null);
        certify.setCompoundDrawables(Utils.setDrawableTint(certify.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.textPrimary)),null,null,null);
        mobile.setOnFocusChangeListener(this);
        username.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        certify.setOnFocusChangeListener(this);

    }


    @OnClick(R.id.register)
    public void register(){
        if(username.getText().toString().equals("")){
            Utils.showToast("用户名不能为空");
            return;
        }
        if(password.getText().toString().equals("")){
            Utils.showToast("密码不能为空");
        }
        if(certify.getText().toString().equals("")){
            Utils.showToast("请输入验证码");
        }
        String md5Pssword = MD5.stringToMD5(password.getText().toString());
        HttpUtils.register(certify.getText().toString(), mobile.getText().toString(), username.getText().toString(), md5Pssword, new HttpUtils.HttpCallback() {

            @Override
            public void onFailure() {
                super.onFailure();
            }

            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    Utils.showToast(R.string.register_success);
                    finish();
                }else{
                    Utils.showToast(R.string.register_failure);
                }
            }
        });
    }

    private Handler mHandler = new Handler();
    private int counter = 60;
    private Runnable runable = new Runnable() {
        @Override
        public void run() {
            if(counter>0) {
                get_code.setText("已发送(" + counter-- + "秒)");
                get_code.setEnabled(false);
                mHandler.postDelayed(runable, 1000);
            }else{
                get_code.setText("获取验证码");
                get_code.setEnabled(true);
            }
        }
    };

    @OnClick(R.id.get_code)
    public void getCode(){

        String phonenumber = mobile.getText().toString();
//        Pattern pattern = Pattern.compile("[1-9]\\d{10}");
        if(phonenumber.length()!=11||phonenumber.charAt(0)!= '1'){
            Utils.showToast(R.string.please_enter_correct_number);
            return;
        }

        HttpUtils.isExistMobile(mobile.getText().toString(), new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {

                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    sendCode();
                }else{
                    Utils.showToast("该手机号已注册");
                }
            }
        });


    }

    private void sendCode(){
        HttpUtils.getCertifyCode(mobile.getText().toString(), new HttpUtils.HttpCallback() {

            @Override
            public void onFailure() {
                Utils.showToast("请检查网络是否连接");
            }

            @Override
            public void onSuccess(String json) {
                mHandler.postDelayed(runable, 1000);
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    Prefer.saveSessionId(result.getSessionId());
                    Utils.showToast(R.string.send_code_success);
                }else{
                    Utils.showToast(R.string.send_code_failure);
                }

            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText)v;
        if(hasFocus){
            editText.setCompoundDrawables(Utils.setDrawableTint(editText.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.colorAccent)),null,null,null);
        }else{
            editText.setCompoundDrawables(Utils.setDrawableTint(editText.getCompoundDrawables()[0].mutate(), getResources().getColor(R.color.textPrimary)),null,null,null);
        }
    }
}

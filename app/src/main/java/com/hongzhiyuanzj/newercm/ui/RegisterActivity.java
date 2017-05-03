package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.MD5;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/26.
 */
public class RegisterActivity extends ToolbarActivity{

    @BindView(R.id.username)EditText username;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.mobile)EditText mobile;
    @BindView(R.id.certify)EditText certify;
    @BindView(R.id.register)Button register;
    @BindView(R.id.get_code)Button get_code;
    public static void start(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(R.string.register);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    get_code.setClickable(true);
                }else{
                    get_code.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        certify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && !username.getText().toString().equals("")){
                    register.setClickable(true);
                }else{
                    get_code.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        register.setClickable(false);
        get_code.setClickable(false);
    }

    @OnClick(R.id.register)
    public void register(){
        String md5Pssword = MD5.stringToMD5(password.getText().toString());
        HttpUtils.register(certify.getText().toString(), mobile.getText().toString(), username.getText().toString(), md5Pssword, new HttpUtils.HttpCallback() {
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

    @OnClick(R.id.get_code)
    public void getCode(){
        String phonenumber = mobile.getText().toString();
//        Pattern pattern = Pattern.compile("[1-9]\\d{10}");
        if(phonenumber.length()!=11||phonenumber.charAt(0)== '1'){
            Utils.showToast(R.string.please_enter_correct_number);
            return;
        }
        HttpUtils.getCertifyCode(mobile.getText().toString(), new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Log.e("json", json);
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    Utils.showToast(R.string.send_code_success);
                }else{
                    Utils.showToast(R.string.send_code_failure);
                }
            }
        });
    }
}

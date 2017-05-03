package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.AppURL;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.MD5;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class LoginActivity extends ToolbarActivity {
    private String Tag = getClass().getSimpleName();

    @BindView(R.id.register)TextView register;
    @BindView(R.id.username)EditText username;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.certify)EditText certify;
    @BindView(R.id.login)Button login;
    @BindView(R.id.code_pic)SimpleDraweeView code_pic;
    @BindView(R.id.headphoto)SimpleDraweeView simpleDraweeView;


    public static void start(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && !password.getText().toString().equals("")){
                    login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && !username.getText().toString().equals("")){
                    login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login.setEnabled(false);
        setTitle(R.string.login);
        getCodeImage();
        username.setText(Prefer.getUsername());
        register.setEnabled(false);
    }

    @OnClick(R.id.login)
    public void login(){
        final String md5Password = MD5.stringToMD5(password.getText().toString());
        Log.e("MD5password", md5Password);
        HttpUtils.login(username.getText().toString(), md5Password, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0) {
                    Prefer.setLogin(true);
                    Prefer.savePassword(md5Password);
                    Prefer.saveUsername(username.getText().toString());
                    Prefer.saveSessionId(result.getSessionId());
                    finish();
                }else{
                    Utils.showToast(result.getMessage());
                }

            }
        });
    }

    @OnClick(R.id.register)
    public void register(){
        RegisterActivity.start(this);
    }

    @OnClick(R.id.code_pic)
    public void getCodePic(){
        getCodeImage();
    }

    private void getCodeImage(){
        Log.e("LoginActivity", "getCode");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(AppURL.getPicCode)
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.evictFromCache(Uri.parse(AppURL.getPicCode));
                    }
                })
                .build();
        code_pic.setController(controller);
    }

}

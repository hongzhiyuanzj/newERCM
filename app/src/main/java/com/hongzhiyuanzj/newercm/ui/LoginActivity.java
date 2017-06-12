package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.application.MyApplication;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.FileHelper;
import com.hongzhiyuanzj.newercm.util.MD5;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class LoginActivity extends ToolbarActivity{

    public static final int LOGIN_REQUEST = 1000;

    @BindView(R.id.register)TextView register;
    @BindView(R.id.username)EditText username;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.certify)EditText certify;
    @BindView(R.id.login)Button login;
    @BindView(R.id.code_pic)SimpleDraweeView code_pic;
    @BindView(R.id.headphoto)SimpleDraweeView headphoto;

    @BindView(R.id.linearlayout)LinearLayout linearLayout;

    public static void startForResult(BaseActivity activity){

        activity.startActivityForResult(new Intent(activity, LoginActivity.class), LOGIN_REQUEST);
    }

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
        username.setText(Prefer.getUserId());

//        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                //获取View可见区域的bottom
//                Log.e("bottom", bottom+"");
//                Log.e("oldBottom", oldBottom+"");
//                Rect rect = new Rect();
//                scrollHeight = (int)headphoto.getY()+headphoto.getHeight();
//
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
//                    linearLayout.scrollTo(0, 0);
//                }else {
//                    linearLayout.scrollTo(0, scrollHeight);
//                }
//            }
//        });
        FileHelper.getInstance().clearCache();
        headphoto.setImageURI(FileHelper.getInstance().getHeadphoto());
    }



    @OnClick(R.id.login)
    public void login(){
        final String md5Password = MD5.stringToMD5(password.getText().toString());
        HttpUtils.login(username.getText().toString(), md5Password, new HttpUtils.HttpCallback() {

            @Override
            public void onSuccess(String json) {
                final Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0) {
                    Prefer.setLogin(true);
                    Prefer.savePassword(md5Password);
                    Prefer.saveUserId(username.getText().toString());
                    Prefer.saveSessionId(result.getSessionId());
                    MyApplication.getInstance().changeState();
                    HttpUtils.getUserInfo(result.getOpenId(), new HttpUtils.HttpCallback() {
                        @Override
                        public void onSuccess(String json) {
                            Result result1 = JSON.parseObject(json, Result.class);
                            if(result.getCode()==0){
                                Prefer.saveUsername(result1.getUserName());
                                finish();
                            }else{
                                Utils.showToast("网络连接错误");
                            }

                        }
                    });

                }else{
                    Utils.showToast("用户名密码错误");
                }
            }
        });
    }

    @OnClick(R.id.register)
    public void register(){
        RegisterActivity.start(this);
    }

    private int scrollHeight;

}

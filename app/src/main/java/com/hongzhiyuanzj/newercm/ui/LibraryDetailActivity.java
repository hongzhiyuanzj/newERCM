package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Library;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/5/17.
 */
public class LibraryDetailActivity extends ToolbarActivity implements View.OnFocusChangeListener{
    public static final String LIBRARY = "library";
    private Library library;

    @BindView(R.id.detail)TextView detail;
    @BindView(R.id.account)EditText account;
    @BindView(R.id.password)EditText password;


    public static void start(Context context, Library library){
        Intent intent = new Intent(context, LibraryDetailActivity.class);
        intent.putExtra(LIBRARY, library);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);
        ButterKnife.bind(this);
        library = (Library) getIntent().getSerializableExtra(LIBRARY);
        setTitle(library.getLibName());
        init();
    }

    private void init(){
        detail.setText("图书馆名称："+ Utils.filterData(library.getLibName())+"\n"
                    +"管理系统："+ Utils.filterData(library.getLibSystem())+"\n"
                    +"联系地址："+ Utils.filterData(library.getAddress())+"\n"
                    +"联系电话："+ Utils.filterData(library.getTelephone())+"\n"
                    +"联系人：" + Utils.filterData(library.getStaff()));
        account.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
    }

    @OnClick(R.id.identify)
    public void identify(){
        HttpUtils.identify(library.getLibraryId(), Values.TIDE_AUTO, account.getText().toString(), password.getText().toString(), new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0){
                    Utils.showToast(R.string.apply_success);
                    finish();
                }else if(result.getCode()==1){
                    Utils.showToast("此身份已绑定");
                }else{
                    Utils.showToast(R.string.apply_failure);
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

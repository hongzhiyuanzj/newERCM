package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.LibraryListAdapter;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.entity.Library;
import com.hongzhiyuanzj.newercm.entity.LibraryList;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/5/17.
 */
public class LibrarySearchActivity extends BaseActivity{

    @BindView(R.id.search)ImageView search;
    @BindView(R.id.input)EditText input;
    @BindView(R.id.framelayout)FrameLayout frameLayout;
    @BindView(R.id.result)RecyclerView result;
    @BindView(R.id.toolbar)Toolbar toolbar;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private LibraryListAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context, LibrarySearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_search);
        ButterKnife.bind(this);
        setLoadView(frameLayout);
        result.setLayoutManager(new LinearLayoutManager(this));
        result.addItemDecoration(new DividerItemDecoration(this));
        initToolbar();
    }

    private void initToolbar(){
        input.setHint(R.string.library);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.search)
    public void search(){
        datas.clear();
        startLoading();
        Utils.hideSoftInput(this);
        HttpUtils.getLibraryList(input.getText().toString(), new HttpUtils.HttpCallback() {

            @Override
            public void onFailure() {
                super.onFailure();
                stopLoading();
            }

            @Override
            public void onSuccess(String json) {
                LibraryList libraryList = JSON.parseObject(json, LibraryList.class);
                if(libraryList.getCode()==0){
                    List<Library> libraries = libraryList.getResult();
                    if(libraries!=null){
                        addData(libraries);
                        adapter = new LibraryListAdapter(datas, LibrarySearchActivity.this);
                        result.setAdapter(adapter);
                    }else{
                        Utils.showToast(R.string.not_find_result);
                    }

                }else{

                }
                stopLoading();

            }
        });

    }

    public void addData(List<Library> libraryList){
        HashMap<String, Object> map = null;
        for(Library library: libraryList){
            map = new HashMap<>();
            map.put(LibraryListAdapter.NAME, library.getLibName());
            map.put(LibraryListAdapter.LIBRARY, library);
            datas.add(map);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            search();
        }
        return super.onKeyDown(keyCode, event);
    }

}

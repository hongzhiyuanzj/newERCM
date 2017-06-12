package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BookListAdapter;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.entity.BookInfo;
import com.hongzhiyuanzj.newercm.entity.BookList;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/27.
 */
public class SearchActivity extends BaseActivity{
    public static String SEARCH_PARAM = "search_param";

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.search_result)RecyclerView result;
    @BindView(R.id.search)ImageView search;
    @BindView(R.id.input)EditText input;

    @BindView(R.id.framelayout)FrameLayout frameLayout;

    private int page;
    private ActionBar actionBar;
    private RecyclerViewApdaterWrapper adapter;
    private List<HashMap<String, Object>> datas = new ArrayList<>();

    public static void start(Context context){
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    public static void start(Context context, String param){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SEARCH_PARAM, param);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setLoadView(frameLayout);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        search.setImageDrawable(Utils.setDrawableTint(search.getDrawable(), getResources().getColorStateList(R.color.backarrow_color_selector)));
        result.setLayoutManager(new LinearLayoutManager(this));
        result.addItemDecoration(new DividerItemDecoration(this));
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().getStringExtra(SEARCH_PARAM)!=null){
            String param = getIntent().getStringExtra(SEARCH_PARAM);
            input.setText(param);
            Utils.hideSoftInput(this);
            search();
        }
        result.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!result.canScrollVertically(1)){
                    loadData();
                }
            }
        });
    }

    @OnClick(R.id.search)
    public void search(){
        startLoading();
        page = Values.startPage;
        if(adapter!=null) {
            datas.clear();
            adapter.setFootVisiable(View.VISIBLE);
        }
        Utils.hideSoftInput(this);
        HttpUtils.getBookList(page, input.getText().toString(), new HttpUtils.HttpCallback() {

            @Override
            public void onFailure() {
                super.onFailure();
                stopLoading();
            }

            @Override
            public void onSuccess(String json) {

                BookList bookList = JSON.parseObject(json, BookList.class);
                if (bookList.getStatus().equals("OK")) {
                    //成功返回结果
                    if(bookList.getList()!=null) {
                        //结果不为空
                        addData(bookList);
                        adapter = new RecyclerViewApdaterWrapper(new BookListAdapter(datas, SearchActivity.this));
                        result.setAdapter(adapter);

                    }else{
                        //结果为空
                        Utils.showToast(R.string.not_find_result);
                    }
                }else{
                    //返回失败
                }
                stopLoading();
            }
        });
    }

    private void addData(BookList bookList){
        HashMap<String, Object> map = null;
        for (BookInfo bookInfo : bookList.getList()) {
            map = new HashMap<String, Object>();
            map.put(BookListAdapter.PostURL, bookInfo.getImgPath());
            map.put(BookListAdapter.BookName, bookInfo.getTitle());
            map.put(BookListAdapter.Author, bookInfo.getAuthor());
            map.put(BookListAdapter.BookInfo, bookInfo.getSummary());
            map.put(BookListAdapter.BookId, bookInfo.getBookId());
            datas.add(map);
        }
    }

    //上拉加载数据
    private void loadData(){
        page++;
        HttpUtils.getBookList(page, input.getText().toString(), new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                BookList bookList = JSON.parseObject(json, BookList.class);
                if (bookList.getStatus().equals("OK")) {
                    //成功返回结果
                    if(bookList.getList()!=null) {
                        //结果不为空
                        addData(bookList);
                        adapter.notifyDataSetChanged();
                    }else{
                        //结果为空
                        adapter.setFootVisiable(View.GONE);
                    }
                }else{
                    //返回失败
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.hideSoftInput(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("keyCode", keyCode+"");
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            search();
        }
        return true;
    }
}

package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BookListAdapter;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.RecommendListAdapter;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.adapter.TalkListAdapter;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Recommend;
import com.hongzhiyuanzj.newercm.entity.RecommendList;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.entity.Talk;
import com.hongzhiyuanzj.newercm.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/15.
 */
public class RecommendListActivity extends ToolbarActivity{

    public static final String RDID = "rdid";

    @BindView(R.id.framelayout)FrameLayout frameLayout;
    @BindView(R.id.book_list)RecyclerView book_list;



    private int page = Values.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewApdaterWrapper adapter;

    public static void start(Context context, int rdId){
        Intent intent = new Intent(context, RecommendListActivity.class);
        intent.putExtra(RDID, rdId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        setLoadView(frameLayout);
        setTitle(R.string.recommend_list);
        book_list.setLayoutManager(new LinearLayoutManager(this));
        book_list.addItemDecoration(new DividerItemDecoration(this));
        book_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!book_list.canScrollVertically(1)){
                    loadData();
                }
            }
        });
        getBookList();
    }

    private void getBookList(){
        page = Values.startPage;
        HttpUtils.getRecommendList(getIntent().getIntExtra(RDID, 0), page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                RecommendList result = JSON.parseObject(json, RecommendList.class);
                if(result.getCode()==0){
                    if(result.getResult()!=null&&result.getResult().size()!=0){
                        addData(result);
                        adapter = new RecyclerViewApdaterWrapper(new RecommendListAdapter(RecommendListActivity.this, datas));
                        book_list.setAdapter(adapter);
                    }else{
                        adapter.setFootVisiable(View.GONE);
                    }
                }else{

                }

            }
        });

    }

    private void loadData(){
        page++;
        HttpUtils.getRecommendList(getIntent().getIntExtra(RDID, 0), page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                RecommendList result = JSON.parseObject(json, RecommendList.class);
                if(result.getCode()==0){
                    if(result.getResult()!=null&&result.getResult().size()!=0){
                        addData(result);
                        adapter.notifyDataSetChanged();
                    }else{
                        adapter.setFootVisiable(View.GONE);
                    }
                }else{

                }

            }
        });

    }

    private void addData(RecommendList recommendList){
        HashMap<String, Object> map = null;
        for (Recommend recommend : recommendList.getResult()) {
            map = new HashMap<String, Object>();
            String isbn = null;
            if(recommend.getIsbn()!=null&&recommend.getIsbn().size()!=0&&!recommend.getIsbn().get(0).equals("")){
                isbn = recommend.getIsbn().get(0);
            }else{
                isbn = getResources().getString(R.string.none);
            }
            map.put(RecommendListAdapter.TITLE, recommend.getTitle());
            map.put(RecommendListAdapter.PRESS, recommend.getPress());
            map.put(RecommendListAdapter.PUB_DATE, recommend.getPubData());
            map.put(RecommendListAdapter.ISBN, isbn);
            map.put(RecommendListAdapter.AUTHOR, recommend.getAuthor());
            map.put(RecommendListAdapter.SUMMARY, recommend.getSummary());
            datas.add(map);
        }
    }
}

package com.hongzhiyuanzj.newercm.ui;

import android.app.Activity;
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
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.adapter.TalkListAdapter;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.entity.Talk;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/12.
 */
public class TalkListActivity extends ToolbarActivity{

    public static final String BOOKID = "bookid";

    @BindView(R.id.talk_list)RecyclerView talk_list;
    @BindView(R.id.framelayout)FrameLayout frameLayout;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewApdaterWrapper adapter;
    private int page = Values.startPage;

    public static void start(Context context, String bookid){
        Intent intent = new Intent(context, TalkListActivity.class);
        intent.putExtra(BOOKID, bookid);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talklist);
        ButterKnife.bind(this);
        talk_list.setLayoutManager(new LinearLayoutManager(this));
        talk_list.addItemDecoration(new DividerItemDecoration(this));
        talk_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!talk_list.canScrollVertically(1)){
                    loadData();
                }

            }
        });
        setTitle(R.string.talk);
        setLoadView(frameLayout);
        getTalkList();
    }

    private void getTalkList(){
        startLoading();
        datas.clear();
        page = Values.startPage;
        HttpUtils.getTalkList(getIntent().getStringExtra(BOOKID), page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result talkList = JSON.parseObject(json, Result.class);
                List<Talk> talks = talkList.getResult();
                if(talks!=null) {
                    addData(talkList);
                    adapter = new RecyclerViewApdaterWrapper(new TalkListAdapter(TalkListActivity.this, datas));
                    talk_list.setAdapter(adapter);
                }else {
                    Utils.showToast("暂无评论");
                }
                stopLoading();

            }
        });
    }

    private void loadData(){
        page++;
        HttpUtils.getTalkList(getIntent().getStringExtra(BOOKID), page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result talkList = JSON.parseObject(json, Result.class);
                List<Talk> talks = talkList.getResult();
                if(talks!=null&&talks.size()!=0) {
                    addData(talkList);
                    adapter.notifyDataSetChanged();

                }else {

                    adapter.setFootVisiable(View.GONE);
                }


            }
        });

    }

    private void addData(Result talkList){
        HashMap<String, Object> map = null;
        for (Talk talk : talkList.getResult()) {
            map = new HashMap<String, Object>();
            map.put(TalkListAdapter.UserName, talk.getUserName());
            map.put(TalkListAdapter.Talk, talk.getContent());
            map.put(TalkListAdapter.Time, talk.getTime());
            map.put(TalkListAdapter.Rating,talk.getRating());
            map.put(TalkListAdapter.Count, talk.getVotecount());
            map.put(TalkListAdapter.CommentId, talk.getCommentId());
            map.put(TalkListAdapter.State, talk.getStatus());
            datas.add(map);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LoginActivity.LOGIN_REQUEST && resultCode==RESULT_OK){
            getTalkList();
        }
    }
}

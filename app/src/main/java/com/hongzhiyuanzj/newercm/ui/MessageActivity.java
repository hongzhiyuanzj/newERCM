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
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.MessageListAdapter;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.adapter.TalkListAdapter;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Message;
import com.hongzhiyuanzj.newercm.entity.MessageList;
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
 * Created by hongzhiyuanzj on 2017/6/5.
 */
public class MessageActivity extends ToolbarActivity{

    @BindView(R.id.message_list)RecyclerView message_list;
    @BindView(R.id.framelayout)FrameLayout frameLayout;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewApdaterWrapper adapter;
    private int page = Values.startPage;


    public static void start(Context context){
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setTitle(R.string.message);

        message_list.setLayoutManager(new LinearLayoutManager(this));
        message_list.addItemDecoration(new DividerItemDecoration(this));
        message_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!message_list.canScrollVertically(1)){
                    loadData();
                }

            }
        });

        setLoadView(frameLayout);
        getMessageList();
    }


    private void getMessageList(){
        startLoading();
        datas.clear();
        page = Values.startPage;
        HttpUtils.getMessageList(page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                MessageList messageList = JSON.parseObject(json, MessageList.class);

                List<Message> messages = messageList.getResult();
                if(messages!=null) {
                    addData(messageList);
                    adapter = new RecyclerViewApdaterWrapper(new MessageListAdapter(datas, MessageActivity.this));
                    message_list.setAdapter(adapter);
                }else {
                    Utils.showToast("暂无推送消息");
                }
                stopLoading();

            }
        });
    }

    private void loadData(){
        page++;
        HttpUtils.getMessageList(page, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                MessageList messageList = JSON.parseObject(json, MessageList.class);
                List<Message> messages = messageList.getResult();
                if(messages!=null&&messages.size()!=0) {
                    addData(messageList);
                    adapter.notifyDataSetChanged();

                }else {

                    adapter.setFootVisiable(View.GONE);
                }


            }
        });

    }

    private void addData(MessageList messageList){
        HashMap<String, Object> map = null;
        for (Message message : messageList.getResult()) {
            map = new HashMap<String, Object>();
            map.put(MessageListAdapter.Id, message.getMsgId());
            map.put(MessageListAdapter.Content, message.getMsgContent());
            map.put(MessageListAdapter.Time, message.getMsgTime());
            map.put(MessageListAdapter.Title,message.getMsgTime());
            map.put(MessageListAdapter.Important, message.getMsgType());
            datas.add(map);
        }
    }
}

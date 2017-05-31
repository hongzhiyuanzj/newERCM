package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.fragment.TideDialog;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;
import com.hongzhiyuanzj.newercm.view.MyRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/12.
 */
public class RatingActivity extends ToolbarActivity{

    public static String BOOK_ID = "bookid";
    @BindView(R.id.result)TextView result;
//    @BindView(R.id.ratingbar)RatingBar ratingBar;
    @BindView(R.id.ratingbar)MyRatingBar ratingBar;
    @BindView(R.id.input)EditText input;

    public static void start(Context context,String bookid){
        Intent intent = new Intent(context, RatingActivity.class);
        intent.putExtra(BOOK_ID, bookid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        getToolbar().setNavigationIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_close_black_24dp),getResources().getColor(R.color.textIcon)));
        setTitle(R.string.talk);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commit, menu);
        MenuItem commit = menu.findItem(R.id.commit);
        commit.setIcon(Utils.setDrawableTint(commit.getIcon(),getResources().getColor(R.color.textIcon)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.commit:
                talk();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void talk(){
        if(input.getText().toString().equals("")){
            Utils.showToast("请输入评论");
            return;
        }
        HttpUtils.commitTalk(getIntent().getStringExtra(BOOK_ID), ratingBar.getRate(), input.getText().toString(), new HttpUtils.HttpCallback() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json,Result.class);
                if(result.getCode()==0){
//                    Utils.showToast(R.string.talk_sccuess);
                    Utils.showToast("评论正在审核");
                    finish();
                }else if(result.getCode()==1){
                    TideDialog.showDialog(RatingActivity.this);
                }else{
                    Utils.showToast(R.string.talk_failure);
                }
            }
        });
    }
}

package com.hongzhiyuanzj.newercm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BookListAdapter;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/4/27.
 */
public class SearchActivity extends BaseActivity{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.search_result)RecyclerView result;
    @BindView(R.id.search)ImageView search;

    private ActionBar actionBar;
    private BookListAdapter adapter;

    public static void start(Context context){
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        search.setImageDrawable(Utils.setDrawableTint(search.getDrawable(), getResources().getColorStateList(R.color.backarrow_color_selector)));

        result.setLayoutManager(new LinearLayoutManager(this));
        result.setAdapter(adapter = new BookListAdapter(Utils.createData(
                new String[]{BookListAdapter.PostURL,BookListAdapter.BookName,BookListAdapter.BookInfo,BookListAdapter.Author },
                new String[]{"", "人民的正义","拍苍蝇，打老虎，喜大普奔","周梅森"}, 20), this));
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

    }

}

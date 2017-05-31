package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/22.
 */
public class ReadingActivity extends ToolbarActivity{
    public static final String URL = "url";
    public static final String BOOKNAME = "bookname";

    @BindView(R.id.webview)WebView webView;

    public static void start(Context context, String url,String bookname){
        Intent intent = new Intent(context, ReadingActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(BOOKNAME, bookname);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra(BOOKNAME));
        webView.loadUrl(getIntent().getStringExtra(URL));
    }
}

package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/6/5.
 */
public class MessageDetailActivity extends ToolbarActivity{

    private static String Title = "title";
    private static String Content = "content";
    private static String Time = "time";
    private static String Important = "important";
    private static String Id = "id";

    @BindView(R.id.content)TextView content;
    @BindView(R.id.important)View important;
    @BindView(R.id.title)TextView title;
    @BindView(R.id.time)TextView time;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");

    public static void start(Context context, String content, String title, long time, int id, int important){
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra(Title, title);
        intent.putExtra(Content, content);
        intent.putExtra(Time, time);
        intent.putExtra(Id, id);
        intent.putExtra(Important, important);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        setTitle(R.string.message);

        int color = 0;
        switch (getIntent().getIntExtra(Important, 0)){
            case 0:
                color = R.color.green;
                break;
            case 1:
                color = R.color.red;
                break;
            case 2:
                color = R.color.blue;
                break;
            default:
                break;
        }
        important.setBackgroundColor(getResources().getColor(color));
        content.setText(getIntent().getStringExtra(Content));
        title.setText(getIntent().getStringExtra(Title));
        time.setText(format.format(getIntent().getLongExtra(Time, 0)));
    }
}

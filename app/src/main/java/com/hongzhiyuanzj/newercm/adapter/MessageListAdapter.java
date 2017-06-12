package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.ui.BookDetailActivity;
import com.hongzhiyuanzj.newercm.ui.MessageDetailActivity;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/6/5.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{


    public static String Title = "title";
    public static String Content = "content";
    public static String Time = "time";
    public static String Important = "important";
    public static String Id = "id";

    private SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public MessageListAdapter(List<HashMap<String, Object>> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_message, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetailActivity.start(context,
                        (String)mDatas.get(position).get(Content),
                        (String)mDatas.get(position).get(Title),
                        (Long)mDatas.get(position).get(Time),
                        (Integer)mDatas.get(position).get(Id),
                        (Integer)mDatas.get(position).get(Important));
            }
        });
        int color = 0;
        switch ((Integer)mDatas.get(position).get(Important)){
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
        holder.important.setBackgroundColor(context.getResources().getColor(color));
        holder.content.setText((String)mDatas.get(position).get(Content));
        holder.time.setText(format.format((Long)mDatas.get(position).get(Time)));
        holder.title.setText((String)mDatas.get(position).get(Title));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.listitem)View listitem;
        @BindView(R.id.title)TextView title;
        @BindView(R.id.content)TextView content;
        @BindView(R.id.time)TextView time;
        @BindView(R.id.important)View important;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/16.
 */
public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder>{

    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String ISBN = "isbn";
    public static final String PUB_DATE = "pubdate";
    public static final String PRESS = "press";
    public static final String SUMMARY = "summary";

    private Context context;
    private List<HashMap<String,Object>> mDatas;


    public RecommendListAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_recommend, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText((String)mDatas.get(position).get(TITLE));
        holder.summary.setText("摘　要："+Utils.filterData((String)mDatas.get(position).get(SUMMARY)));
        holder.other.setText(context.getResources().getString(R.string.author)+ Utils.filterData((String)mDatas.get(position).get(AUTHOR))+"\n" +
                            "出版社："+Utils.filterData((String)mDatas.get(position).get(PRESS))+"\n"+
                            "出版日："+new SimpleDateFormat("yyyy-MM-dd").format(mDatas.get(position).get(PUB_DATE))+"\n" +
                            "书　号："+Utils.filterData((String)mDatas.get(position).get(ISBN))
                            );
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title)TextView title;
        @BindView(R.id.summary)TextView summary;
        @BindView(R.id.other)TextView other;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

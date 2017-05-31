package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.ui.ReadingActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/25.
 */
public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ViewHolder>{

    public static final String LIBNAME = "libname";
    public static final String URL = "url";

    private List<HashMap<String, Object>> mDatas;
    private Context context;
    private String bookname;

    public ReadAdapter(List<HashMap<String, Object>> mDatas, Context context, String bookname) {
        this.mDatas = mDatas;
        this.context = context;
        this.bookname = bookname;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_read_link, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.libname.setText((String)mDatas.get(position).get(LIBNAME));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadingActivity.start(context, (String)mDatas.get(position).get(URL), bookname);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.lib_container)LinearLayout container;
        @BindView(R.id.libname)TextView libname;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

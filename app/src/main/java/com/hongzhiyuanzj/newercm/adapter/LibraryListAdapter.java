package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.entity.Library;
import com.hongzhiyuanzj.newercm.ui.LibraryDetailActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/18.
 */
public class LibraryListAdapter extends RecyclerView.Adapter<LibraryListAdapter.ViewHolder>{

    public static final String NAME="name";
    public static final String LIBRARY="library";

    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public LibraryListAdapter(List<HashMap<String, Object>> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_library, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText((String)mDatas.get(position).get(NAME));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryDetailActivity.start(context, (Library)mDatas.get(position).get(LIBRARY));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)TextView name;
        @BindView(R.id.container)LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

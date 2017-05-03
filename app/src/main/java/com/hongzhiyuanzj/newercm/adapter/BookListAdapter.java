package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/27.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{

    public static String BookName = "book_name";
    public static String PostURL = "post_url";
    public static String Author = "author";
    public static String BookInfo = "book_info";

    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public BookListAdapter(List<HashMap<String, Object>> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_book, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.book_name.setText((String)mDatas.get(position).get(BookName));
        holder.author.setText(context.getResources().getString(R.string.author)+(String)mDatas.get(position).get(Author));
        holder.info.setText((String)mDatas.get(position).get(BookInfo));
        holder.poster.setImageDrawable(context.getResources().getDrawable(R.drawable.exp));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)SimpleDraweeView poster;
        @BindView(R.id.book_name)TextView book_name;
        @BindView(R.id.author)TextView author;
        @BindView(R.id.info)TextView info;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class BookListDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

}

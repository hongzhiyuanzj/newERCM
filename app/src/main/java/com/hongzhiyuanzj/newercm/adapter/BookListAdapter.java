package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.ui.BookDetailActivity;
import com.hongzhiyuanzj.newercm.util.Utils;

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
    public static String BookId = "book_id";

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.start(context, (String)mDatas.get(position).get(BookId),(String)mDatas.get(position).get(BookName));
            }
        });

        holder.book_name.setText(Utils.setTextColor((String)mDatas.get(position).get(BookName)));
        holder.author.setText(Utils.setTextColor(context.getResources().getString(R.string.author)+(String)mDatas.get(position).get(Author)));
        holder.info.setText(Utils.setTextColor((String)mDatas.get(position).get(BookInfo)));
        holder.poster.setImageURI((String)mDatas.get(position).get(PostURL));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.listitem)View listitem;
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
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Context context;
        private Drawable mDrawable;
        public BookListDecoration(Context context){
            TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDrawable = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth()-parent.getPaddingRight();
            for(int i=0;i<childCount;i++){
                View child = parent.getChildAt(i);
                int top = child.getBottom();
                int bottom = top+mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }


        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        }
    }

}

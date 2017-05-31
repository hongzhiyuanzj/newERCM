package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.entity.BookDetail;
import com.hongzhiyuanzj.newercm.ui.BookDetailActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder>{

    public static String BookName = "name";
    public static String BookAuthor = "author";
    public static String BookId = "bookid";
    public static String BookImg = "bookimg";

    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public BooksAdapter(List<HashMap<String, Object>> mDatas,Context context){
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_book, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mDatas.get(position).get(BookName)!=null) {
                BookDetailActivity.start(context, (String) mDatas.get(position).get(BookId), (String) mDatas.get(position).get(BookName));
            }
            }
        });
        holder.poster.setImageURI((String)mDatas.get(position).get(BookImg));
        holder.name.setText((String)mDatas.get(position).get(BookName));
        holder.author.setText((String)mDatas.get(position).get(BookAuthor));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)
        SimpleDraweeView poster;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.container)
        LinearLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class BookItemDecoration extends RecyclerView.ItemDecoration{
        private Context context;
        private Drawable drawable;

        public BookItemDecoration(Context context){
            this.context = context;
            drawable = context.getResources().getDrawable(R.color.textIcon);
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            int margin = context.getResources().getDimensionPixelOffset(R.dimen.activity_main_margin);
            switch (itemPosition % 3) {
                case 0:
                    outRect.set(0, 0, margin / 2, margin);
                    if(itemPosition/3==0){
                        outRect.set(0, margin, margin/2,margin);
                    }
                    break;
                case 1:
                    outRect.set(margin / 4, 0, margin / 4, margin);
                    if(itemPosition/3==0){
                        outRect.set(margin / 4, margin, margin / 4,margin);
                    }
                    break;
                case 2:
                    outRect.set(margin / 2, 0, 0, margin);
                    if(itemPosition/3==0){
                        outRect.set(margin / 2, margin, 0,margin);
                    }
                    break;
            }
        }
    }
}

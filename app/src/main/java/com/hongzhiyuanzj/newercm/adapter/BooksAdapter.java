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

import com.hongzhiyuanzj.newercm.R;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.poster.setImageDrawable(context.getResources().getDrawable(R.drawable.exp));

        holder.name.setText((String)mDatas.get(position).get(BookName));
        holder.author.setText((String)mDatas.get(position).get(BookAuthor));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.author)
        TextView author;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class BookItemDecoration extends RecyclerView.ItemDecoration{
        private Drawable mHorizontalDivider;
        private Drawable mVerticalDivider;

        public BookItemDecoration(Context context){
            mHorizontalDivider = context.getResources().getDrawable(R.drawable.book_divider_shape);
//            mVerticalDivider = context.getResources().getDrawable(R.drawable.)

        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }

        private void drawHorizontal(Canvas canvas, RecyclerView parent){
            int left = parent.getPaddingLeft();
            int right = parent.getWidth()-parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for(int i=0;i<childCount;i++){
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom()+params.bottomMargin;
                int bottom = top+mHorizontalDivider.getIntrinsicHeight();
                mHorizontalDivider.setBounds(left, top, right, bottom);
                mHorizontalDivider.draw(canvas);
            }
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, mHorizontalDivider.getIntrinsicHeight());
        }

        private void drawVertical(Canvas canvas, RecyclerView parent){

        }
    }
}

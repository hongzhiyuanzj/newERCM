package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.entity.Shelf;
import com.hongzhiyuanzj.newercm.fragment.ShelfFragment;
import com.hongzhiyuanzj.newercm.ui.BookDetailActivity;
import com.hongzhiyuanzj.newercm.util.ShelfHelper;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/24.
 */
public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.MyViewHolder>{

    public static String BookName = "name";
    public static String BookAuthor = "author";
    public static String BookId = "bookid";
    public static String BookImg = "bookimg";

    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public ShelfAdapter(List<HashMap<String, Object>> mDatas,Context context){
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
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteData(position);
                return true;
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

    public void deleteData(int position){
        ShelfHelper.deleteShelf((String)(mDatas).get(position).get(BookId));
        mDatas.remove(mDatas.get(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size()-position);
        if(mDatas.size()==0){
            ((ShelfFragment)(((MainActivity)context).getFragment(ShelfFragment.class))).initBookShelf();
        }
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
}

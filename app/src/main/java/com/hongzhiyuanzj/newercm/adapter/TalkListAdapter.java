package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;
import com.hongzhiyuanzj.newercm.view.MyRatingBar;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/12.
 */
public class TalkListAdapter extends RecyclerView.Adapter<TalkListAdapter.ViewHolder>{

    public static String CommentId = "commendid";
    public static String UserName= "username";
    public static String Time = "time";
    public static String Talk = "talk";
    public static String Headphoto = "headphoto";
    public static String Rating = "rating";
    public static String Count = "count";
    public static String State = "state";

    private Context context;
    private List<HashMap<String, Object>> mDatas;


    public TalkListAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_talk, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.username.setText((String)mDatas.get(position).get(UserName));
        holder.talk.setText((String)mDatas.get(position).get(Talk));
        holder.time.setText(Utils.transTime((Long)mDatas.get(position).get(Time)));
        holder.headphoto.setImageURI((String)mDatas.get(position).get(Headphoto));
        holder.star.setRate((Integer)mDatas.get(position).get(Rating));
        holder.star.cantClick();
        holder.count.setText((Integer) mDatas.get(position).get(Count)+"");

        if((Integer)mDatas.get(position).get(State)==3){
            holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(),context.getResources().getColor(R.color.red)));
        }else{
            holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(),context.getResources().getColor(R.color.dividerColor)));
        }

        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((Integer)mDatas.get(position).get(State)==3){
                    Utils.showToast("您已点赞");
                    return;
                }

                if(Utils.checkLogin((BaseActivity) context)){

                    HttpUtils.zanForComment((Long) mDatas.get(position).get(CommentId), new HttpUtils.HttpCallback() {
                        @Override
                        public void onSuccess(String json) {
                            Result result = JSON.parseObject(json, Result.class);
                            switch (result.getCode()){
                                case 0:
                                    int count = (Integer)mDatas.get(position).get(Count);
                                    holder.count.setText(++count+"");
                                    holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(), context.getResources().getColor(R.color.red)));
                                    break;
                                case 3:

                                    break;
                            }
                        }
                    });


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.username)TextView username;
        @BindView(R.id.talk)TextView talk;
        @BindView(R.id.time)TextView time;
        @BindView(R.id.headphoto)SimpleDraweeView headphoto;
        @BindView(R.id.star)MyRatingBar star;
        @BindView(R.id.zan_container)LinearLayout zan;
        @BindView(R.id.count)TextView count;
        @BindView(R.id.count_img)ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

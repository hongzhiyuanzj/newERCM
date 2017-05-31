package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.fragment.TideDialog;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.ui.RecommendListActivity;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/15.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{

    public static final String TITLE = "title";
    public static final String TIME = "time";
    public static final String INTRODUCE = "introduce";
    public static final String COUNT = "count";
    public static final String LIST_ID = "list_id";
    public static final String STATUS = "status";

    private SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
    private Context context;
    private List<HashMap<String, Object>> mDatas;

    public RecommendAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_recommend, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText((String)mDatas.get(position).get(TITLE));
        holder.count.setText((Integer) mDatas.get(position).get(COUNT)+"");
        holder.introduce.setText((String)mDatas.get(position).get(INTRODUCE));
        if((Integer)mDatas.get(position).get(STATUS)==3){
            holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(), context.getResources().getColor(R.color.red)));
        }else{
            holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(), context.getResources().getColor(R.color.dividerColor)));
        }

        holder.time.setText(format.format(mDatas.get(position).get(TIME)));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendListActivity.start(context, (Integer)mDatas.get(position).get(LIST_ID));
            }
        });
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((Integer)mDatas.get(position).get(STATUS)==3){
                    Utils.showToast("您已推荐");
                    return;
                }

                if(Utils.checkLogin((BaseActivity) context)){

                    HttpUtils.recommend((Integer) mDatas.get(position).get(LIST_ID), new HttpUtils.HttpCallback() {
                        @Override
                        public void onSuccess(String json) {
                            Result result = JSON.parseObject(json, Result.class);
                            switch (result.getCode()){
                                case 0:
                                    int count = (Integer)mDatas.get(position).get(COUNT);
                                    holder.count.setText(++count+"");
                                    holder.img.setImageDrawable(Utils.setDrawableTint(holder.img.getDrawable().mutate(), context.getResources().getColor(R.color.red)));
                                    break;
                                case 4:
                                    TideDialog.showDialog((BaseActivity)context);
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

        @BindView(R.id.title)TextView title;
        @BindView(R.id.container)LinearLayout container;
        @BindView(R.id.time)TextView time;
        @BindView(R.id.introduce)TextView introduce;
        @BindView(R.id.count)TextView count;
        @BindView(R.id.count_img)ImageView img;
        @BindView(R.id.zan_container)LinearLayout zan;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




}

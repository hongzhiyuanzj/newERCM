package com.hongzhiyuanzj.newercm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/18.
 */
public class IdentityListAdapter extends RecyclerView.Adapter<IdentityListAdapter.ViewHolder>{

    public static final String NAME ="name";
    public static final String STATE ="state";

    private List<HashMap<String, Object>> mDatas;
    private Context context;

    public IdentityListAdapter(List<HashMap<String, Object>> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_identity,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText((String)mDatas.get(position).get(NAME));
        int state = (Integer)mDatas.get(position).get(STATE);
        String state_str = "";
        switch (state){
            case 0:
                state_str = "(申请中)";
                holder.state.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
            case 1:
                state_str = "(已通过)";
                holder.state.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case 2:
                state_str = "(申请拒绝)";
                holder.state.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 3:
                state_str = "(系统对接关联成功)";
                holder.state.setTextColor(context.getResources().getColor(R.color.textPrimary));
                break;
            case 4:
                state_str = "(系统对接关联失败)";
                holder.state.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }
        holder.state.setText(state_str);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)TextView name;
        @BindView(R.id.state)TextView state;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

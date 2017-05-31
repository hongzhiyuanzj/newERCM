package com.hongzhiyuanzj.newercm.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongzhiyuanzj.newercm.R;

import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/13.
 */
public class RecyclerViewApdaterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int FOOTVIEW = 10000;
    private RecyclerView.Adapter mInnerAdapter;
    private boolean footViewVisiable = true;

    public RecyclerViewApdaterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==FOOTVIEW){
            return new RecyclerViewApdaterWrapper.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_foot, null));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isFootView(position)){
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getRealCount()+ (footViewVisiable?1:0);
    }

    @Override
    public int getItemViewType(int position) {
        if(isFootView(position)){
            return FOOTVIEW;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){

            final GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    int viewType = getItemViewType(position);
                    if(viewType==FOOTVIEW){
                        return gridLayoutManager.getSpanCount();
                    }
                    if(spanSizeLookup!=null){
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public int getRealCount(){
        return mInnerAdapter.getItemCount();
    }


    public boolean isFootView(int position){
        return position>=getRealCount();

    }

    public void setFootVisiable(int visiable){
        if(visiable==View.GONE){
            footViewVisiable = false;
        }
        if(visiable==View.VISIBLE){
            footViewVisiable = true;
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

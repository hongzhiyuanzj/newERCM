package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.RecommendAdapter;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.entity.RecommendDir;
import com.hongzhiyuanzj.newercm.entity.RecommendDirList;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.recommend_container)RecyclerView recommend;
    @BindView(R.id.recommend_img)ImageView img;
    @BindView(R.id.framelayout)FrameLayout frameLayout;
    private List<HashMap<String,Object>> datas;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recommend, null);
        ButterKnife.bind(this,view);
        recommend.setLayoutManager(new LinearLayoutManager(getContext()));
        recommend.addItemDecoration(new DividerItemDecoration(getContext()));
        img.setImageDrawable(Utils.setDrawableTint(img.getDrawable(), getResources().getColor(R.color.colorPrimary)));
        setLoadView(frameLayout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(datas==null) {
            datas = new ArrayList<>();
            getRecommendDir();
        }
        return view;
    }


    public void getRecommendDir(){
        startLoading();
        datas.clear();
        HttpUtils.getRecommendDirList(new HttpUtils.HttpCallback() {
            @Override
            public void onFailure() {
                super.onFailure();
                stopLoading();
            }

            @Override
            public void onSuccess(String json) {

            RecommendDirList result = JSON.parseObject(json, RecommendDirList.class);
            if(result.getCode()==0){
                if(result.getResult()!=null){
                    List<RecommendDir> list = result.getResult();
                    HashMap<String, Object> map = null;
                    for(RecommendDir dir: list){
                        map = new HashMap<String, Object>();
                        map.put(RecommendAdapter.TITLE, dir.getRdName());
                        map.put(RecommendAdapter.TIME, dir.getTime());
                        map.put(RecommendAdapter.COUNT,dir.getCount());
                        map.put(RecommendAdapter.INTRODUCE,dir.getRdIntroduce());
                        map.put(RecommendAdapter.LIST_ID, dir.getRdId());
                        map.put(RecommendAdapter.STATUS, dir.getStatus());
                        datas.add(map);
                    }
                    recommend.setAdapter(new RecommendAdapter(getContext(), datas));
                }else{

                }

            }else{


            }
            stopLoading();
            }
        });
    }
}

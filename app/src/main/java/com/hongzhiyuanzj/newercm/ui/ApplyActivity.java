package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.IdentityListAdapter;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.entity.Identity;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongzhiyuanzj on 2017/5/8.
 */
public class ApplyActivity extends ToolbarActivity{


    @BindView(R.id.framelayout)FrameLayout frameLayout;
    @BindView(R.id.iden_list)RecyclerView iden_list;
    @BindView(R.id.library_img)ImageView library_img;
    private MenuItem search;
    private List<HashMap<String, Object>> datas = new ArrayList<>();

    public static void start(Context context){
        Intent intent = new Intent(context, ApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        setTitle(R.string.apply_recognize);
        iden_list.setLayoutManager(new LinearLayoutManager(this));
        iden_list.addItemDecoration(new DividerItemDecoration(this));
        library_img.setImageDrawable(Utils.setDrawableTint(library_img.getDrawable().mutate(), getResources().getColor(R.color.colorPrimary)));
        setLoadView(frameLayout);
        getIdenList();
    }

    private void getIdenList(){
        startLoading();
        HttpUtils.getIdentityList(new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                Result result = JSON.parseObject(json, Result.class);
                if(result.getCode()==0) {
                    List<Identity> identitys = result.getIdentList();
                    if(identitys!=null) {
                        HashMap<String, Object> map = null;
                        for (Identity identity : identitys) {
                            map = new HashMap<String, Object>();
                            map.put(IdentityListAdapter.NAME, identity.getLibName());
                            map.put(IdentityListAdapter.STATE, identity.getStatus());
                            datas.add(map);
                        }
                        iden_list.setAdapter(new IdentityListAdapter(datas, ApplyActivity.this));
                    }
                }else{

                }
                stopLoading();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        search = menu.findItem(R.id.search);
        search.setIcon(Utils.setDrawableTint(search.getIcon(), getResources().getColor(R.color.textIcon)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LibrarySearchActivity.start(this);
        return true;
    }


}

package com.hongzhiyuanzj.newercm.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BooksAdapter;
import com.hongzhiyuanzj.newercm.adapter.ShelfAdapter;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.entity.Shelf;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.ui.LoginActivity;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.ShelfHelper;
import com.hongzhiyuanzj.newercm.util.Utils;
import com.hongzhiyuanzj.newercm.view.MyPullToRefreshLayout;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public class ShelfFragment extends BaseFragment {

    @BindView(R.id.shelf_layout)
    RecyclerView shlef_layout;
    @BindView(R.id.has_log_container)
    RelativeLayout has_log_container;
    @BindView(R.id.un_log_container)
    LinearLayout un_log_container;
    @BindView(R.id.log_container)RelativeLayout log_container;

    private View view;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).from(getContext()).inflate(R.layout.fragment_shelf, null);
        ButterKnife.bind(this, view);
        shlef_layout.setLayoutManager(new GridLayoutManager(getContext(), 3));
        shlef_layout.addItemDecoration(new BooksAdapter.BookItemDecoration(getContext()));
        shlef_layout.setItemAnimator(new DefaultItemAnimator());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBookShelf();
        return view;
    }


    public void initBookShelf(){
        if(Prefer.isLogin()) {
            if(getShelf()){
                has_log_container.setVisibility(View.VISIBLE);
                un_log_container.setVisibility(View.GONE);
            }else{
                has_log_container.setVisibility(View.GONE);
                un_log_container.setVisibility(View.VISIBLE);
                log_container.setVisibility(View.GONE);
            }

        }else{
            log_container.setVisibility(View.VISIBLE);
            has_log_container.setVisibility(View.GONE);
            un_log_container.setVisibility(View.VISIBLE);
        }

    }

    private boolean getShelf(){
        datas.clear();
        List<Shelf> shelfs = ShelfHelper.getShelf();
        if(shelfs != null && shelfs.size()!=0) {
            HashMap<String, Object> map = null;
            for (int i = 0; i < shelfs.size(); i++) {
                map = new HashMap<>();
                map.put(BooksAdapter.BookId, shelfs.get(i).getBookid());
                map.put(BooksAdapter.BookName, shelfs.get(i).getBookname());
                map.put(BooksAdapter.BookAuthor, shelfs.get(i).getAuthor());
                map.put(BooksAdapter.BookImg, shelfs.get(i).getImgPath());
                datas.add(map);
            }
            shlef_layout.setAdapter(new ShelfAdapter(datas, getContext()));
            return true;
        }else{
            return false;
        }

    }

    @OnClick(R.id.login)
    public void login(){
        LoginActivity.startForResult((BaseActivity) getActivity());
    }

    @OnClick(R.id.goto_library)
    public void gotoLibrary(){
        ((MainActivity)getActivity()).changePage(2);
    }

}

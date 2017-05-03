package com.hongzhiyuanzj.newercm.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hongzhiyuanzj.newercm.MainActivity;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BooksAdapter;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.ui.LoginActivity;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.security.Permission;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_shelf, null);
        ButterKnife.bind(this, view);
        initBookShelf();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        initBookShelf();
    }
    private void initBookShelf(){
        if(Prefer.isLogin()) {
            shlef_layout.setLayoutManager(new GridLayoutManager(getContext(), 3));
            shlef_layout.setAdapter(new BooksAdapter(
                    Utils.createData(new String[]{BooksAdapter.BookName, BooksAdapter.BookAuthor}, new String[]{"人民的名义", "周梅森"}, 30),
                    getContext()));
            shlef_layout.addItemDecoration(new BooksAdapter.BookItemDecoration(getContext()));
            has_log_container.setVisibility(View.VISIBLE);
            un_log_container.setVisibility(View.GONE);
        }else{
            has_log_container.setVisibility(View.GONE);
            un_log_container.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.login)
    public void login(){
        LoginActivity.start(getContext());
    }

    @OnClick(R.id.goto_library)
    public void gotoLibrary(){
        ((MainActivity)getActivity()).changePage(2);
    }

}

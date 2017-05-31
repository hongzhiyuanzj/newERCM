package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.BooksAdapter;
import com.hongzhiyuanzj.newercm.adapter.RecyclerViewApdaterWrapper;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.base.BaseFragment;
import com.hongzhiyuanzj.newercm.entity.BookInfo;
import com.hongzhiyuanzj.newercm.entity.BookList;
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
public class LibraryFragment extends BaseFragment{

    @BindView(R.id.library_container)RecyclerView recyclerView;
    @BindView(R.id.framelayout)FrameLayout frameLayout;

    private RecyclerViewApdaterWrapper adapter;
    private List<HashMap<String, Object>> datas;
    private View view;
    private int page = Values.startPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_library, null);
        ButterKnife.bind(this, view);
        setLoadView(frameLayout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new BooksAdapter.BookItemDecoration(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(datas==null){
            datas = new ArrayList<>();
            getBooks();
        }
        return view;
    }

    private void loadData(){
        page++;
        HttpUtils.getBookList(page, 12, "", new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                BookList bookList = JSON.parseObject(json, BookList.class);
                if (bookList.getStatus().equals("OK")) {
                    //成功返回结果
                    if(bookList.getList()!=null) {
                        //结果不为空
                        addData(bookList);
                        adapter.notifyDataSetChanged();

                    }else{
                        //结果为空
                        Utils.showToast("该图书馆还没有书");
                    }


                }else{
                    //返回失败
                }

            }
        });
    }

    private void addData(BookList bookList){
        HashMap<String, Object> map = null;
        for (BookInfo bookInfo : bookList.getList()) {
            map = new HashMap<String, Object>();
            map.put(BooksAdapter.BookImg, bookInfo.getImgPath());
            map.put(BooksAdapter.BookName, bookInfo.getTitle());
            map.put(BooksAdapter.BookAuthor, bookInfo.getAuthor());
            map.put(BooksAdapter.BookId, bookInfo.getBookId());
            datas.add(map);
        }
    }

    private void getBooks(){
        startLoading();
        page = Values.startPage;
        if(adapter!=null) {
            datas.clear();
            adapter.setFootVisiable(View.VISIBLE);
        }
        HttpUtils.getBookList(page,12, "", new HttpUtils.HttpCallback() {
            @Override
            public void onFailure() {
                stopLoading();
            }

            @Override
            public void onSuccess(String json) {
                BookList bookList = JSON.parseObject(json, BookList.class);
                if (bookList.getStatus().equals("OK")) {
                    //成功返回结果
                    if(bookList.getList()!=null) {
                        //结果不为空
                        addData(bookList);
                        adapter = new RecyclerViewApdaterWrapper(new BooksAdapter(datas, getContext()));
                        recyclerView.setAdapter(adapter);

                    }else{
                        //结果为空
                        Utils.showToast("该图书馆还没有书");
                    }


                }else{
                    //返回失败
                }
                stopLoading();

            }
        });
    }

}

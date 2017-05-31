package com.hongzhiyuanzj.newercm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.adapter.DividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.ReadAdapter;
import com.hongzhiyuanzj.newercm.adapter.ShortDividerItemDecoration;
import com.hongzhiyuanzj.newercm.adapter.TalkListAdapter;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.base.CantScrollViewLayoutManager;
import com.hongzhiyuanzj.newercm.entity.BookContent;
import com.hongzhiyuanzj.newercm.entity.BookDetail;
import com.hongzhiyuanzj.newercm.entity.BookInfo;
import com.hongzhiyuanzj.newercm.entity.Result;
import com.hongzhiyuanzj.newercm.entity.Talk;
import com.hongzhiyuanzj.newercm.http.HttpUtils;
import com.hongzhiyuanzj.newercm.util.ShelfHelper;
import com.hongzhiyuanzj.newercm.util.TagColor;
import com.hongzhiyuanzj.newercm.util.Utils;
import com.hongzhiyuanzj.newercm.view.MyRatingBar;
import com.hongzhiyuanzj.newercm.view.MyScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/5/5.
 */
public class BookDetailActivity extends BaseActivity{
    public static String BOOK_ID = "book_id";
    public static String BOOK_TITLE = "book_title";

    @BindView(R.id.framelayout)FrameLayout frameLayout;
    @BindView(R.id.scrollView)MyScrollView scrollView;
    @BindView(R.id.poster)SimpleDraweeView poster;
    @BindView(R.id.name)TextView name;
    @BindView(R.id.author)TextView author;
    @BindView(R.id.add_shelf)TextView add_shelf;
    @BindView(R.id.recommend)TextView recommend;
    @BindView(R.id.info)TextView info;
    @BindView(R.id.show_total)TextView show_total;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.title)TextView title;

    @BindView(R.id.tag_container)LinearLayout tag_container;

    @BindView(R.id.add_shelf_img)ImageView add_shelf_img;
    @BindView(R.id.recommend_img)ImageView recommend_img;

    @BindView(R.id.shelf_container)LinearLayout shelf_container;
    @BindView(R.id.reading_container)LinearLayout reading_container;
    @BindView(R.id.recommend_container)LinearLayout recommend_container;

    @BindView(R.id.talk_list)RecyclerView talk_list;
    @BindView(R.id.talk_list_container)LinearLayout talk_container;
    @BindView(R.id.no_talk_container)RelativeLayout no_talk_container;
    @BindView(R.id.detail)TextView detail;

    @BindView(R.id.ratingbar)MyRatingBar ratingBar;
    @BindView(R.id.score)TextView score;
    @BindView(R.id.comment_count)TextView comment_count;

    @BindView(R.id.read_list)RecyclerView read_list;

    private List<HashMap<String,Object>> read_datas = new ArrayList<>();
    private MenuItem share;
    private List<HashMap<String, Object>> talk_datas = new ArrayList<>();

    private boolean isGetInfo, isGetTalk;
    private int talk_count;
    private boolean islayout;

    public static void start(Context context, String id,String title){
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(BOOK_ID, id);
        intent.putExtra(BOOK_TITLE,title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        setLoadView(frameLayout);
        getBookInfo();
        getTalk();
        init();
    }


    private void init(){
        initAddShelf();
        recommend_img.setImageDrawable(Utils.setDrawableTint(recommend_img.getDrawable(), getResources().getColorStateList(R.color.backarrow_color_selector)));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.getBackground().mutate().setAlpha(0);
        scrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void scrollChange(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

                if(y>200){
                    toolbar.getBackground().mutate().setAlpha(255);
                    title.setVisibility(View.VISIBLE);

                }else {
                    toolbar.getBackground().mutate().setAlpha(y<0?0:(int) (y / 200f * 255));
                    title.setVisibility(View.GONE);
                }
            }
        });
        talk_list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(talk_list.getChildCount()!=0&&!islayout) {
                    islayout = true;
                    int size = talk_list.getChildAt(0).getHeight();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) talk_list.getLayoutParams();
                    params.height = size*(talk_count>=3?3:talk_count);
                    talk_list.setLayoutParams(params);
                }
            }
        });
        CantScrollViewLayoutManager manager = new CantScrollViewLayoutManager(this);
        manager.setScrollEnabled(false);
        talk_list.setLayoutManager(manager);
        talk_list.addItemDecoration(new ShortDividerItemDecoration(this));

        CantScrollViewLayoutManager manager1 = new CantScrollViewLayoutManager(this);
        manager1.setScrollEnabled(false);
        read_list.setLayoutManager(manager1);
        read_list.addItemDecoration(new DividerItemDecoration(this));
        read_list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(read_list.getChildCount()!=0&&!islayout) {
                    islayout = true;
                    int size = read_list.getChildAt(0).getHeight();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) read_list.getLayoutParams();
                    params.height = size * read_list.getChildCount();
                    read_list.setLayoutParams(params);
                }
            }
        });

    }

    private void initAddShelf(){
        if(ShelfHelper.containShelf(getIntent().getStringExtra(BOOK_ID))){
            add_shelf_img.setImageDrawable(Utils.setDrawableTint(add_shelf_img.getDrawable().mutate(),
                    getResources().getColor(R.color.colorAccent)));
            add_shelf.setText("已加入书架");
            add_shelf.setEnabled(false);
        }else{
            add_shelf_img.setImageDrawable(Utils.setDrawableTint(add_shelf_img.getDrawable().mutate(),
                    getResources().getColor(R.color.textIcon)));
            add_shelf.setText("加入书架");
            add_shelf.setEnabled(true);
        }


    }

    @OnClick(R.id.show_total)
    public void showTotal(){
        info.setMaxLines(100);
        show_total.setVisibility(View.GONE);
    }

    private void getBookInfo(){
        startLoading();
        HttpUtils.getBookInfo(getIntent().getStringExtra(BOOK_ID), new HttpUtils.HttpCallback() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(String json) {
                BookDetail bookDetail = JSON.parseObject(json, BookDetail.class);
                if(bookDetail.getFullBookInfo()!=null) {
                    BookInfo bookInfo = bookDetail.getFullBookInfo();
                    if (bookInfo.getType() != null) {
                        String[] type = bookInfo.getType().split(", ");
                        addTag(type);
                    }
                    poster.setImageURI(bookDetail.getImgPath());
                    name.setText(bookInfo.getTitle());
                    title.setText(bookInfo.getTitle());
                    title.setVisibility(View.GONE);
                    String isbn = null;
                    if(bookInfo.getIsbn()!=null&&bookInfo.getIsbn().size()!=0&&!bookInfo.getIsbn().get(0).equals("")){
                        isbn = bookInfo.getIsbn().get(0);
                    }else{
                        isbn = getResources().getString(R.string.none);
                    }
                    detail.setText("出版社："+Utils.filterData(bookInfo.getPress())+"\n"+
                                    "出版日："+Utils.filterData(bookInfo.getPubDate())+"\n" +
                                    "书　号："+isbn+"\n" +
                                    "关键字："+Utils.filterData(bookInfo.getKeyword()));
                    author.setText(getResources().getString(R.string.author) + Utils.filterData(bookInfo.getAuthor()));
                    if (bookInfo.getSummary() == null || bookInfo.getSummary().equals("")) {
                        info.setText(R.string.none);
                    } else {
                        info.setText(bookInfo.getSummary());
                        if (info.getLineCount() > 3) {
                            show_total.setVisibility(View.VISIBLE);
                        }
                    }
                    bookname = bookInfo.getTitle();
                    descripiton = bookInfo.getSummary();
                    bookid = getIntent().getStringExtra(BOOK_ID);
                    bookauthor = bookInfo.getAuthor();
                    imgpath = bookInfo.getImgPath();
                }
                if(bookDetail.getBookUrlList()==null||bookDetail.getBookUrlList().size()==0){
                    reading_container.setVisibility(View.GONE);
                    shelf_container.setVisibility(View.GONE);
                    recommend_container.setVisibility(View.VISIBLE);
                }else{
                    addReadLib(bookDetail.getBookUrlList());

                }
                score.setText(bookDetail.getRating()/10f+"分");
                comment_count.setText(bookDetail.getCommentCount()+"人评价");
                ratingBar.setRate(bookDetail.getRating()/10);
                isGetInfo = true;
                if(isGetInfo&&isGetTalk) {
                    stopLoading();
                }
            }
        });
    }

    private void addReadLib(List<BookContent> contents){
        HashMap<String, Object> map = null;
        for(int i=0;i<contents.size();i++){
            map = new HashMap<>();
            map.put(ReadAdapter.LIBNAME, contents.get(i).getLibName());
            map.put(ReadAdapter.URL, contents.get(i).getUrl());
            read_datas.add(map);
        }
        read_list.setAdapter(new ReadAdapter(read_datas, this, getIntent().getStringExtra(BOOK_TITLE)));

    }


    private void addTag(String[] tags){
        TextView textView = null;
        LinearLayout linearLayout = null;
        int margin = getResources().getDimensionPixelSize(R.dimen.activity_main_margin);
        int color_pos = 0;
        for(int i=0;i<(tags.length-1)/3+1;i++){
            linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.topMargin = margin;
            for(int j=0;j<3;j++){
                final String tag;
                try {
                    tag = tags[i*3+j];
                } catch (Exception e) {
                    tag_container.addView(linearLayout, params1);
                    return;
                }
                textView = new TextView(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params2.rightMargin = margin;
                textView.setTextAppearance(this, R.style.tag_text_appearance);
                int padding = getResources().getDimensionPixelSize(R.dimen.margin);
                textView.setPadding(padding,padding/2,padding,padding/2);
                textView.setText(tag);
                textView.setTextColor(getResources().getColor(TagColor.colors[color_pos]));
                color_pos++;
                if(color_pos>=TagColor.colors.length){
                    color_pos-=TagColor.colors.length;
                }
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_btn_primary_bg));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchActivity.start(BookDetailActivity.this, tag);
                    }
                });
                linearLayout.addView(textView, params2);
            }
            tag_container.addView(linearLayout, params1);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.share, menu);
//        share = menu.findItem(R.id.share);
//        share.setIcon(Utils.setDrawableTint(share.getIcon(), getResources().getColor(R.color.textIcon)));
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.share:
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @OnClick(R.id.add_shelf)
    public void addShelf(){
        if(Utils.checkLogin(this)) {
            if (!ShelfHelper.containShelf(bookid)) {
                ShelfHelper.addShelf(bookid, bookname, bookauthor, imgpath);
                Utils.showToast("添加成功");
                initAddShelf();
            } else {
                Utils.showToast("已经添加");
            }
        }
    }

    @OnClick(R.id.recommend)
    public void recommend(){
        if(Utils.checkLogin(this)){

        }
    }

    @OnClick(R.id.i_want_talk)
    public void wantTalk(){
        if(Utils.checkLogin(this)){
            RatingActivity.start(this, getIntent().getStringExtra(BOOK_ID));
        }

    }

    @OnClick(R.id.all_talk)
    public void getAllTalk(){
        TalkListActivity.start(this, getIntent().getStringExtra(BOOK_ID));

    }

    public void getTalk(){
        talk_datas.clear();
        HttpUtils.getTalkList(getIntent().getStringExtra(BOOK_ID), 1, 3, new HttpUtils.HttpCallback() {

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(String json) {
                Result talkList = JSON.parseObject(json, Result.class);
                List<Talk> talks = talkList.getResult();
                if(talks!=null) {
                    talk_count = talks.size();
                    HashMap<String, Object> map = null;
                    for (Talk talk : talks) {
                        map = new HashMap<String, Object>();
                        map.put(TalkListAdapter.UserName, talk.getUserName());
                        map.put(TalkListAdapter.Talk, talk.getContent());
                        map.put(TalkListAdapter.Time, talk.getTime());
                        map.put(TalkListAdapter.Rating, talk.getRating());
                        map.put(TalkListAdapter.Count, talk.getVotecount());
                        map.put(TalkListAdapter.CommentId, talk.getCommentId());
                        map.put(TalkListAdapter.State, talk.getStatus());
                        talk_datas.add(map);
                    }
                    talk_list.setAdapter(new TalkListAdapter(BookDetailActivity.this, talk_datas));
                }else {
                    talk_container.setVisibility(View.GONE);
                    no_talk_container.setVisibility(View.VISIBLE);
                }
                isGetTalk = true;
                if (isGetInfo && isGetTalk) {
                    stopLoading();
                }
            }
        });
    }

    private String bookname;
    private String descripiton;
    private String bookid;
    private String bookauthor;
    private String imgpath;


}

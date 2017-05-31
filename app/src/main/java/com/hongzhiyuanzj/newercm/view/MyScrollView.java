package com.hongzhiyuanzj.newercm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by hongzhiyuanzj on 2017/5/7.
 */
public class MyScrollView extends ScrollView{

    private OnScrollChangeListener onScrollChangeListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollChangeListener!=null) {
            onScrollChangeListener.scrollChange(this, l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener){
        onScrollChangeListener = listener;
    }


    public interface OnScrollChangeListener{
        public void scrollChange(MyScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}

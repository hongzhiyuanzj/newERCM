package com.hongzhiyuanzj.newercm.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;

/**
 * Created by hongzhiyuanzj on 2017/5/3.
 */
public class MyPullToRefreshLayout extends ViewGroup{
    private String TAG = this.getClass().getSimpleName();

    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;

    private OnRefreshListener onRefreshListener;
    private View mHeadView;
    private View mContentView;
    private boolean isEnd;
    private boolean isRefreable;
    private boolean isRecord;

    private int state;
    private int rate = 3;
    private int startY;
    private int headViewHeight;
    private int maxHeadViewHeight = 200;
    private int pull_height;

    private int mLastX;
    private int mLastY;

    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    private Handler mHandle = new Handler();

    public MyPullToRefreshLayout(Context context) {
        this(context, null);

    }

    public MyPullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.view_refresh_header, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView = (ImageView)mHeadView.findViewById(R.id.load_img);
        animationDrawable = (AnimationDrawable)imageView.getDrawable();
        headViewHeight = 100;
        maxHeadViewHeight = headViewHeight*2;
        addView(mHeadView, layoutParams);
        isEnd = true;
        isRefreable = false;
        state = DONE;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for(int i=0;i<getChildCount();i++){
            if(getChildAt(i)!=mHeadView){
                mContentView = getChildAt(i);
            }
        }
    }

    public interface OnRefreshListener{
        public void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
        isRefreable = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(state==REFRESHING){
            return true;
        }

        if(canChildScrollUp()||state == REFRESHING){
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("refresh", "intercept: action_down");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e("refresh", "intercept: action_move");
                int dx = (int)ev.getX()-mLastX;
                int dy = (int)ev.getY()-mLastY;
                if(dy>0 && Math.abs(dy)>Math.abs(dx)){
                    startY = (int)ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("refresh", "intercept: action_up");
                break;
        }
        mLastX = (int)ev.getX();
        mLastY = (int)ev.getY();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(canChildScrollUp()||state==REFRESHING){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                Log.e("refresh", "touch: action_move");
                pull_height = Math.max((mLastY - startY)/rate, 0);
                if(pull_height>maxHeadViewHeight){
                    pull_height = maxHeadViewHeight;
                }
                requestLayout();
                if(pull_height>=headViewHeight){
                    chageState(RELEASE_TO_REFRESH);
                }else if(pull_height<headViewHeight&&pull_height>=0){
                    chageState(PULL_TO_REFRESH);
                }
                mLastY = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.e("refresh", "touch: action_up");
                if(pull_height>=headViewHeight){

                    pull_height = headViewHeight;
                    Log.e("up", pull_height+"");
                    requestLayout();
                    chageState(REFRESHING);
                    onRefreshListener.onRefresh();
                }
                if(pull_height<headViewHeight){
                    pull_height = 0;
                    requestLayout();
                }

                break;
        }
        return true;
    }

    private void chageState(int state){
        switch (state){
            case DONE:
                break;
            case PULL_TO_REFRESH:
                this.state = PULL_TO_REFRESH;
                break;
            case RELEASE_TO_REFRESH:
                this.state = RELEASE_TO_REFRESH;
                break;
            case REFRESHING:
                this.state = REFRESHING;

                break;
        }
    }

    public void finishRefresh(){
        pull_height = 0;
        requestLayout();
        animationDrawable.stop();
        state = DONE;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChild(mHeadView,widthMeasureSpec, heightMeasureSpec);
        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mHeadView.layout(l,t,r,b);
        Log.e("layout", pull_height+"");
        mContentView.layout(l,t+pull_height,r,b);
    }

    protected boolean canChildScrollUp() {
        View targetView = getChildAt(1);
        if (Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

}

package com.hongzhiyuanzj.newercm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.util.Utils;

/**
 * Created by hongzhiyuanzj on 2017/5/19.
 */
public class MyRatingBar extends LinearLayout{


    private ImageView[] stars = new ImageView[5];
    private int rate = 0;

    private int margin;
    private int starWidth;

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        if(a!=null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.MyRatingBar_starMargin:
                        margin = a.getDimensionPixelSize(attr, 0);
                        break;
                    case R.styleable.MyRatingBar_starWidth:
                        starWidth = a.getDimensionPixelSize(attr, 0);
                        break;
                }
            }
        }
        a.recycle();
        init();
    }

    private void init(){
        for(int i=0;i<5;i++){
            final int position = i;
            ImageView imageView = new ImageView(getContext());
            stars[i] = imageView;
            LayoutParams params = new LayoutParams(starWidth, starWidth);
            params.rightMargin = margin;
            imageView.setImageDrawable(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_star_black_24dp).mutate(), getResources().getColor(R.color.dividerColor)));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rate = position;
                    for(int j=0;j<=position;j++) {
                        stars[j].setImageDrawable(Utils.setDrawableTint(stars[j].getDrawable().mutate(), getResources().getColor(R.color.yellow)));
                    }
                    for(int j=position+1;j<5;j++){
                        stars[j].setImageDrawable(Utils.setDrawableTint(stars[j].getDrawable().mutate(), getResources().getColor(R.color.dividerColor)));
                    }
                }
            });
            addView(imageView, params);
        }

    }


    public int getRate(){
        return rate;
    }

    public void setRate(int rate){
        this.rate = rate;
        for(int i=0;i<rate;i++){
            stars[i].setImageDrawable(Utils.setDrawableTint(stars[i].getDrawable().mutate(), getResources().getColor(R.color.yellow)));
        }
    }

    public void cantClick(){
        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            view.setClickable(false);

        }
    }
}

package com.hongzhiyuanzj.newercm.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class RoundButton extends TextView {
    private float radius;
    private Color bg, fg;

    public RoundButton(Context context) {
        this(context, null);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundButton,defStyleAttr, R.style.ResultButton);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.RoundButton_radius:
                    radius = a.getFloat(attr, 0f);
                    break;
                case R.styleable.RoundButton_bg:
                    break;
            }
        }


    }

    @TargetApi(21)
    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

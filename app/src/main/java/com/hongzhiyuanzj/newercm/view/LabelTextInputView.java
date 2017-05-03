package com.hongzhiyuanzj.newercm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class LabelTextInputView extends RelativeLayout{

    private TextView label;
    private EditText input;
    private ImageView clear;
    private View underline;

    private String labelText;
    private boolean clearIconIsVisiable;
    public LabelTextInputView(Context context) {
        this(context, null);
    }

    public LabelTextInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelTextInputView, defStyleAttr, 0);
        if(a!=null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.LabelTextInputView_label:
                        labelText = a.getString(attr);
                        break;
                    case R.styleable.LabelTextInputView_clearIcon:
                        clearIconIsVisiable = a.getBoolean(attr, true);
                        break;
                }
            }
        }
        a.recycle();
        init();
    }

    private void init(){
        label = new TextView(getContext());
        label.setText(labelText);


    }
}

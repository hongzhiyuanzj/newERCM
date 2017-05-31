package com.hongzhiyuanzj.newercm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseActivity;
import com.hongzhiyuanzj.newercm.ui.ApplyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/5/22.
 */
public class TideDialog extends DialogFragment{

    @BindView(R.id.message)TextView message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.dialog_remend_login, null);
        ButterKnife.bind(this, view);
        message.setText("您还未绑定身份，是否立即绑定");
        return view;
    }

    public static void showDialog(BaseActivity activity){
        TideDialog tideDialog = new TideDialog();
        tideDialog.show(activity.getSupportFragmentManager(), "TideDialog");
    }

    @OnClick(R.id.sure)
    public void sure(){
        ApplyActivity.start(getContext());
        dismiss();
    }

    @OnClick(R.id.cancel)
    public void cancel(){
        dismiss();
    }
}

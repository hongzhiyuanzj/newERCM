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
import com.hongzhiyuanzj.newercm.ui.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/5/8.
 */
public class LoginDialog extends DialogFragment{

    @BindView(R.id.sure)TextView sure;
    @BindView(R.id.cancel)TextView cancel;

    private static BaseActivity baseActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_remend_login, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static void showDialog(BaseActivity activity){
        baseActivity = activity;
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(activity.getSupportFragmentManager(), "LoginDialog");
    }

    @OnClick(R.id.sure)
    public void sure(){
        LoginActivity.start(baseActivity);
        dismiss();
    }

    @OnClick(R.id.cancel)
    public void cancel(){
        dismiss();
    }
}

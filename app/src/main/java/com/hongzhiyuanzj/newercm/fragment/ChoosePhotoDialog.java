package com.hongzhiyuanzj.newercm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongzhiyuanzj.newercm.R;
import com.hongzhiyuanzj.newercm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongzhiyuanzj on 2017/6/5.
 */
public class ChoosePhotoDialog extends DialogFragment{
    public static final int REQUEST_IMAGE_CAPTURE = 888;
    public static final int REQUEST_IMAGE_PICK = 889;
    private static BaseActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static void show(BaseActivity activity){
        baseActivity = activity;
        ChoosePhotoDialog dialog = new ChoosePhotoDialog();
        dialog.show(activity.getSupportFragmentManager(), "ChoosePhotoDialog");

    }

    @OnClick(R.id.take_photo)
    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(baseActivity.getPackageManager()) != null) {
            baseActivity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        dismiss();
    }

    @OnClick(R.id.choose_photo)
    public void choosePhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        baseActivity.startActivityForResult(intent, REQUEST_IMAGE_PICK);
        dismiss();
    }

}

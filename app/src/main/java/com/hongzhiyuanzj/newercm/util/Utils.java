package com.hongzhiyuanzj.newercm.util;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.hongzhiyuanzj.newercm.application.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class Utils {
    public static List<HashMap<String,Object>> createData(String[] keys, String[] values ,int count){
        List<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> map;
        for(int i=0;i<count;i++){
            map = new HashMap<>();
            for(int j=0;j<keys.length;j++) {
                map.put(keys[j], values[j]);
            }
            data.add(map);
        }
        return data;
    }

    public static void showToast(String text){
        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resId){
        Toast.makeText(MyApplication.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static Drawable setDrawableTint(Drawable drawable, ColorStateList colorStateList){
        DrawableCompat.setTintList(drawable, colorStateList);
        return drawable;
    }

    public static String getPhoneNumber(){
        TelephonyManager telephonyManager = (TelephonyManager)MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

}

package com.hongzhiyuanzj.newercm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.util.Log;
import android.widget.SeekBar;

import com.alibaba.fastjson.JSON;
import com.hongzhiyuanzj.newercm.application.MyApplication;
import com.hongzhiyuanzj.newercm.entity.Shelf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class Prefer {
    private static String DEFAULT_VALUE = "";
    private static String SHARED_PREF = "shared_pref";
    private static String USERID = "userid";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String IS_LOGIN = "islogin";
    private static String SESSION_ID = "session_id";
    private static String SHELF = "shelf";

    public static void save(String key,String value){
        getShared().edit().putString(key, value).commit();
    }

    public static String get(String key){
        return getShared().getString(key, DEFAULT_VALUE);
    }
    private static SharedPreferences getShared(){
        return MyApplication.getContext().getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
    }

    public static void saveUserId(String userid){
        save(USERID, userid);
    }

    public static String getUserId(){
        return get(USERID);
    }

    public static void saveUsername(String username){
        save(USERNAME, username);
    }

    public static String getUsername(){
        return get(USERNAME);
    }

    public static void savePassword(String password){
        save(PASSWORD, password);
    }

    public static String getPassword(){
        return get(PASSWORD);
    }

    public static Boolean isLogin(){
        return getShared().getBoolean(IS_LOGIN, false);
    }

    public static void saveSessionId(String id){
        save(SESSION_ID, id);
    }

    public static String getSeesionId(){
        return get(SESSION_ID);
    }
    public static void setLogin(boolean isLogin){
        getShared().edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    public static void saveShelf(List<Shelf> shelfs){
        String json = JSON.toJSONString(shelfs);
        getShared().edit().putString(SHELF,json).commit();

    }

    public static List<Shelf> getShelf(){
        String json = getShared().getString(SHELF, DEFAULT_VALUE);
        if(!json.equals(DEFAULT_VALUE)){
            Log.e("getshelf",json);
            return JSON.parseArray(json, Shelf.class);
        }else{
            return null;
        }
    }




}

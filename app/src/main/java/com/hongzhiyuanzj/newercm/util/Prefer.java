package com.hongzhiyuanzj.newercm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.widget.SeekBar;

import com.hongzhiyuanzj.newercm.application.MyApplication;

/**
 * Created by hongzhiyuanzj on 2017/4/25.
 */
public class Prefer {
    private static String DEFAULT_VALUE = "";
    private static String SHARED_PREF = "shared_pref";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String IS_LOGIN = "islogin";
    public static String SESSION_ID = "session_id";

    public static void save(String key,String value){
        getShared().edit().putString(key, value).commit();
    }

    public static String get(String key){
        return getShared().getString(key, DEFAULT_VALUE);
    }
    private static SharedPreferences getShared(){
        return MyApplication.getContext().getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
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

}

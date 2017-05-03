package com.hongzhiyuanzj.newercm.http;

import android.util.Log;

import com.google.zxing.client.android.HttpHelper;
import com.hongzhiyuanzj.newercm.application.Values;
import com.hongzhiyuanzj.newercm.util.MD5;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by hongzhiyuanzj on 2017/4/24.
 */
public class HttpUtils {
    public static void loginToWeb(String id, String userid, String password, HttpCallback callback){
        Map<String,String> map = new HashMap<>();
        map.put("t", "appscan");
        map.put("id", id);
        map.put("userId",userid);
        map.put("password",password);
        OKHttpHelper.getInstance().postAsycHttp(AppURL.LoginToWeb, map,callback);
    }

    public static void login(String username, String password, HttpCallback callback){
        String timestamp = System.currentTimeMillis()+"";
        String sign = MD5.stringToMD5(Values.sysid+Values.syscode+timestamp);
        Map<String,String> map = new HashMap<>();
        map.put("t", "app");
        map.put("userId", username);
        map.put("sysid", Values.sysid);
        map.put("syscode", Values.syscode);
        map.put("password", password);
        map.put("timestamp", timestamp);
        map.put("sign", sign);
        OKHttpHelper.getInstance().postAsycHttp(AppURL.Login, map, callback);
    }

   public static void getCertifyCode(String mobile, HttpCallback callback){
       Map<String,String> map = new HashMap<>();
       map.put("mobile", mobile);
       OKHttpHelper.getInstance().postAsycHttp(AppURL.getCertifyCode, map, callback);
   }

    public static void register(String smscode,String mobile,String name,String password, HttpCallback callback){
        Map<String,String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("smscode", smscode);
        map.put("name", name);
        map.put("password", password);
        OKHttpHelper.getInstance().postAsycHttp(AppURL.getCertifyCode, map, callback);
    }

    public static void  getUserInfo(HttpCallback callback){
        Map<String,String> map = new HashMap<>();
        OKHttpHelper.getInstance().postAsycHttp(AppURL.getUserInfo, map, callback);
    }

    public interface HttpCallback{
        public void onSuccess(String json);
    }
}

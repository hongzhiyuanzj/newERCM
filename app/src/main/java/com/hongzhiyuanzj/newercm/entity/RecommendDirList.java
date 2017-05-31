package com.hongzhiyuanzj.newercm.entity;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/14.
 */
public class RecommendDirList {

    int code;
    String message;
    List<RecommendDir> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecommendDir> getResult() {
        return result;
    }

    public void setResult(List<RecommendDir> result) {
        this.result = result;
    }
}

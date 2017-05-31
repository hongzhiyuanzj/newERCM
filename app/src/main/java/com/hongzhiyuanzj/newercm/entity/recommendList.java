package com.hongzhiyuanzj.newercm.entity;

import com.hongzhiyuanzj.newercm.adapter.RecommendAdapter;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/15.
 */
public class RecommendList {

    int code;
    String message;
    int totalCount;
    int currentPage;

    List<Recommend> result;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Recommend> getResult() {
        return result;
    }

    public void setResult(List<Recommend> result) {
        this.result = result;
    }
}

package com.hongzhiyuanzj.newercm.entity;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/4.
 */
public class BookList {

    String status;
    int totalCount;
    int currentPage;
    List<BookInfo> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<BookInfo> getList() {
        return list;
    }

    public void setList(List<BookInfo> list) {
        this.list = list;
    }
}

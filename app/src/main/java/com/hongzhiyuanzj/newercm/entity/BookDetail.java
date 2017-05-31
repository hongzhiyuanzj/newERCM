package com.hongzhiyuanzj.newercm.entity;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/5.
 */
public class BookDetail {
    int code;
    String message;
    String imgPath;
    int rating;
    int commentCount;
    BookInfo fullBookInfo;
    List<BookContent> bookUrlList;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public BookInfo getFullBookInfo() {
        return fullBookInfo;
    }

    public void setFullBookInfo(BookInfo fullBookInfo) {
        this.fullBookInfo = fullBookInfo;
    }

    public void setBookUrlList(List<BookContent> bookUrlList) {
        this.bookUrlList = bookUrlList;
    }

    public List<BookContent> getBookUrlList() {
        return bookUrlList;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

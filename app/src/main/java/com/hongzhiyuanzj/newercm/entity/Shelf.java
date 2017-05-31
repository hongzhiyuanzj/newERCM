package com.hongzhiyuanzj.newercm.entity;

import java.io.Serializable;

/**
 * Created by hongzhiyuanzj on 2017/5/24.
 */
public class Shelf implements Serializable{

    String imgPath;
    String bookname;
    String bookid;
    String author;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

package com.hongzhiyuanzj.newercm.entity;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/15.
 */
public class Recommend {
    int rdId;
    String title;
    String author;
    String type;
    String press;
    int pubData;
    String pubNo;
    List<String> isbn;
    String summary;
    String language;
    String keyword;
    int pages;

    public int getRdId() {
        return rdId;
    }

    public void setRdId(int rdId) {
        this.rdId = rdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getPubData() {
        return pubData;
    }

    public void setPubData(int pubData) {
        this.pubData = pubData;
    }

    public String getPubNo() {
        return pubNo;
    }

    public void setPubNo(String pubNo) {
        this.pubNo = pubNo;
    }

    public List<String> getIsbn() {
        return isbn;
    }

    public void setIsbn(List<String> isbn) {
        this.isbn = isbn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

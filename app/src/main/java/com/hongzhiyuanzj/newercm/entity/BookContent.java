package com.hongzhiyuanzj.newercm.entity;

/**
 * Created by hongzhiyuanzj on 2017/5/5.
 */
public class BookContent {
    String contentId;
    String bookId;
    int vendorOrderId;
    int bookType;
    String imgPath;
    String url;
    String param1;
    String param2;
    String param3;
    String param4;
    String libName;
    int status;
    int priority;
    int price;
    int heat;
    int batchId;

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getVendorOrderId() {
        return vendorOrderId;
    }

    public void setVendorOrderId(int vendorOrderId) {
        this.vendorOrderId = vendorOrderId;
    }

    public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}

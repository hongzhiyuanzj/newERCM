package com.hongzhiyuanzj.newercm.entity;

import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/5/18.
 */
public class LibraryList {

    int code;
    String message;
    List<Library> result;

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

    public List<Library> getResult() {
        return result;
    }

    public void setResult(List<Library> result) {
        this.result = result;
    }
}

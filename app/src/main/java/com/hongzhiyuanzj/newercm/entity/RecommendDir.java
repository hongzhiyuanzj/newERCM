package com.hongzhiyuanzj.newercm.entity;

/**
 * Created by hongzhiyuanzj on 2017/5/14.
 */
public class RecommendDir {
    int rdId;
    String rdName;
    String rdIntroduce;
    int date;
    long time;
    int count;
    int status;

    public int getRdId() {
        return rdId;
    }

    public void setRdId(int rdId) {
        this.rdId = rdId;
    }

    public String getRdName() {
        return rdName;
    }

    public void setRdName(String rdName) {
        this.rdName = rdName;
    }

    public String getRdIntroduce() {
        return rdIntroduce;
    }

    public void setRdIntroduce(String rdIntroduce) {
        this.rdIntroduce = rdIntroduce;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

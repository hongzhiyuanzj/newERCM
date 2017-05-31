package com.hongzhiyuanzj.newercm.entity;

import java.io.Serializable;

/**
 * Created by hongzhiyuanzj on 2017/5/18.
 */
public class Library implements Serializable{

    int libraryId;
    String libName;
    String libSystem;
    String sysParam;
    String address;
    String telephone;
    String staff;

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getLibSystem() {
        return libSystem;
    }

    public void setLibSystem(String libSystem) {
        this.libSystem = libSystem;
    }

    public String getSysParam() {
        return sysParam;
    }

    public void setSysParam(String sysParam) {
        this.sysParam = sysParam;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }
}

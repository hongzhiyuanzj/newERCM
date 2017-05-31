package com.hongzhiyuanzj.newercm.entity;

/**
 * Created by hongzhiyuanzj on 2017/5/18.
 */
public class Identity {

    long appId;
    String userId;
    int libraryId;
    String accountNo;
    String password;
    String userIdent;
    int userStatus;
    String name;
    int expireDate;
    int status;
    long appTime;
    long passTime;
    int typeId;
    String typeName;
    String libName;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserIdent() {
        return userIdent;
    }

    public void setUserIdent(String userIdent) {
        this.userIdent = userIdent;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(int expireDate) {
        this.expireDate = expireDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAppTime() {
        return appTime;
    }

    public void setAppTime(long appTime) {
        this.appTime = appTime;
    }

    public long getPassTime() {
        return passTime;
    }

    public void setPassTime(long passTime) {
        this.passTime = passTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}

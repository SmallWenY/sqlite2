package com.nuaa233.sqlite.Favorite;

public class MyFavItemModel {
    private String uploadTime;
    private String uploadUsrName;
    private String uploadUsrId;
    private String dishName;
    private String dishcalorie;
    private String usrId;
    private String canteen;
    private String dishprice;

    public String getDishprice() {
        return dishprice;
    }
    public String getCanteen() {
        return canteen;
    }

    public String getDishcalorie() {
        return dishcalorie;
    }

    public String getUsrId() {
        return usrId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public String getDishName() {
        return dishName;
    }

    public String getUploadUsrId() {
        return uploadUsrId;
    }

    public String getUploadUsrName() {
        return uploadUsrName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setUploadUsrId(String uploadUsrId) {
        this.uploadUsrId = uploadUsrId;
    }

    public void setUploadUsrName(String uploadUsrName) {
        this.uploadUsrName = uploadUsrName;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public void setDishcalorie(String dishcalorie) {
        this.dishcalorie = dishcalorie;
    }

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public void setDishprice(String dishprice) {
        this.dishprice = dishprice;
    }
}
package com.nuaa233.sqlite.Canteen.Canteen1;

public class Canteen1Model {
    private String uploadTime;
    private String uploadUsrName;
    private String uploadUsrId;
    private String dishName;
    private String dishcalorie;
    private String dishprice;
    private String usrId;

    public String getDishprice() {
        return dishprice;
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

    public void setDishprice(String dishprice) {
        this.dishprice = dishprice;
    }
}

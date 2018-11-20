package com.nuaa233.sqlite.Disscussion.MyFollow;

public class MyFollowItemModel {
    private String usrId;
    private String followId;
    private String followNick;
    private String followDate;

    public String getUsrId() {
        return usrId;
    }

    public String getFollowId() {
        return followId;
    }

    public String getFollowNick() {
        return followNick;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public void setFollowNick(String followNick) {
        this.followNick = followNick;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}

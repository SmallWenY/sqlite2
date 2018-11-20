package com.nuaa233.sqlite.Disscussion.newItem;

public class ShuoShuo {
    private int shuoId;// 说说Id
    private String userName;// 发说说的名字
    private String shuoDate;// 发说说的日期
    private String shuoContent;// 说说的内容
    private boolean isPhrase;// 我是否点赞
    private int shuoPhraseNum;// 说说点赞的数目
    private int shuoCommentNum;// 说说评论的数目
    private String shuoPhoneModel;// 说说的手机型号。

    public int getShuoId() {
        return shuoId;
    }

    public void setShuoId(int shuoId) {
        this.shuoId = shuoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String shuoName) {
        this.userName = shuoName;
    }

    public String getShuoDate() {
        return shuoDate;
    }

    public void setShuoDate(String shuoDate) {
        this.shuoDate = shuoDate;
    }

    public String getShuoContent() {
        return shuoContent;
    }

    public void setShuoContent(String shuoContent) {
        this.shuoContent = shuoContent;
    }

    public boolean isPhrase() {
        return isPhrase;
    }

    public void setPhrase(boolean isPhrase) {
        this.isPhrase = isPhrase;
    }

    public int getShuoPhraseNum() {
        return shuoPhraseNum;
    }

    public void setShuoPhraseNum(int shuoPhraseNum) {
        this.shuoPhraseNum = shuoPhraseNum;
    }

    public int getShuoCommentNum() {
        return shuoCommentNum;
    }

    public void setShuoCommentNum(int shuoCommentNum) {
        this.shuoCommentNum = shuoCommentNum;
    }

    public String getShuoPhoneModel() {
        return shuoPhoneModel;
    }

    public void setShuoPhoneModel(String shuoPhoneModel) {
        this.shuoPhoneModel = shuoPhoneModel;
    }
}

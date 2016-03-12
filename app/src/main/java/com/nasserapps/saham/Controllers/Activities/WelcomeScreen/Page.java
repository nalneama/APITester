package com.nasserapps.saham.Controllers.Activities.WelcomeScreen;

public class Page {

    String bgColor;
    int    pageNum;
    int    imgResId;

    public Page(int pPageNum,String pBgColor,int pImgResId){

        this.bgColor = pBgColor;
        this.pageNum = pPageNum;
        this.imgResId = pImgResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getBgColor() {

        return bgColor;
    }
}
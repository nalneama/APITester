package com.nasserapps.apitester.Controllers.Activities.WelcomeScreen;

import java.util.LinkedList;
import java.util.List;

public class PageViewManager {

    List<Page> mPageList;
    int mCurrent = 0;
    int mLastPage = 0;

    public PageViewManager() {

        mPageList = new LinkedList<>();
    }

    public Page getPage(int index){

        return mPageList.get(index);
    }

    public int numPages(){

        return mPageList.size();
    }

    public int getCurrPage(){
        return this.mCurrent;
    }

    public void addPage(Page pPage){

        mPageList.add(pPage);
    }


    public void setCurrPage(int pPageNum){

        mCurrent = pPageNum;
    }

    public void setLastPage(int pLastScrolledPage){

        this.mLastPage = pLastScrolledPage;
    }


    public int getLastPage(){

        return this.mLastPage;
    }

}
package com.nasserapps.saham.Controllers.Activities.WelcomeScreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SlidePageAdapter extends FragmentStatePagerAdapter {


    PageViewManager mPageMgr;

    public SlidePageAdapter(FragmentManager pFm, PageViewManager pPagerMgr) {
        super(pFm);
        this.mPageMgr = pPagerMgr;
    }




    @Override
    public Fragment getItem(int position) {
        return OnboardingPageFragment.newInstance(this.mPageMgr.getPage(position));

    }

    @Override
    public int getCount() {
        return this.mPageMgr.numPages();
    }
}
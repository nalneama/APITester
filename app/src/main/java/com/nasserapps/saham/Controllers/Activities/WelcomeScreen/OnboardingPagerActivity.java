package com.nasserapps.saham.Controllers.Activities.WelcomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nasserapps.saham.Controllers.Activities.HomeActivity;
import com.nasserapps.saham.R;

public class OnboardingPagerActivity extends AppCompatActivity implements
        OnboardingPageFragment.OnFragmentInteractionListener,
        OnBoardingPagerIndicator.OnboardingPagerIndicatorInteractionListener{

    private PageViewManager mPagerMgr; //manages the slides/pages
    private ViewPager mViewPager; //slides
    private OnBoardingPagerIndicator onp; //the control at the bottom

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Window window = getWindow();
//        // clear FLAG_TRANSLUCENT_STATUS flag:
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        // finally change the color
//        window.setStatusBarColor(getResources().getColor(R.color.green600));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mPagerMgr = new PageViewManager();

        Page p = new Page(0, "#354353", R.drawable.onboard);
        mPagerMgr.addPage(p);
        p = new Page(1, "#354ABC", R.drawable.two);
        mPagerMgr.addPage(p);
        p = new Page(2, "#23f353", R.drawable.three);
        mPagerMgr.addPage(p);
        p = new Page(3, "#23f353", R.drawable.three);
        mPagerMgr.addPage(p);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            onp = OnBoardingPagerIndicator.newInstance(mPagerMgr.numPages());
            ft.add(R.id.homeLayout, onp, "footer" );
            ft.commit();

        }

        setContentView(R.layout.onboarder_activity);


        mViewPager= (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SlidePageAdapter(getSupportFragmentManager(), mPagerMgr));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPagerMgr.setLastPage(position);

            }

            public void onPageSelected(int position) {
                OnBoardingPagerIndicator lFooterFrag = (OnBoardingPagerIndicator) getSupportFragmentManager().findFragmentByTag("footer");
                mPagerMgr.setCurrPage(position);

                int lLastPage = OnboardingPagerActivity.this.mPagerMgr.getLastPage();
                if (lLastPage == position) {
                    lLastPage = lLastPage + 1;
                }

                lFooterFrag.updateIndicatorCircles(lFooterFrag.getView(), position, lLastPage);
                lFooterFrag.updateIndicatorControlViews(lFooterFrag.getView(), position, lLastPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onDone() {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onNext() {
        this.mViewPager.setCurrentItem(this.mPagerMgr.getCurrPage() + 1);
    }



}
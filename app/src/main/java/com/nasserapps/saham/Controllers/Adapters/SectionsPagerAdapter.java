package com.nasserapps.saham.Controllers.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nasserapps.saham.Controllers.Fragments.MarketFragment;
import com.nasserapps.saham.Controllers.Fragments.StocksListFragment;
import com.nasserapps.saham.Controllers.Fragments.WalletFragment;
import com.nasserapps.saham.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;

    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StocksListFragment();
            case 1:
                return new WalletFragment();
            case 2:
                return new MarketFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.Section_1_Title);
            case 1:
                return mContext.getString(R.string.Section_2_Title);
            case 2:
                return mContext.getString(R.string.Section_3_Title);
        }
        return null;
    }
}
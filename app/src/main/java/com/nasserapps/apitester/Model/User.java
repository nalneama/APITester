package com.nasserapps.apitester.Model;

import android.content.Context;

import java.util.ArrayList;

public class User {

    private Wallet mWallet;
    private UserData mUserData;
    private DataSource mDataSource;


    public User(Context context) {
        // New Items

        mWallet= new Wallet();
        mDataSource = new DataSource(context);
        mUserData = new UserData(context);
        //Get mAllStocks from SQLDatabase
    }

    public ArrayList<Ticker> getAllStocks(){
        ArrayList<Ticker> allStocks = new ArrayList<>();
        allStocks.addAll(mDataSource.getStocks().values());
        //return sort(allStocks, mUserData.getSortPreference());
        return allStocks;
    }

    public ArrayList<Ticker> getWatchList(){
        ArrayList<Ticker> watchlist = new ArrayList<>();
        for(Ticker ticker:mDataSource.getStocks().values()){
            if (ticker.isInWatchList()){
                watchlist.add(ticker);
            }
        }
        return watchlist;
    }

    public Wallet getWallet(){
        mWallet.setInvestmentList(getAllStocks());
        return mWallet;
    }



    public UserData getUserData() {
        return mUserData;
    }

}

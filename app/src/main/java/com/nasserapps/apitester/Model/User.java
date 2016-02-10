package com.nasserapps.apitester.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class User {

    private Wallet mWallet;
    private HashMap<String,Ticker> mAllStocks;
    private Context mContext;
    private UserData mUserData;


    public User(Context context) {
        mContext = context;
        mWallet= new Wallet();
        mAllStocks = new HashMap<>();
        mUserData = new UserData(mContext);
    }


    public Ticker getStock(String key){
        return mAllStocks.get(key);
    }

    public ArrayList<Ticker> getAllStocks(){
        return sort((ArrayList) mAllStocks.values(), mUserData.getSortPreference());
    }
    public ArrayList<Ticker> getWatchLists(){
        ArrayList<Ticker> watchlist = new ArrayList<>();
        for(Ticker ticker:mAllStocks.values()){
            if (ticker.isInWatchList()){
                watchlist.add(ticker);
            }
        }
        return sort(watchlist,mUserData.getSortPreference());
    }

    public Wallet getWallet(){
        return mWallet;
    }


    //Tools Methods
    public ArrayList<Ticker> sort(ArrayList<Ticker> list, String string){
        mUserData.setSortPreference(string);
        HashMap<String, Comparator<Ticker>> comparatorHashMap =  new HashMap<>();

        String[] sortingOptions = new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price"};

        // A-Z Comparator
        comparatorHashMap.put(sortingOptions[0], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return stock1.getSymbol().compareTo(stock2.getName());}});

        //Book Value
        comparatorHashMap.put(sortingOptions[1],new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock1.getPBV(), stock2.getPBV());}});

        // Gain
        comparatorHashMap.put(sortingOptions[2], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(Double.parseDouble(stock2.getPercentage().substring(0, stock2.getPercentage().length() - 1)), Double.parseDouble(stock1.getPercentage().substring(0, stock1.getPercentage().length() - 1)));}});

        // PE Ratio
        comparatorHashMap.put(sortingOptions[3], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock1.getPERatio(), stock2.getPERatio());}});

        // Price
        comparatorHashMap.put(sortingOptions[4], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock2.getPrice(), stock1.getPrice());}});


        //Sort by the Option and return the sorted list
        Collections.sort(list, comparatorHashMap.get(string));

        return list;
    }


}

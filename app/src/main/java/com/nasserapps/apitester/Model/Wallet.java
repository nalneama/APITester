package com.nasserapps.apitester.Model;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Wallet {

    //Wallet
    List<Investment> mInvestmentList;

    //User
    ArrayList<Ticker> mWatchList;

    //User
    private String mSortType = "A-Z";

    //Wallet
    public Wallet( List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    //Wallet
    public double getProfit(){
        double profit=0;
        for (int i=0; i<mInvestmentList.size();i++){
            profit= profit + mInvestmentList.get(i).getProfit();
        }
        return profit;
    }

    //Wallet
    public double getCapital(){
        double capital=0;
        for (int i=0; i<mInvestmentList.size();i++){
            capital= capital + mInvestmentList.get(i).getCapitalInvested();
        }
        return capital;
    }

    //Wallet
    public double getCurrentWorth(){
        double worth=0;
        for (int i=0; i<mInvestmentList.size();i++){
            worth= worth + mInvestmentList.get(i).getCurrentWorth();
        }
        return worth;
    }

    //Wallet
    public double getReturn(){
        return getProfit()/getCapital();
    }


    //Wallet
    public double getPercentageChange(){
        return (getCurrentWorth()-getCapital())/getCapital();
    }

    //Wallet
    public List<Investment> getInvestmentList() {
        return mInvestmentList;
    }

    //Wallet
    public void setInvestmentList(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    //User
    public ArrayList<Ticker> getWatchList() {
        return mWatchList;
    }

    //Wallet
    public Wallet(){
        mInvestmentList= new ArrayList<>();
    }

    //User
    public void setWatchList(ArrayList<Ticker> watchList) {
        mWatchList = watchList;
    }

    //User
    public void setInitialWatchList(List<Ticker> watchList){
        mWatchList = (ArrayList) watchList;
//        String[] companies = mContext.getResources().getStringArray(R.array.Companies_API_Codes);
//        for (String code:companies){
//            mWatchList.add(new Ticker(code));
//        }
    }

    //User
    public void updateWatchList(String json) throws JSONException{

        JSONParser jsonParser = new JSONParser(json);
        mWatchList = jsonParser.updateStocks(mWatchList);

    }

    //Preference
    public String getAPIKey(){
        String APICode="";
        for(Ticker stock:mWatchList){
            APICode=APICode+stock.getAPICode()+"+";
        }
        return APICode;
    }

    //User
    public static HashMap<String, Ticker> getWatchMap(ArrayList<Ticker> list){
            HashMap<String, Ticker> watchMap = new HashMap<>();
            for(Ticker stock:list){
                watchMap.put(stock.getSymbol(),stock);
            }
            return watchMap;
        }

    //User
    public List<Ticker> sort(List<Ticker> list, String string){
        //mSortType = string;
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
        Collections.sort(list,comparatorHashMap.get(string));

        return list;
        }

}

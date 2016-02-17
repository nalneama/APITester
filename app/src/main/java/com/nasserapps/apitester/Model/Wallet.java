package com.nasserapps.apitester.Model;

import com.nasserapps.apitester.R;

import java.util.ArrayList;

public class Wallet {

    //Wallet
    ArrayList<Ticker> mInvestmentList;

    public double getProfit(){
        double profit=0;
        mInvestmentList=getInvestments();
        for (int i=0; i<mInvestmentList.size();i++){
            profit= profit + mInvestmentList.get(i).getQuantity()*(mInvestmentList.get(i).getPrice() - mInvestmentList.get(i).getPurchasedPrice());
        }
        return profit;
    }

    public double getCapital(){
        double capital=0;
        mInvestmentList = getInvestments();
        for (int i=0; i<mInvestmentList.size();i++){
            capital= capital + mInvestmentList.get(i).getQuantity()*mInvestmentList.get(i).getPurchasedPrice();
        }
        return capital;
    }

    public double getCurrentWorth(){
        double worth=0;
        mInvestmentList = getInvestments();
        for (int i=0; i<mInvestmentList.size();i++){
            worth= worth + mInvestmentList.get(i).getPrice() * mInvestmentList.get(i).getQuantity();
        }
        return worth;
    }

    public double getReturn(){
        return getProfit()/getCapital();
    }

    public double getPercentageChange(){
        return (getCurrentWorth()-getCapital())/getCapital();
    }

    public ArrayList<Ticker> getInvestments(){
        ArrayList<Ticker> stocklist = new ArrayList<>();
        for(Ticker ticker:mInvestmentList){
            if (ticker.isInInvestments()){
                stocklist.add(ticker);
            }
        }
        return stocklist;
    }

    public void setInvestmentList(ArrayList<Ticker> investmentList) {
        mInvestmentList = investmentList;
    }

    public int getPriceColor(){
        if(getProfit() < 0){
            return R.color.red;
        }
        else if(getProfit()>0){
            return R.color.green;
        }
        else{
            return R.color.yellow;
        }
    }



    //User
//    private HashMap<String,Ticker> StocksData;



//    public void setWatchList(ArrayList<Ticker> watchList) {
//        mWatchList = watchList;
//    }
//
//    public void setStocksData(ArrayList<Ticker> watchList){
//        StocksData=getWatchMap(watchList);
//    }

//    public void setInitialWatchList(List<Ticker> watchList){
//        mWatchList = (ArrayList) watchList;
////        String[] companies = mContext.getResources().getStringArray(R.array.Companies_API_Codes);
////        for (String code:companies){
////            mWatchList.add(new Ticker(code));
////        }
//    }

//
//    public static HashMap<String, Ticker> getWatchMap(ArrayList<Ticker> list){
//            HashMap<String, Ticker> watchMap = new HashMap<>();
//            for(Ticker stock:list){
//                watchMap.put(stock.getSymbol(),stock);
//            }
//            return watchMap;
//        }





    //Preference

}

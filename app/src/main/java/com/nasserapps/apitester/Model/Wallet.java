package com.nasserapps.apitester.Model;

import java.util.List;

public class Wallet {

    List<Investment> mInvestmentList;
    List<Stock> mStockList;

    public Wallet(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    public double getProfit(){
        double profit=0;
        for (int i=0; i<mInvestmentList.size();i++){
            profit= profit + mInvestmentList.get(i).getProfit();
        }
        return profit;
    }

    public double getCapital(){
        double capital=0;
        for (int i=0; i<mInvestmentList.size();i++){
            capital= capital + mInvestmentList.get(i).getCapitalInvested();
        }
        return capital;
    }

    public double getCurrentWorth(){
        double worth=0;
        for (int i=0; i<mInvestmentList.size();i++){
            worth= worth + mInvestmentList.get(i).getCurrentWorth();
        }
        return worth;
    }

    public double getReturn(){
        return getProfit()/getCapital();
    }

    public List<Investment> getInvestmentList() {
        return mInvestmentList;
    }

    public void setInvestmentList(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    public void updateInvestments(List<Stock> stockList){
        for (int i=0; i<stockList.size();i++){
            for (int j=0;j<mInvestmentList.size();j++){
                if(stockList.get(i).getSymbol().equals(mInvestmentList.get(j).getStock().getSymbol())){
                mInvestmentList.get(j).getStock().setPrice(stockList.get(i).getPrice());
                }
            }
        }
    }

    public List<Stock> getStockList() {
        return mStockList;
    }
}

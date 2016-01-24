package com.nasserapps.apitester.Model;

import java.util.List;

public class Wallet {

    List<Investment> mInvestmentList;

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

    public double getReturn(){
        return getProfit()/getCapital();
    }

    public List<Investment> getInvestmentList() {
        return mInvestmentList;
    }

    public void setInvestmentList(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }
}

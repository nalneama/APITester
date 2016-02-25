package com.nasserapps.apitester.Model;

import java.util.ArrayList;

public class Wallet {

    //Wallet
    ArrayList<Ticker> mInvestmentList;

    public double getProfit() {
        double profit = 0;
        mInvestmentList = getInvestments();
        for (int i = 0; i < mInvestmentList.size(); i++) {
            profit = profit + mInvestmentList.get(i).getQuantity() * (mInvestmentList.get(i).getPrice() - mInvestmentList.get(i).getPurchasedPrice());
        }
        return profit;
    }

    public double getCapital() {
        double capital = 0;
        mInvestmentList = getInvestments();
        for (int i = 0; i < mInvestmentList.size(); i++) {
            capital = capital + mInvestmentList.get(i).getQuantity() * mInvestmentList.get(i).getPurchasedPrice();
        }
        return capital;
    }

    public double getCurrentWorth() {
        double worth = 0;
        mInvestmentList = getInvestments();
        for (int i = 0; i < mInvestmentList.size(); i++) {
            worth = worth + mInvestmentList.get(i).getPrice() * mInvestmentList.get(i).getQuantity();
        }
        return worth;
    }

    public double getReturn() {
        return getProfit() / getCapital();
    }

    public double getPercentageChange() {
        return (getCurrentWorth() - getCapital()) / getCapital();
    }

    public ArrayList<Ticker> getInvestments() {
        ArrayList<Ticker> stocklist = new ArrayList<>();
        for (Ticker ticker : mInvestmentList) {
            if (ticker.isInInvestments()) {
                stocklist.add(ticker);
            }
        }
        return stocklist;
    }

    public void setInvestmentList(ArrayList<Ticker> investmentList) {
        mInvestmentList = investmentList;
    }
}
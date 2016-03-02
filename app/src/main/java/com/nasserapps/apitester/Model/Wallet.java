package com.nasserapps.apitester.Model;

import java.util.ArrayList;

public class Wallet {

    //Wallet
    ArrayList<Stock> mInvestmentList;

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

    public ArrayList<Stock> getInvestments() {
        ArrayList<Stock> stocklist = new ArrayList<>();
        for (Stock stock : mInvestmentList) {
            if (stock.isInInvestments()) {
                stocklist.add(stock);
            }
        }
        return stocklist;
    }

    public void setInvestmentList(ArrayList<Stock> investmentList) {
        mInvestmentList = investmentList;
    }
}
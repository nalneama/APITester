package com.nasserapps.apitester.Model;

public class Investment {

    private Ticker mStock;
    private double mPurchasedPrice;
    private int mQuantity;
    private double mProfit;
    private double mReturn;
    private double mCapitalInvested;
    private double mCurrentWorth;

    public Investment(Ticker stock, double purchasedPrice, int quantity) {
        mStock = stock;
        mPurchasedPrice = purchasedPrice;
        mQuantity = quantity;
        mCurrentWorth = mStock.getPrice()*mQuantity;
        mCapitalInvested = mPurchasedPrice*mQuantity;
        mProfit= mCurrentWorth-mCapitalInvested;
        mReturn = mProfit/mCapitalInvested;
    }

    public Ticker getStock() {
        return mStock;
    }

    public void setStock(Ticker stock) {
        mStock = stock;
    }

    public double getPurchasedPrice() {
        return mPurchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        mPurchasedPrice = purchasedPrice;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public double getProfit() {
        return mProfit;
    }

    public double getReturn() {
        return mReturn;
    }

    public double getCapitalInvested() {
        return mCapitalInvested;
    }

    public double getCurrentWorth() {
        return mCurrentWorth;
    }
}

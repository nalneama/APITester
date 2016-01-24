package com.nasserapps.apitester.Model;

public class Investment {

    private Stock mStock;
    private double mPurchasedPrice;
    private int mQuantity;
    private double mProfit;
    private double mReturn;
    private double mCapitalInvested;
    private double mCurrentWorth;

    public Investment(Stock stock, double purchasedPrice, int quantity) {
        mStock = stock;
        mPurchasedPrice = purchasedPrice;
        mQuantity = quantity;
        mCurrentWorth = mStock.getPrice()*mQuantity;
        mCapitalInvested = mPurchasedPrice*mQuantity;
        mProfit= mCurrentWorth-mCapitalInvested;
        mReturn = mProfit/mCapitalInvested;
    }

    public Stock getStock() {
        return mStock;
    }

    public void setStock(Stock stock) {
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

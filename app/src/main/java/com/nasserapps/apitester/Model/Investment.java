package com.nasserapps.apitester.Model;

public class Investment {

    private Ticker mStock;
    private double mPurchasedPrice;
    private int mQuantity;

    public Investment(Ticker stock, double purchasedPrice, int quantity) {
        mStock = stock;
        mPurchasedPrice = purchasedPrice;
        mQuantity = quantity;
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
        return getCurrentWorth()-getCapitalInvested();
    }

    public double getReturn() {
        return getProfit()/getCapitalInvested();
    }

    public double getCapitalInvested() {
        return mPurchasedPrice*mQuantity;
    }

    public double getCurrentWorth() {
        return mStock.getPrice()*mQuantity;
    }
}

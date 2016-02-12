package com.nasserapps.apitester.Model;

import com.nasserapps.apitester.R;

/**
 * Created by Nasser on 10/1/15.
 * Stock Object
 * Has the following attributes: Price, volume, symbol, name, supply(ask), demand(bid), PE Ratio, Price-to-Book Value.
 */
public class Ticker {
    String mSymbol;
    String mName;
    double mPrice;
    double mPERatio;
    long mVolume;
    double mBid;
    double mAsk;
    double mPBV;
    String percentage;
    double change;
    final String APICode;
    boolean inWatchList;
    private double mPurchasedPrice;
    private int mQuantity;
    private boolean inInvestments;

    public Ticker(String symbol, String name, double price, double PERatio, long volume, double bid, double ask, double PBV, String percentage, double change, String APICode, boolean inWatchList) {
        mSymbol = symbol;
        mName = name;
        mPrice = price;
        mPERatio = PERatio;
        mVolume = volume;
        mBid = bid;
        mAsk = ask;
        mPBV = PBV;
        this.percentage = percentage;
        this.change = change;
        this.APICode = APICode;
        this.inWatchList = inWatchList;
    }

    public void setInWatchList(boolean inWatchList) {
        this.inWatchList = inWatchList;
    }

    public boolean isInWatchList() {
        return inWatchList;
    }

    public Ticker(String symbol, String name, double price, double PERatio, long volume, double bid, double ask, double PBV, String percentage, double change, String APICode, boolean inWatchList, double purchasedPrice, int quantity, boolean inInvestments) {
        mSymbol = symbol;
        mName = name;
        mPrice = price;
        mPERatio = PERatio;
        mVolume = volume;
        mBid = bid;
        mAsk = ask;
        mPBV = PBV;
        this.percentage = percentage;
        this.change = change;
        this.APICode = APICode;
        this.inWatchList = inWatchList;
        mPurchasedPrice = purchasedPrice;
        mQuantity = quantity;
        this.inInvestments = inInvestments;
    }

    public Ticker(String APICode) {
        this.APICode = APICode;
        inWatchList=true;
    }

    public Ticker() {
        APICode="";
        inWatchList=true;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPBV() {
        return mPBV;
    }

    public void setPBV(double PBV) {
        mPBV = PBV;
    }

    public double getPERatio() {
        return mPERatio;
    }

    public void setPERatio(double PERatio) {
        mPERatio = PERatio;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String symbol) {
        mSymbol = symbol;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public long getVolume() {
        return mVolume;
    }

    public void setVolume(long volume) {
        mVolume = volume;
    }

    public double getBid() {
        return mBid;
    }

    public void setBid(double bid) {
        mBid = bid;
    }

    public double getAsk() {
        return mAsk;
    }

    public void setAsk(double ask) {
        mAsk = ask;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getPriceColor(){
        if(change < 0){
            return R.color.red;
        }
        else if(change>0){
            return R.color.green;
        }
        else{
            return R.color.yellow;
        }
    }

    public String getAPICode() {
        return APICode;
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

    public boolean isInInvestments() {
        return inInvestments;
    }

    public void setInInvestments(boolean inInvestments) {
        this.inInvestments = inInvestments;
    }
}

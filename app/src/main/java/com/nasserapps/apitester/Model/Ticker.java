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
    double mDemand;
    double mSupply;
    double mPBV;
    String percentage;
    double change;
    final String APICode;

    public void setInWatchList(boolean inWatchList) {
        this.inWatchList = inWatchList;
    }

    public boolean isInWatchList() {
        return inWatchList;
    }

    boolean inWatchList;

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

    public double getDemand() {
        return mDemand;
    }

    public void setDemand(double demand) {
        mDemand = demand;
    }

    public double getSupply() {
        return mSupply;
    }

    public void setSupply(double supply) {
        mSupply = supply;
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
}

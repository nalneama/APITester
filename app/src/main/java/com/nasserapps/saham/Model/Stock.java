package com.nasserapps.saham.Model;

/**
 * Created by Nasser on 10/1/15.
 * Stock Object
 * Has the following attributes: Price, volume, symbol, name, supply(ask), demand(bid), PE Ratio, Price-to-Book Value.
 */
public class Stock {
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
    private double mDayHigh;
    private double mDayLow;
    private double m52WHigh;
    private double m52WLow;
    private double mBestPBV;
    private double mBestPE;
    private double mWorstPE;
    private double mWorstPBV;
    private double openPrice;
    private boolean mIsIslamic=false;
    private boolean mIsMixed=false;
    private boolean mNotIslamic=false;
    private boolean mIsNotificationEnabled;

    public void setInWatchList(boolean inWatchList) {
        this.inWatchList = inWatchList;
    }

    public boolean isInWatchList() {
        return inWatchList;
    }

    public Stock(String symbol, String name, double price, double PERatio, long volume, double bid, double ask, double PBV, String percentage, double change, String APICode, boolean inWatchList, double purchasedPrice, int quantity, boolean inInvestments, double openPrice, double dayHigh, double dayLow, double m52WHigh, double m52WLow, double bestPE, double worstPE, double bestPBV, double worstPBV, int purity, boolean isNotificationEnabled) {
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
        this.openPrice = openPrice;
        this.mDayHigh = dayHigh;
        this.mDayLow = dayLow;
        this.m52WHigh = m52WHigh;
        this.m52WLow = m52WLow;
        mBestPE = bestPE;
        mWorstPE = worstPE;
        mBestPBV = bestPBV;
        mWorstPBV = worstPBV;
        mIsNotificationEnabled = isNotificationEnabled;

        switch (purity){
            case 0:
                mIsIslamic=true;
                break;
            case 1:
                mIsMixed=true;
                break;
            case 2:
                mNotIslamic=true;
                break;
        }
    }

    public Stock(String APICode) {
        this.APICode = APICode;
        inWatchList=true;
    }

    public Stock() {
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

    public double getDayHigh() {
        return mDayHigh;
    }

    public void setDayHigh(double dayHigh) {
        mDayHigh = dayHigh;
    }

    public double getDayLow() {
        return mDayLow;
    }

    public void setDayLow(double dayLow) {
        mDayLow = dayLow;
    }

    public double getM52WHigh() {
        return m52WHigh;
    }

    public void setM52WHigh(double m52WHigh) {
        this.m52WHigh = m52WHigh;
    }

    public double getM52WLow() {
        return m52WLow;
    }

    public void setM52WLow(double m52WLow) {
        this.m52WLow = m52WLow;
    }

    public double getBestPBV() {
        return mBestPBV;
    }

    public void setBestPBV(double bestPBV) {
        mBestPBV = bestPBV;
    }

    public double getBestPE() {
        return mBestPE;
    }

    public void setBestPE(double bestPE) {
        mBestPE = bestPE;
    }

    public double getWorstPE() {
        return mWorstPE;
    }

    public void setWorstPE(double worstPE) {
        mWorstPE = worstPE;
    }

    public double getWorstPBV() {
        return mWorstPBV;
    }

    public void setWorstPBV(double worstPBV) {
        mWorstPBV = worstPBV;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getInvestmentProfit(){
        return (mPrice-mPurchasedPrice)*mQuantity;
    }

    public double getChangeFromPurchasedPrice(){
        return mPrice-mPurchasedPrice;
    }

    public String getPercentageChangeFromPurchasedPrice(){

        return String.format("%.1f%%",getChangeFromPurchasedPrice()/mPurchasedPrice*100);
    }

    public boolean isIslamic() {
        return mIsIslamic;
    }

    public boolean isMixed() {
        return mIsMixed;
    }

    public boolean isNotIslamic() {
        return mNotIslamic;
    }

    public boolean isNotificationEnabled() {
        return mIsNotificationEnabled;
    }

    public void setIsNotificationEnabled(boolean isNotificationEnabled) {
        mIsNotificationEnabled = isNotificationEnabled;
    }
}

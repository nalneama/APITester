package com.nasserapps.saham.Model;

public class FinancialStatement {
    private double mRevenues;
    private double mCOGS;
    private double mGrossProfit;
    private double mSGA;
    private double mDA;
    private double mOperatingProfit;
    private double mSpecialItems;
    private double mEBIT;
    private double mInterest;
    private double EBT;
    private double mTax;
    private double mEAT;
    private double mMinorityIntrest;

    public FinancialStatement(double revenues, double COGS, double SGA, double DA, double specialItems, double interest, double tax, double minorityInterest,double outstandingStocks) {
        mRevenues = revenues/outstandingStocks;
        mCOGS = COGS/outstandingStocks;
        mSGA = SGA/outstandingStocks;
        mDA = DA/outstandingStocks;
        mSpecialItems = specialItems/outstandingStocks;
        mInterest = interest/outstandingStocks;
        mTax = tax/outstandingStocks;
        mMinorityIntrest = minorityInterest/outstandingStocks;
    }

    public double getRevenues() {
        return mRevenues;
    }

    public double getCOGS() {
        return mCOGS;
    }

    public double getGrossProfit() {
        return mRevenues+mCOGS;
    }

    public double getSGA() {
        return mSGA;
    }

    public double getDA() {
        return mDA;
    }

    public double getOperatingProfit() {
        return getGrossProfit()+mSGA+mDA;
    }

    public double getSpecialItems() {
        return mSpecialItems;
    }

    public double getEBIT() {
        return getOperatingProfit()+mSpecialItems;
    }

    public double getInterest() {
        return mInterest;
    }

    public double getEBT() {
        return getEBIT()+mInterest;
    }

    public double getTax() {
        return mTax;
    }

    public double getEAT() {
        return getEBT()+mTax+mMinorityIntrest;
    }
}

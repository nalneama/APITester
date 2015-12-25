package com.nasserapps.apitester;

/**
 * Created by Nasser on 10/23/15.
 */
public class stockHistoricalData {

    int[] mYears;
    double[] mEPS;
    double[] mBestPERatio;
    double[] mDividend;
    double[] mBestPBVRatio;

    public double[] getEPS() {
        return mEPS;
    }

    public void setEPS(double[] EPS) {
        mEPS = EPS;
    }

    public int[] getYears() {
        return mYears;
    }

    public void setYears(int[] years) {
        mYears = years;
    }

    public double[] getBestPERatio() {
        return mBestPERatio;
    }

    public void setBestPERatio(double[] bestPERatio) {
        mBestPERatio = bestPERatio;
    }

    public double[] getDividend() {
        return mDividend;
    }

    public void setDividend(double[] dividend) {
        mDividend = dividend;
    }

    public double[] getBestPBVRatio() {
        return mBestPBVRatio;
    }

    public void setBestPBVRatio(double[] bestPBVRatio) {
        mBestPBVRatio = bestPBVRatio;
    }
}

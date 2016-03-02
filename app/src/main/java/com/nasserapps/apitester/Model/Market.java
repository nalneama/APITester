package com.nasserapps.apitester.Model;

public class Market {
    private Index mIndex;
    private Commodity mCommodity;

    public Market(Index index, Commodity commodity) {
        mIndex = index;
        mCommodity = commodity;
    }

    public Index getIndex() {
        return mIndex;
    }

    public Commodity getCommodity() {
        return mCommodity;
    }
}

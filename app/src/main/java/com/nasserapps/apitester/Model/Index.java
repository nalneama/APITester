package com.nasserapps.apitester.Model;

import java.util.ArrayList;

public class Index {
    private int mValue;
    private String mPercentage;
    private double mChange;
    private String mName;
    private ArrayList<Stock> mIndexStock;

    public Index(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public String getPercentage() {
        return mPercentage;
    }

    public void setPercentage(String percentage) {
        mPercentage = percentage;
    }

    public double getChange() {
        return mChange;
    }

    public void setChange(double change) {
        mChange = change;
    }

    public void setIndexStock(ArrayList<Stock> indexStock) {
        mIndexStock = indexStock;
    }

    public int getUps(){
        int ups=0;
        for (Stock stock: mIndexStock){
            if(stock.getChange()>0){
                ups++;
            }
        }
        return ups;
    }

    public int getDowns(){
        int downs=0;
        for (Stock stock: mIndexStock){
            if(stock.getChange()<0){
                downs++;
            }
        }
        return downs;
    }

    public int getNCs(){
        int NCs=0;
        for (Stock stock: mIndexStock){
            if(stock.getChange()<0.01 && stock.getChange()>-0.01){
                NCs++;
            }
        }
        return NCs;
    }
}

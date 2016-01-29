package com.nasserapps.apitester.Model;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Wallet {

    List<Investment> mInvestmentList;
    ArrayList<Ticker> mWatchList;

    public Wallet(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    public double getProfit(){
        double profit=0;
        for (int i=0; i<mInvestmentList.size();i++){
            profit= profit + mInvestmentList.get(i).getProfit();
        }
        return profit;
    }

    public double getCapital(){
        double capital=0;
        for (int i=0; i<mInvestmentList.size();i++){
            capital= capital + mInvestmentList.get(i).getCapitalInvested();
        }
        return capital;
    }

    public double getCurrentWorth(){
        double worth=0;
        for (int i=0; i<mInvestmentList.size();i++){
            worth= worth + mInvestmentList.get(i).getCurrentWorth();
        }
        return worth;
    }

    public double getReturn(){
        return getProfit()/getCapital();
    }

    public double getPercentageChange(){
        return (getCurrentWorth()-getCapital())/getCapital();
    }

    public List<Investment> getInvestmentList() {
        return mInvestmentList;
    }

    public void setInvestmentList(List<Investment> investmentList) {
        mInvestmentList = investmentList;
    }

    public void updateInvestments(List<Ticker> stockList){
        for (int i=0; i<stockList.size();i++){
            for (int j=0;j<mInvestmentList.size();j++){
                if(stockList.get(i).getSymbol().equals(mInvestmentList.get(j).getStock().getSymbol())){
                mInvestmentList.get(j).getStock().setPrice(stockList.get(i).getPrice());
                }
            }
        }
    }

    public ArrayList<Ticker> getWatchList() {
        return mWatchList;
    }

    public Wallet(){
        mInvestmentList= new ArrayList<>();
    }

    public void setWatchList(ArrayList<Ticker> watchList) {
        mWatchList = watchList;
    }

    public void setInitialWatchList(){
        mWatchList = new ArrayList<>();
        mWatchList.add(new Ticker("MERS.QA"));
        mWatchList.add(new Ticker("BRES.QA"));
        mWatchList.add(new Ticker("MRDS.QA"));
        mWatchList.add(new Ticker("QIIK.QA"));
        mWatchList.add(new Ticker("QIBK.QA"));
        mWatchList.add(new Ticker("MARK.QA"));
        mWatchList.add(new Ticker("AKHI.QA"));
        mWatchList.add(new Ticker("QIGD.QA"));
    }



    public void updateWatchList(String json) throws JSONException{

        JSONParser jsonParser = new JSONParser(json);
        mWatchList = jsonParser.updateStocks(mWatchList);

    }

    public String getAPIKey(){
        String APICode="";
        for(Ticker stock:mWatchList){
            APICode=APICode+stock.getAPICode()+"+";
        }
        return APICode;
    }


}

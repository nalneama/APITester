package com.nasserapps.apitester.AI.CustomNotificationStatement;

import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.Wallet;

import java.util.ArrayList;

public class Checklist {

    private ArrayList<Rule> rules;

    public Checklist(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public boolean isPassing(Ticker stock){
        boolean response = true;

        for(Rule rule:rules){
            response=rule.evaluate(stock);
            if (response == false){
                return false;
            }
        }
        return response;
    }

    public ArrayList<Ticker> getPassingStocks(Wallet mWallet){
        ArrayList<Ticker> stocksMeetingCriterias = new ArrayList<>();
        ArrayList<Ticker> watchList = mWallet.getWatchList();

        for (Ticker stock: watchList){
            if (isPassing(stock)){
                stocksMeetingCriterias.add(stock);
            }
        }
        return stocksMeetingCriterias;
    }
}

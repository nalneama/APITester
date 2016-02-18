package com.nasserapps.apitester.AI.CustomNotificationStatement;

import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;

import java.util.ArrayList;

public class Checklist {

    private String checklistName;
    private ArrayList<Rule> rules;

    public Checklist(String name) {
        checklistName=name;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

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

    public ArrayList<Ticker> getPassingStocks(User mUser){
        ArrayList<Ticker> stocksMeetingCriterias = new ArrayList<>();
        ArrayList<Ticker> watchList = mUser.getWatchList();

        for (Ticker stock: watchList){
            if (isPassing(stock)){
                stocksMeetingCriterias.add(stock);
            }
        }
        return stocksMeetingCriterias;
    }
}

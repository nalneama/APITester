package com.nasserapps.saham.Model.Checklists;

import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.User;

import java.util.ArrayList;

public class Checklist {

    private String checklistName;
    private ArrayList<Rule> rules;
    private boolean mNotificationMuted;

    public Checklist(String name) {
        checklistName=name;
        rules = new ArrayList<>();
        mNotificationMuted=false;
    }

    public Checklist() {
        checklistName="Untitled Checklist";
        rules = new ArrayList<>();
        mNotificationMuted=false;
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

    public boolean isPassing(Stock stock){
        boolean response = true;

        for(Rule rule:rules){
            response=rule.evaluate(stock);
            if (response == false){
                return false;
            }
        }
        return response;
    }

    public ArrayList<Stock> getPassingStocks(User mUser){
        ArrayList<Stock> stocksMeetingCriterias = new ArrayList<>();
        ArrayList<Stock> watchList = mUser.getWatchList();

        for (Stock stock: watchList){
            if (isPassing(stock)){
                stocksMeetingCriterias.add(stock);
            }
        }
        return stocksMeetingCriterias;
    }

    public void addRule(Rule rule){
        rules.add(rule);
    }

    public void removeRule(Rule rule){
        rules.remove(rule);
    }


    public String getNotificationStatement(){
        String statement="";
        for (Rule rule: rules){
            statement = statement + rule.getRuleStatement() +"%n";
        }
        return statement.substring(0,statement.length()-2);
    }
}

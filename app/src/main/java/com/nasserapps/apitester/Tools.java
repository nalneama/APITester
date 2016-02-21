package com.nasserapps.apitester;

import com.nasserapps.apitester.Model.Ticker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Tools {


    // Sort lists as per the following criteria
    public static ArrayList<Ticker> sort(ArrayList<Ticker> list, String string){
        HashMap<String, Comparator<Ticker>> comparatorHashMap =  new HashMap<>();

        String[] sortingOptions = new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price"};

        // A-Z Comparator
        comparatorHashMap.put(sortingOptions[0], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return stock1.getName().compareTo(stock2.getName());}});

        //Book Value
        comparatorHashMap.put(sortingOptions[1],new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock1.getPBV(), stock2.getPBV());}});

        // Gain
        comparatorHashMap.put(sortingOptions[2], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(Double.parseDouble(stock2.getPercentage().substring(0, stock2.getPercentage().length() - 1)), Double.parseDouble(stock1.getPercentage().substring(0, stock1.getPercentage().length() - 1)));}});

        // PE Ratio
        comparatorHashMap.put(sortingOptions[3], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock1.getPERatio(), stock2.getPERatio());}});

        // Price
        comparatorHashMap.put(sortingOptions[4], new Comparator<Ticker>() {
            @Override
            public int compare(Ticker stock1, Ticker stock2) {
                return Double.compare(stock2.getPrice(), stock1.getPrice());}});


        //Sort by the Option and return the sorted list
        Collections.sort(list, comparatorHashMap.get(string));

        return list;
    }



    //General handy methods
    public static Ticker getStockFromList(String symbol, ArrayList<Ticker> list){
        return Tools.convertListToMap(list).get(symbol);
    }

    public static ArrayList<Ticker> updateStockInList(Ticker ticker,ArrayList<Ticker> list){
        HashMap<String,Ticker> map= Tools.convertListToMap(list);
        map.put(ticker.getSymbol(),ticker);
        return Tools.convertMapToList(map);
    }

    public static HashMap<String,Ticker> convertListToMap(ArrayList<Ticker> list){
        HashMap<String,Ticker> map= new HashMap<>();
        for(Ticker ticker:list){
            map.put(ticker.getSymbol(),ticker);
        }
        return map;
    }

    public static ArrayList<Ticker> convertMapToList(HashMap<String,Ticker> map){
        ArrayList<Ticker> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }
}

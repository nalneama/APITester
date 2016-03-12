package com.nasserapps.saham.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.nasserapps.saham.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Tools {

    public static String[] sortingOptions = new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price", "Volume"};

    // Sort lists as per the following criteria
    public static ArrayList<Stock> sort(ArrayList<Stock> list, String string){
        HashMap<String, Comparator<Stock>> comparatorHashMap =  new HashMap<>();



        // A-Z Comparator
        comparatorHashMap.put(sortingOptions[0], new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return stock1.getName().compareTo(stock2.getName());}});

        //Book Value
        comparatorHashMap.put(sortingOptions[1],new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return Double.compare(stock1.getPBV(), stock2.getPBV());}});

        // Gain
        comparatorHashMap.put(sortingOptions[2], new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return Double.compare(Double.parseDouble(stock2.getPercentage().substring(0, stock2.getPercentage().length() - 1)), Double.parseDouble(stock1.getPercentage().substring(0, stock1.getPercentage().length() - 1)));}});

        // PE Ratio
        comparatorHashMap.put(sortingOptions[3], new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return Double.compare(stock1.getPERatio(), stock2.getPERatio());}});

        // Price
        comparatorHashMap.put(sortingOptions[4], new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return Double.compare(stock2.getPrice(), stock1.getPrice());}});

        // Price
        comparatorHashMap.put(sortingOptions[5], new Comparator<Stock>() {
            @Override
            public int compare(Stock stock1, Stock stock2) {
                return Double.compare(stock2.getVolume(), stock1.getVolume());}});

        //Sort by the Option and return the sorted list
        Collections.sort(list, comparatorHashMap.get(string));

        return list;
    }



    //General handy methods
    public static Stock getStockFromList(String symbol, ArrayList<Stock> list){
        return Tools.convertListToMap(list).get(symbol);
    }

    public static ArrayList<Stock> updateStockInList(Stock stock,ArrayList<Stock> list){
        HashMap<String,Stock> map= Tools.convertListToMap(list);
        map.put(stock.getSymbol(), stock);
        return Tools.convertMapToList(map);
    }

    public static HashMap<String,Stock> convertListToMap(ArrayList<Stock> list){
        HashMap<String,Stock> map= new HashMap<>();
        for(Stock stock :list){
            map.put(stock.getSymbol(), stock);
        }
        return map;
    }

    public static ArrayList<Stock> convertMapToList(HashMap<String,Stock> map){
        ArrayList<Stock> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    public static int getTextColor(Context context, double change){
        int color;
        if(change < -0.01){
            color=  R.color.red600;
        }
        else if(change>0.01){
            color = R.color.green600;
        }
        else{
            color =  R.color.yellow600;
        }
        return context.getResources().getColor(color);
    }

    public static Drawable getArrowDirection(Context context, double change){
        int drawable;
        if(change < -0.01){
            drawable=  R.drawable.arrow_down;
        }
        else if(change>0.01){
            drawable = R.drawable.arrow_up_bold;
        }
        else{
            drawable = R.drawable.minus;
        }
        return context.getDrawable(drawable);
    }

    public static String getProfitOrLossSting(double profit){
        if (profit>0){
            return "Profit";
        }
        else {
            return "Loss";
        }
    }
}

package com.nasserapps.apitester.Model.Database;

import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nasser on 10/1/15.
 * Sets the bind the JSON data to the CurrentStock object.
 * Takes JSON string as a parameter and output CurrentStock object.
 */
public class JSONParser {

    private JSONArray mQuotes;
    private int mCount;
    private ArrayList<Ticker> mAllStocksInDB;
    private ArrayList<Ticker> mIndexes;
    private ArrayList<Ticker> mStocks;

    public JSONParser(String jsonData, ArrayList<Ticker> allStocksInDB) throws JSONException {

        mIndexes = new ArrayList<>();
        mStocks = new ArrayList<>();
        mAllStocksInDB = allStocksInDB;

        JSONObject APIResults = new JSONObject(jsonData);
        JSONObject query = APIResults.getJSONObject("query");
        JSONObject results;

        mCount = query.getInt("count");
        switch (mCount) {
            case 0:
                mQuotes = new JSONArray();
                break;
            case 1:
                results = query.getJSONObject("results");
                JSONObject mJSONObject = results.getJSONObject("quote");
                mQuotes = new JSONArray("["+mJSONObject+"]");
                break;
            default:
                results = query.getJSONObject("results");
                Object mObject = results.get("quote");
                mQuotes = new JSONArray(mObject.toString().trim());
                break;
        }

        JSONObject stock;
        for (int i=0;i < mQuotes.length(); i++){
            stock = mQuotes.getJSONObject(i);
            if(!stock.isNull("StockExchange")) {
                mStocks.add(extractStock(stock));
            }
            else{
                mIndexes.add(extractIndex(stock));
            }

        }


    }

    private Ticker extractStock(JSONObject mQuote) throws JSONException{
        String symbol = mQuote.getString("Symbol").substring(0, 4);
        Ticker stock = Tools.getStockFromList(symbol, mAllStocksInDB);
        //Todo uncomment the two parameters
        //stock.setSymbol(mQuote.getString("Symbol").substring(0, 4));
        if(!mQuote.isNull("PERatio")){
        stock.setPERatio(mQuote.getDouble("PERatio"));}
        stock.setVolume(mQuote.getLong("Volume"));
        stock.setPBV(mQuote.getDouble("PriceBook"));
        stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));
        if(!mQuote.isNull("Bid")){
        stock.setBid(mQuote.getDouble("Bid"));}
        if(!mQuote.isNull("Ask")){
        stock.setAsk(mQuote.getDouble("Ask"));}
        //stock.setName(mQuote.getString("Name"));
        stock.setPercentage(mQuote.getString("PercentChange"));
        stock.setChange(mQuote.getDouble("Change"));
        stock.setDayHigh(mQuote.getDouble("DaysHigh"));
        stock.setDayLow(mQuote.getDouble("DaysLow"));
        stock.setM52WHigh(mQuote.getDouble("YearHigh"));
        stock.setM52WLow(mQuote.getDouble("YearLow"));
        if(!mQuote.isNull("Open")){
        stock.setOpenPrice(mQuote.getDouble("Open"));}

        //Set Calculations
        stock.setBestPE(stock.getM52WLow()*stock.getPERatio()/stock.getPrice());
        stock.setWorstPE(stock.getM52WHigh() * stock.getPERatio() / stock.getPrice());
        stock.setBestPBV(stock.getM52WLow() * stock.getPBV() / stock.getPrice());
            stock.setWorstPBV(stock.getM52WHigh() * stock.getPBV()/stock.getPrice());


        return stock;
    }
    private Ticker extractIndex(JSONObject mQuote) throws JSONException{
        Ticker stock = new Ticker();
        //Todo uncomment the two parameters
        stock.setSymbol(mQuote.getString("Symbol").substring(0, 3));
        //stock.setPERatio(mQuote.getDouble("PERatio"));
        if(!mQuote.isNull("Volume")){
        stock.setVolume(mQuote.getLong("Volume"));}
        //stock.setPBV(mQuote.getDouble("PriceBook"));
        if(!mQuote.isNull("LastTradePriceOnly")){
        stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));}
        if(!mQuote.isNull("Bid")){
        stock.setBid(mQuote.getDouble("Bid"));}
        if(!mQuote.isNull("Ask")){
        stock.setAsk(mQuote.getDouble("Ask"));}
        stock.setName(mQuote.getString("Name"));
        stock.setPercentage(mQuote.getString("PercentChange"));
        if(!mQuote.isNull("Change")){
        stock.setChange(mQuote.getDouble("Change"));}

        return stock;
    }

    public ArrayList<Ticker> getStocks() throws JSONException{
        return mStocks;
    }

    public ArrayList<Ticker> getIndexes() {
        return mIndexes;
    }


}

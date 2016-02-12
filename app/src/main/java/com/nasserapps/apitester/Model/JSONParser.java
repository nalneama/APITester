package com.nasserapps.apitester.Model;

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
    private ArrayList<Ticker> mIndexes;
    private ArrayList<Ticker> mStocks;

    public JSONParser(String jsonData) throws JSONException {

        mIndexes = new ArrayList<>();
        mStocks = new ArrayList<>();

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
            if(!stock.isNull("PERatio")) {
                mStocks.add(extractStock(stock));
            }
            else{
                mIndexes.add(extractIndex(stock));
            }

        }


    }

    private Ticker extractStock(JSONObject mQuote) throws JSONException{
        Ticker stock = new Ticker();
        //Todo uncomment the two parameters
        stock.setSymbol(mQuote.getString("Symbol").substring(0, 4));
        if(!mQuote.isNull("PERatio")){
        stock.setPERatio(mQuote.getDouble("PERatio"));}
        stock.setVolume(mQuote.getLong("Volume"));
        stock.setPBV(mQuote.getDouble("PriceBook"));
        stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));
        if(!mQuote.isNull("Bid")){
        stock.setBid(mQuote.getDouble("Bid"));}
        if(!mQuote.isNull("Ask")){
        stock.setAsk(mQuote.getDouble("Ask"));}
        stock.setName(mQuote.getString("Name"));
        stock.setPercentage(mQuote.getString("PercentChange"));
        stock.setChange(mQuote.getDouble("Change"));

        return stock;
    }
    private Ticker extractIndex(JSONObject mQuote) throws JSONException{
        Ticker stock = new Ticker();
        //Todo uncomment the two parameters
        stock.setSymbol(mQuote.getString("Symbol").substring(0, 3));
        //stock.setPERatio(mQuote.getDouble("PERatio"));
        stock.setVolume(mQuote.getLong("Volume"));
        //stock.setPBV(mQuote.getDouble("PriceBook"));
        stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));
        if(!mQuote.isNull("Bid")){
        stock.setBid(mQuote.getDouble("Bid"));}
        if(!mQuote.isNull("Ask")){
        stock.setAsk(mQuote.getDouble("Ask"));}
        stock.setName(mQuote.getString("Name"));
        stock.setPercentage(mQuote.getString("PercentChange"));
        stock.setChange(mQuote.getDouble("Change"));

        return stock;
    }

    public ArrayList<Ticker> getStocks() throws JSONException{
        return mStocks;
    }

    public ArrayList<Ticker> getIndexes() {
        return mIndexes;
    }

    private Ticker updateStock(Ticker stock,JSONObject mQuote) throws JSONException{
        //Todo uncomment the two parameters
        if (stock.getAPICode().equals(mQuote.get("Symbol"))){
            stock.setSymbol(mQuote.getString("Symbol").substring(0, 4));
            if(!mQuote.isNull("PERatio")) {
                stock.setPERatio(mQuote.getDouble("PERatio"));}
            stock.setVolume(mQuote.getLong("Volume"));
            stock.setPBV(mQuote.getDouble("PriceBook"));
            stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));
            if(!mQuote.isNull("Bid")){
                stock.setBid(mQuote.getDouble("Bid"));}
            if(!mQuote.isNull("Ask")){
                stock.setAsk(mQuote.getDouble("Ask"));}
            stock.setName(mQuote.getString("Name"));
            stock.setPercentage(mQuote.getString("PercentChange"));
            stock.setChange(mQuote.getDouble("Change"));
        }
        return stock;
    }

    public ArrayList<Ticker> updateStocks(ArrayList<Ticker> stockArrayList) throws JSONException{

        for(int i=0;i<stockArrayList.size();i++){
            for(int j=0;j<stockArrayList.size();j++){
                updateStock(stockArrayList.get(i),mQuotes.getJSONObject(j));
            }
        }
        return stockArrayList;
    }
}

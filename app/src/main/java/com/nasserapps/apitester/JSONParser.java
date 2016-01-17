package com.nasserapps.apitester;

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

    public JSONParser(String jsonData) throws JSONException {
        JSONObject APIResults = new JSONObject(jsonData);
        JSONObject query = APIResults.getJSONObject("query");
        JSONObject results;

        //TODO if count =0, 1 or more;
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
    }

    private Stock tieData(JSONObject mQuote) throws JSONException{
        Stock stock = new Stock();

        stock.setSymbol(mQuote.getString("Symbol"));
        stock.setPERatio(mQuote.getDouble("PERatio"));
        stock.setVolume(mQuote.getLong("Volume"));
        stock.setPBV(mQuote.getDouble("PriceBook"));
        stock.setPrice(mQuote.getDouble("LastTradePriceOnly"));
        stock.setDemand(mQuote.getDouble("Bid"));
        stock.setSupply(mQuote.getDouble("Ask"));
        stock.setName(mQuote.getString("Name"));
        stock.setPercentage(mQuote.getString("PercentChange"));
        stock.setChange(mQuote.getDouble("Change"));

        return stock;
    }

    public ArrayList<Stock> getStocks() throws JSONException{
        JSONObject stock;
        ArrayList<Stock> stocks = new ArrayList<>(1);
        for (int i=0;i < mQuotes.length(); i++){
            stock = mQuotes.getJSONObject(i);
            stocks.add(tieData(stock));

        }
        return  stocks;
    }
}

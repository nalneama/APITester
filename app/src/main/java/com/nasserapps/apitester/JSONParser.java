package com.nasserapps.apitester;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nasser on 10/1/15.
 * Sets the bind the JSON data to the CurrentStock object.
 * Takes JSON string as a parameter and output CurrentStock object.
 */
public class JSONParser {

    public JSONObject getQuote() {
        return mQuote;
    }

    private JSONObject mQuote;

    public JSONParser(String jsonData) throws JSONException{
        JSONObject APIResults = new JSONObject(jsonData);

        JSONObject query = APIResults.getJSONObject("query");
        JSONObject results = query.getJSONObject("results");
        mQuote = results.getJSONObject("quote");
    }

    public CurrentStock tieData() throws JSONException{
        CurrentStock stock = new CurrentStock();

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
}

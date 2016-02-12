package com.nasserapps.apitester.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSource {

    private Context mContext;
    private SqlLiteDbHelper mSqlLiteDbHelper;
    private SharedPreferences memory;
    private SharedPreferences.Editor memoryWriter;

    private ArrayList<String> mStocksList;

    private static final String MEMORY_KEY="appMemory";
    private static final String MEMORY_WALLET_DATA="WalletData";

    public DataSource(Context context){
        mContext = context;

        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        SQLiteDatabase database = mSqlLiteDbHelper.openDataBase();
        database.close();

        memory = mContext.getSharedPreferences(MEMORY_KEY, mContext.MODE_PRIVATE);
    }

    //For testing only
    public Ticker getStock(){
        SQLiteDatabase database = mSqlLiteDbHelper.openDataBase();

        Cursor cursor = database.rawQuery("SELECT * FROM stocks_db", null);
        if (cursor != null && cursor.moveToFirst()){
            // Get the API code from cursor.getString(2)
            Ticker ticker = new Ticker(cursor.getString(2));

            // return stock
            cursor.close();
            database.close();

            return ticker;

        }
        return new Ticker("else");
    }

    public HashMap<String,Ticker> getStocks(){
        SQLiteDatabase database = mSqlLiteDbHelper.openDataBase();

        HashMap<String,Ticker> AllStocks= new HashMap<>();

        Cursor cursor = database.rawQuery("SELECT * FROM stocks_db", null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                // Get the API code from cursor.getString(2)
                //Ticker ticker = new Ticker(cursor.getString(1));
                //Ticker ticker = new Ticker(cursor.getString(0),cursor.getString(1),cursor.getDouble(15),cursor.getDouble(11),cursor.getLong(6),cursor.getDouble(10),cursor.getDouble(9),cursor.getDouble(12),cursor.getString(14),cursor.getDouble(13),cursor.getString(2),cursor.getInt(5)==1);
                Ticker ticker = new Ticker(cursor.getString(0),cursor.getString(1),cursor.getDouble(15),cursor.getDouble(11),cursor.getLong(6),cursor.getDouble(10),cursor.getDouble(9),cursor.getDouble(12),cursor.getString(14),cursor.getDouble(13),cursor.getString(2),cursor.getInt(5)==1,cursor.getDouble(25),cursor.getInt(26),cursor.getInt(24)==1);
                AllStocks.put(ticker.APICode,ticker);
                // return stock
            }while(cursor.moveToNext());
            cursor.close();
            database.close();

            return AllStocks;

        }
        return null;
    }

    public Ticker getStock(String symbol){
        return getStocks().get(symbol);
    }





    public ArrayList<Ticker> loadStocksDataFromMemory() throws JSONException{
        return new JSONParser(getStoredStockData()).getStocks();
    }

    //Can be deleted after refactoring
    private String getStoredStockData(){

        //Initiate SharedPreferences
        return memory.getString("WalletData", "No Data");
    }

    //To be made private
    public String getAPIURL(String APIKey) {
        //Initiate SharedPreferences
        // https://www.xignite.com/Register?ReturnURL=%2fproduct%2fforex%2fapi%2fListCurrencies%2f

        String stockURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%3D%22"
                + APIKey
                + "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        return stockURL;
    }

    //This is working perfect
    public void saveStockDataInMemory(String data){
        memoryWriter = memory.edit();
        memoryWriter.putString("mStockData", data).commit();
    }

    public void saveWallet(Wallet wallet){
        Gson gson = new Gson();
        memoryWriter = memory.edit();
        memoryWriter.putString("WalletData", gson.toJson(wallet)).commit();
    }

    public Wallet getWallet(){
        String data = memory.getString(MEMORY_WALLET_DATA, null);
        Gson gson = new Gson();
        Wallet wallet = gson.fromJson(data,
                Wallet.class);

        return wallet;
    }



}

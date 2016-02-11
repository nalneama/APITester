package com.nasserapps.apitester.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

public class DataSource {

    private Context mContext;
    private SqlLiteDbHelper mSqlLiteDbHelper;
    private SharedPreferences memory;
    private SharedPreferences.Editor memoryWriter;

    private ArrayList<String> mStocksList;

    private static final String MEMORY_KEY="appMemory";
    private static final String SETTINGS_AI_ACTIVATED ="isAIActivated";
    private static final String MEMORY_WALLET_DATA="WalletData";
    private static final String SETTINGS_NOTIFICATION_STATUS="isNotificationEnabled";

    public DataSource(Context context){
        mContext = context;

//      mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
//      SQLiteDatabase database = mSqlLiteDbHelper.getReadableDatabase();
//      database.close();

        memory = mContext.getSharedPreferences(MEMORY_KEY, mContext.MODE_PRIVATE);
    }

//    private SQLiteDatabase open(){
//        return mSqlLiteDbHelper.getWritableDatabase();
//    }
//
//    private void close(SQLiteDatabase database){
//        database.close();
//    }
//
//    public Ticker getStock(){
//        SQLiteDatabase database = open();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM stocks_db", null);
//        if (cursor != null && cursor.moveToFirst()){
//            // Get the API code from cursor.getString(2)
//            Ticker ticker = new Ticker(cursor.getString(2));
//            // return stock
//            cursor.close();
//            close(database);
//
//            return ticker;
//
//        }
//        return new Ticker("else");
//    }







    public boolean isStoredDataAvailable (){
        return !getStoredStockData().contains("No Data");
    }

    public ArrayList<Ticker> loadStocksDataFromMemory() throws JSONException{
        return new JSONParser(getStoredStockData()).getStocks();
    }
    public ArrayList<Ticker> loadIndexesDataFromMemory() throws JSONException{
        return new JSONParser(getStoredStockData()).getIndexes();
    }

    public ArrayList<Ticker> loadStockDataFromResponse(String data) throws JSONException{
        return new JSONParser(data).getStocks();
    }

    public ArrayList<Ticker> loadIndexDataFromResponse(String data) throws JSONException{
        return new JSONParser(data).getIndexes();
    }

    //TODO save the order of stocks in the memory and save the new sort option in memory
    public ArrayList<String> getStocksNameList() {
        return mStocksList;
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





    public boolean isNotificationEnabled(){
        return memory.getBoolean(SETTINGS_NOTIFICATION_STATUS, false);
    }

    public void setNotificationStatus(boolean b){
        memoryWriter = memory.edit();
        memoryWriter.putBoolean(SETTINGS_NOTIFICATION_STATUS,b).apply();
    }

    public boolean isAIActivated(){
        return memory.getBoolean(SETTINGS_AI_ACTIVATED, false);
    }

    public void setIsAIActivated(boolean b){
        memoryWriter = memory.edit();
        memoryWriter.putBoolean(SETTINGS_AI_ACTIVATED,b).apply();
    }

}

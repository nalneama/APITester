package com.nasserapps.apitester.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nasserapps.apitester.Model.Ticker;

import java.util.HashMap;

public class DataSource {

    private Context mContext;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    private static final String MEMORY_WALLET_DATA="WalletData";


    //Constructor
    public DataSource(Context context){
        mContext = context;

        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        SQLiteDatabase database = mSqlLiteDbHelper.openDataBase();
        database.close();

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
                //Ticker ticker = new Ticker(cursor.getString(0),cursor.getString(1),cursor.getDouble(15),cursor.getDouble(11),cursor.getLong(6),cursor.getDouble(10),cursor.getDouble(9),cursor.getDouble(12),cursor.getString(14),cursor.getDouble(13),cursor.getString(2),cursor.getInt(5)==1,cursor.getDouble(25),cursor.getInt(26),cursor.getInt(24)==1);
                Ticker ticker = new Ticker(cursor.getString(0),cursor.getString(1),cursor.getDouble(15),cursor.getDouble(11),cursor.getLong(6),cursor.getDouble(10),cursor.getDouble(9),cursor.getDouble(12),cursor.getString(14),cursor.getDouble(13),cursor.getString(2),cursor.getInt(5)==1,cursor.getDouble(25),cursor.getInt(26),cursor.getInt(24)==1,cursor.getDouble(16),cursor.getDouble(8),cursor.getDouble(7),cursor.getDouble(18),cursor.getDouble(19),cursor.getDouble(20),cursor.getDouble(21),cursor.getDouble(22),cursor.getDouble(23));
                AllStocks.put(ticker.getAPICode(),ticker);
                // return stock
            }while(cursor.moveToNext());
            cursor.close();
            database.close();

            return AllStocks;

        }
        return null;
    }

    public void updateStock(Ticker ticker){
        SQLiteDatabase database = mSqlLiteDbHelper.openDataBase();
        database.beginTransaction();

        ContentValues updateStockValues = new ContentValues();
        //TODO put all values and all stocks in the database + add islamic, mix or non-islamic
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_IN_WATCHLIST, ticker.isInWatchList());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_VOLUME, ticker.getVolume());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_DAY_LOW,ticker.getDayLow());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_DAY_HIGH,ticker.getDayHigh());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_ASK, ticker.getAsk());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_BID, ticker.getBid());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PE_RATIO, ticker.getPERatio());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PBV_RATIO, ticker.getPBV());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PRICE_CHANGE, ticker.getChange());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PERCENTAGE_CHANGE, ticker.getPercentage());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_CURRENT_PRICE, ticker.getPrice());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_OPEN_PRICE, ticker.getOpenPrice());
        //updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_MARKET_CAP, ticker.getMarketCap;
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_HIGH, ticker.getM52WHigh());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_LOW, ticker.getM52WLow());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_BEST_PE_RATIO, ticker.getBestPE());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_WORST_PE_RATIO, ticker.getWorstPE());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_BEST_PBV_RATIO, ticker.getBestPBV());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_52W_WORST_PBV_RATIO, ticker.getWorstPBV());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_IN_INVESTMENT, ticker.isInInvestments());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PURCHASED_PRICE, ticker.getPurchasedPrice());
        updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_PURCHASED_QUANTITY, ticker.getQuantity());

                database.update(SqlLiteDbHelper.TABLE_STOCKS,
                        updateStockValues,
                        String.format("%s =\"%s\"", SqlLiteDbHelper.COLUMN_STOCK_SYMBOL, ticker.getSymbol()),
                        null);
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }


    //Helper methods



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


    public Ticker getStock(String symbol){
        return getStocks().get(symbol);
    }

}

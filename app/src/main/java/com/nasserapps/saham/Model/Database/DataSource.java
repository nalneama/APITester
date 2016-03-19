package com.nasserapps.saham.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nasserapps.saham.Model.Commodity;
import com.nasserapps.saham.Model.Index;
import com.nasserapps.saham.Model.Stock;

import java.util.ArrayList;
import java.util.HashMap;

//Also known as data layer
public class DataSource {

    private Context mContext;
    private FinancialDBHelper mFinancialDBHelper;


    //Constructor
    public DataSource(Context context){
        mContext = context;

        mFinancialDBHelper = new FinancialDBHelper(mContext);
        SQLiteDatabase database = mFinancialDBHelper.getReadableDatabase();
        database.close();

    }


    //Stocks
    public HashMap<String,Stock> getStocks(){
        SQLiteDatabase database = mFinancialDBHelper.getWritableDatabase();
        database.beginTransaction();

        HashMap<String,Stock> AllStocks= new HashMap<>();

        Cursor cursor = database.rawQuery("SELECT * FROM stocks_db", null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                // Get the API code from cursor.getString(2)
                Stock stock = new Stock(cursor.getString(0),cursor.getString(1),cursor.getDouble(15),cursor.getDouble(11),cursor.getLong(6),cursor.getDouble(10),cursor.getDouble(9),cursor.getDouble(12),cursor.getString(14),cursor.getDouble(13),cursor.getString(2),cursor.getInt(5)==1,cursor.getDouble(25),cursor.getInt(26),cursor.getInt(24)==1,cursor.getDouble(16),cursor.getDouble(8),cursor.getDouble(7),cursor.getDouble(18),cursor.getDouble(19),cursor.getDouble(20),cursor.getDouble(21),cursor.getDouble(22),cursor.getDouble(23),cursor.getInt(27),cursor.getInt(28)==1);
                AllStocks.put(stock.getAPICode(), stock);
                // return stock
            }while(cursor.moveToNext());
            cursor.close();
            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();

            return AllStocks;

        }
        return null;
    }

    public void updateStock(Stock stock){
        SQLiteDatabase database = mFinancialDBHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues updateStockValues = new ContentValues();
        //TODO Update the database in order to include financial ratios and type of industry
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_IN_WATCHLIST, stock.isInWatchList());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_VOLUME, stock.getVolume());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_DAY_LOW, stock.getDayLow());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_DAY_HIGH, stock.getDayHigh());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_ASK, stock.getAsk());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_BID, stock.getBid());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PE_RATIO, stock.getPERatio());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PBV_RATIO, stock.getPBV());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PRICE_CHANGE, stock.getChange());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PERCENTAGE_CHANGE, stock.getPercentage());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_CURRENT_PRICE, stock.getPrice());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_OPEN_PRICE, stock.getOpenPrice());
        //updateStockValues.put(SqlLiteDbHelper.COLUMN_STOCK_MARKET_CAP, ticker.getMarketCap;
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_HIGH, stock.getM52WHigh());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_LOW, stock.getM52WLow());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_BEST_PE_RATIO, stock.getBestPE());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_WORST_PE_RATIO, stock.getWorstPE());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_BEST_PBV_RATIO, stock.getBestPBV());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_52W_WORST_PBV_RATIO, stock.getWorstPBV());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_IN_INVESTMENT, stock.isInInvestments());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PURCHASED_PRICE, stock.getPurchasedPrice());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_PURCHASED_QUANTITY, stock.getQuantity());
        updateStockValues.put(DataContract.StocksEntry.COLUMN_STOCK_NOTIFICATION,stock.isNotificationEnabled());

                database.update(DataContract.StocksEntry.TABLE_NAME,
                        updateStockValues,
                        String.format("%s =\"%s\"", DataContract.StocksEntry.COLUMN_STOCK_SYMBOL, stock.getSymbol()),
                        null);
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }



    // Index
    public Index getIndex(){
        Index qeIndex = new Index("Qatar Exchange");
        qeIndex.setChange(14);
        qeIndex.setPercentage("1.3%");
        qeIndex.setValue(9420);
        ArrayList<Stock> allStocks = new ArrayList<>();
        allStocks.addAll(getStocks().values());
        qeIndex.setIndexStock(allStocks);
        return qeIndex;
    }



    // Commodity
    public Commodity getBrent(){
        Commodity brent = new Commodity("OIL");
        brent.setPercentage("-3.1%");
        brent.setPrice(36.3);
        brent.setChange(-1.2);
        return brent;
    }


    //Helper methods

}

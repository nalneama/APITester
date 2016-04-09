package com.nasserapps.saham.Model.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {

    // 1.0 BASE_CONTENT_URI is the root path of the complete content provider
    public static final String CONTENT_AUTHORITY = "com.nasserapps.saham.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // 1.1 All the paths under the base uri
    public static final String PATH_STOCK = "stock";
    public static final String PATH_COMMODITY = "commodity";
    public static final String PATH_INDEX = "index";
    //GNRI.QA







    //2.0 The Stock Path
    public static final class StocksEntry implements BaseColumns{
        // 2.1 Table Name
        public static final String TABLE_NAME="stocks_db";
        // 2.2 Columns Names
        public static final String COLUMN_STOCK_SYMBOL="symbol";
        public static final String COLUMN_STOCK_NAME="name_english";
        public static final String COLUMN_STOCK_API_CODE="api_code";
        public static final String COLUMN_STOCK_EPS="eps";
        public static final String COLUMN_STOCK_BOOK_VALUE="book_value";
        public static final String COLUMN_STOCK_IN_WATCHLIST="in_watchlist";
        public static final String COLUMN_STOCK_VOLUME="volume";
        public static final String COLUMN_STOCK_DAY_LOW="day_low";
        public static final String COLUMN_STOCK_DAY_HIGH="day_high";
        public static final String COLUMN_STOCK_ASK="ask";
        public static final String COLUMN_STOCK_BID="bid";
        public static final String COLUMN_STOCK_PE_RATIO="pe_ratio";
        public static final String COLUMN_STOCK_PBV_RATIO="pbv_ratio";
        public static final String COLUMN_STOCK_PRICE_CHANGE="change";
        public static final String COLUMN_STOCK_PERCENTAGE_CHANGE="percentage_change";
        public static final String COLUMN_STOCK_CURRENT_PRICE="current_price";
        public static final String COLUMN_STOCK_OPEN_PRICE="open_price";
        public static final String COLUMN_STOCK_MARKET_CAP="market_cap";
        public static final String COLUMN_STOCK_52W_HIGH="m52w_high";
        public static final String COLUMN_STOCK_52W_LOW="m52w_low";
        public static final String COLUMN_STOCK_52W_BEST_PE_RATIO="m52w_best_pe_ratio";
        public static final String COLUMN_STOCK_52W_WORST_PE_RATIO="m52w_worst_pe_ratio";
        public static final String COLUMN_STOCK_52W_BEST_PBV_RATIO="m52w_best_pbv_ratio";
        public static final String COLUMN_STOCK_52W_WORST_PBV_RATIO="m52w_worst_pbv_ratio";
        public static final String COLUMN_STOCK_IN_INVESTMENT="in_investment";
        public static final String COLUMN_STOCK_PURCHASED_PRICE="purchased_price";
        public static final String COLUMN_STOCK_PURCHASED_QUANTITY="purchased_quantity";
        public static final String COLUMN_STOCK_PURITY="is_islamic";
        public static final String COLUMN_STOCK_NOTIFICATION="notification_enabled";
        public static final String COLUMN_STOCK_NOTE="personal_note";

        //2.3 The content uri for the stock table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOCK).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK; //Return multiple items
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK; //Return one item

        //https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3650138769 to get more types and queries


        // Helper method: get the Uri from the row id
        public static Uri buildStockUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        // Get one stock
        public static Uri buildStockFromSymbol(String symbol){
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }


    }










    // 3.0 The Commodity Path
    public static final class CommoditiesEntry implements BaseColumns{
        // 3.1 Table Name
        public static final String TABLE_NAME="commodities_db";
        // 3.2 Columns Names
        public static final String COLUMN_COMMODITY_SYMBOL="symbol";
        public static final String _ID="id";
        public static final String COLUMN_COMMODITY_NAME="name_english";
        public static final String COLUMN_COMMODITY_EPS="eps";
        public static final String COLUMN_COMMODITY_PRICE_CHANGE="change";
        public static final String COLUMN_COMMODITY_PERCENTAGE_CHANGE="percentage_change";
        public static final String COLUMN_COMMODITY_CURRENT_PRICE="current_price";


        // 3.3 The content uri for the Commodity table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMODITY).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMODITY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMODITY;


        // Helper method: get the Uri from the row id
        public static Uri buildCommodityUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        // Get one Commodity
        public static Uri buildCommodityFromSymbol(String symbol){
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }
    }










    // 4.0 The Index Path
    public static final class IndexesEntry implements BaseColumns{
        // 4.1 Table Name
        public static final String TABLE_NAME="indexes_db";
        // 4.2 Columns Names
        public static final String COLUMN_STOCK_SYMBOL="symbol";
        public static final String COLUMN_STOCK_NAME="name_english";
        public static final String COLUMN_STOCK_EPS="eps";
        public static final String COLUMN_STOCK_PRICE_CHANGE="change";
        public static final String COLUMN_STOCK_PERCENTAGE_CHANGE="percentage_change";
        public static final String COLUMN_STOCK_CURRENT_PRICE="current_price";

        //4.3 The content uri for the Index table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INDEX).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INDEX;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INDEX;

        // Helper method: get the Uri from the row id
        public static Uri buildIndexUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        // Get one Index
        public static Uri buildIndexFromSymbol(String symbol){
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }
    }



}

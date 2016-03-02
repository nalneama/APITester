package com.nasserapps.apitester.Model.Database;

import android.provider.BaseColumns;

public class DataContract {

    public static final class StocksEntry implements BaseColumns{
        // Tables Names
        public static final String TABLE_NAME="stocks_db";
        //Columns Names
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

    }



    public static final class IndexesEntry implements BaseColumns{
        // Tables Names
        public static final String TABLE_NAME="indexes_db";
        //Columns Names
        public static final String COLUMN_STOCK_SYMBOL="symbol";
        public static final String COLUMN_STOCK_NAME="name_english";
        public static final String COLUMN_STOCK_EPS="eps";
        public static final String COLUMN_STOCK_PRICE_CHANGE="change";
        public static final String COLUMN_STOCK_PERCENTAGE_CHANGE="percentage_change";
        public static final String COLUMN_STOCK_CURRENT_PRICE="current_price";

    }




    public static final class CommoditiesEntry implements BaseColumns{
        // Tables Names
        public static final String TABLE_NAME="commodities_db";
        //Columns Names
        public static final String COLUMN_STOCK_SYMBOL="symbol";
        public static final String COLUMN_STOCK_NAME="name_english";
        public static final String COLUMN_STOCK_EPS="eps";
        public static final String COLUMN_STOCK_PRICE_CHANGE="change";
        public static final String COLUMN_STOCK_PERCENTAGE_CHANGE="percentage_change";
        public static final String COLUMN_STOCK_CURRENT_PRICE="current_price";

    }

}

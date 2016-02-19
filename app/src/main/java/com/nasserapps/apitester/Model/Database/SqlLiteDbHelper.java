package com.nasserapps.apitester.Model.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "database.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;

    // Tables Names
    public static final String TABLE_STOCKS="stocks_db";
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
    public static final String COLUMN_STOCK_52W_HIGH="52w_high";
    public static final String COLUMN_STOCK_52W_LOW="52w_low";
    public static final String COLUMN_STOCK_52W_BEST_PE_RATIO="52w_best_pe_ratio";
    public static final String COLUMN_STOCK_52W_WORST_PE_RATIO="52w_worst_pe_ratio";
    public static final String COLUMN_STOCK_52W_BEST_PBV_RATIO="52w_best_pbv_ratio";
    public static final String COLUMN_STOCK_52W_WORST_PBV_RATIO="52w_worst_pbv_ratio";
    public static final String COLUMN_STOCK_IN_INVESTMENT="in_investment";
    public static final String COLUMN_STOCK_PURCHASED_PRICE="purchased_price";
    public static final String COLUMN_STOCK_PURCHASED_QUANTITY="purchased_quantity";


    //Constructor
    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    //Copy Database to application
    public void CopyDataBaseFromAsset() throws IOException{

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

      // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Toast.makeText(ctx,"Application is ready",Toast.LENGTH_LONG).show();
        Log.d("zxc","Database copied from Assets folder");
    }

    //Mandatory methods
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
    }

    //Helper methods
    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }
    //Open database
    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }
}

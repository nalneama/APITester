package com.nasserapps.saham.Model.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FinancialDBHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "database.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;


    //Constructor
    public FinancialDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                String outFileName = "/data/user/0/com.nasserapps.apitester/databases/database.sqlite";

                InputStream myInput = null;
                myInput = ctx.getAssets().open(DATABASE_NAME);


                // Path to the just created empty db

                // if the path doesn't exist first, create it
                File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
                if (!f.exists())
                    f.mkdir();

                // Open the empty db as the output stream, false will override
                OutputStream myOutput = new FileOutputStream(outFileName,true);

                // transfer bytes from the input file to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                // Close the streams
                myOutput.flush();
                myOutput.close();
                myInput.close();

            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
    }

    //Mandatory methods
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_COMMODITY_TABLE= "CREATE TABLE " + DataContract.CommoditiesEntry.TABLE_NAME + "( " +
                DataContract.CommoditiesEntry._ID + " INTEGER PRIMARY KEY," +
                DataContract.CommoditiesEntry.COLUMN_COMMODITY_NAME + " TEXT NOT NULL," +
                DataContract.CommoditiesEntry.COLUMN_COMMODITY_SYMBOL + " TEXT UNIQUE NOT NULL," +
                DataContract.CommoditiesEntry.COLUMN_COMMODITY_CURRENT_PRICE + " REAL NOT NULL" + " );";
        db.execSQL(SQL_CREATE_COMMODITY_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //Helper methods
    public static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    //Open database
    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    //https://www.udacity.com/course/viewer#!/c-ud853/l-3621368730/m-3617349500
}

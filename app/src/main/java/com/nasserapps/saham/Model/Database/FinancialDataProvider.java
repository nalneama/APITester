package com.nasserapps.saham.Model.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class FinancialDataProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FinancialDBHelper mOpenHelper;


    static final int ALL_STOCKS = 100;
    static final int ONE_STOCK = 101;

    static final int ALL_COMMODITIES =200;


    private static final SQLiteQueryBuilder sFinancialDataQueryHelper;
    static {
        sFinancialDataQueryHelper = new SQLiteQueryBuilder();
        //sFinancialDataQueryHelper.setTables(); This used to join two tables from https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3667798749
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,DataContract.PATH_STOCK, ALL_STOCKS); //Dir to get all stocks
        matcher.addURI(authority,DataContract.PATH_STOCK + "/*", ONE_STOCK); //item to get one stock

        //Add matcher for commodity and index below here
        matcher.addURI(authority,DataContract.PATH_COMMODITY, ALL_COMMODITIES);

        return matcher;
    }



    //Should contain URIMatcher and we have to register the content provider in the manifest
    @Override
    public boolean onCreate() {

        mOpenHelper = new FinancialDBHelper(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case ALL_STOCKS:
                return DataContract.StocksEntry.CONTENT_TYPE;
            case ONE_STOCK:
                return DataContract.StocksEntry.CONTENT_ITEM_TYPE;

            case ALL_COMMODITIES:
                return DataContract.CommoditiesEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){

            //stock
            case ALL_STOCKS:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.StocksEntry.TABLE_NAME,
                        null,
                        null,null,null,null,null
                );
                break;

            //stock/*
            case ONE_STOCK:
                retCursor = null;//todo to be fixed
                break;

            case ALL_COMMODITIES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.CommoditiesEntry.TABLE_NAME,
                        null,
                        null,null,null,null,null
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ALL_STOCKS: {
                long _id = db.insert(DataContract.StocksEntry.TABLE_NAME, null, values);
//                if (_id>0){returnUri = DataContract.StocksEntry.buildStockUri();}
//                else throw new android.database.SQLException("Failed to insert row into "+uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        db.close();
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

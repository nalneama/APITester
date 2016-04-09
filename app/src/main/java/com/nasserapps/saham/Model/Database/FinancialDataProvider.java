package com.nasserapps.saham.Model.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class FinancialDataProvider extends ContentProvider {

    // UriMatcher used by the content provider to know with method to return
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FinancialDBHelper mOpenHelper;


    static final int ALL_STOCKS = 100;
    static final int ONE_STOCK = 101;

    static final int ALL_COMMODITIES =200;
    static final int ONE_COMMODITY =201;

    static final int ALL_INDEXES =300;
    static final int ONE_INDEX =301;

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

        matcher.addURI(authority,DataContract.PATH_COMMODITY, ALL_COMMODITIES);
        matcher.addURI(authority,DataContract.PATH_COMMODITY + "/*", ONE_COMMODITY);

        matcher.addURI(authority,DataContract.PATH_COMMODITY, ALL_INDEXES);
        matcher.addURI(authority,DataContract.PATH_INDEX + "/*", ONE_INDEX);

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
            case ONE_COMMODITY:
                return DataContract.CommoditiesEntry.CONTENT_ITEM_TYPE;

            case ALL_INDEXES:
                return DataContract.IndexesEntry.CONTENT_TYPE;
            case ONE_INDEX:
                return DataContract.IndexesEntry.CONTENT_ITEM_TYPE;

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
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.StocksEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            //stock/*
            case ONE_STOCK:
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.StocksEntry.TABLE_NAME,projection,"symbol = ?",selectionArgs,null,null,sortOrder);
                break;


            //commodity
            case ALL_COMMODITIES:
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.CommoditiesEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            //commodity/*
            case ONE_COMMODITY:
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.CommoditiesEntry.TABLE_NAME,projection,"symbol = ?",selectionArgs,null,null,sortOrder);
                break;


            //index
            case ALL_INDEXES:
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.IndexesEntry.TABLE_NAME, projection, selection,selectionArgs,null,null,sortOrder);
                break;
            //index/*
            case ONE_INDEX:
                retCursor = mOpenHelper.getReadableDatabase().query(DataContract.IndexesEntry.TABLE_NAME,projection,"symbol = ?",selectionArgs,null,null,sortOrder);
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
                if (_id > 0) {returnUri = DataContract.StocksEntry.buildStockUri(_id);
                } else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case ALL_COMMODITIES: {
                long _id = db.insert(DataContract.CommoditiesEntry.TABLE_NAME, null, values);
                if (_id>0){returnUri = DataContract.CommoditiesEntry.buildCommodityUri(_id);}
                else throw new android.database.SQLException("Failed to insert row into "+uri);
                break;
            }
            case ALL_INDEXES: {
                long _id = db.insert(DataContract.IndexesEntry.TABLE_NAME, null, values);
                if (_id>0){returnUri = DataContract.IndexesEntry.buildIndexUri(_id);}
                else throw new android.database.SQLException("Failed to insert row into "+uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if( null == selection) selection ="1";
        switch (match) {
            case ALL_STOCKS: {
                rowsDeleted = db.delete(DataContract.StocksEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case ALL_COMMODITIES: {
                rowsDeleted = db.delete(DataContract.CommoditiesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case ALL_INDEXES: {
                rowsDeleted = db.delete(DataContract.IndexesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ALL_STOCKS: {
                rowsUpdated = db.update(DataContract.StocksEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            }
            case ALL_COMMODITIES: {
                rowsUpdated = db.update(DataContract.CommoditiesEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            }
            case ALL_INDEXES: {
                rowsUpdated = db.update(DataContract.IndexesEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(rowsUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsUpdated;
    }

    //bulkInsert
    //If required we can get from https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3614409717
}

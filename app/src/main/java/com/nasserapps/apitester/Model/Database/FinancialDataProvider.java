package com.nasserapps.apitester.Model.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

public class FinancialDataProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SqlLiteDbHelper mOpenHelper;

    static final int ONE_STOCK = 100;
    static final int All_STOCKS = 200;

    private static final SQLiteQueryBuilder sFinancialDataQueryHelper;
    static {
        sFinancialDataQueryHelper = new SQLiteQueryBuilder();
        //sFinancialDataQueryHelper.setTables();
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,DataContract.PATH_STOCK, All_STOCKS);

        return matcher;
    }



    //Should contain URIMatcher and we have to register the content provider in the manifest
    @Override
    public boolean onCreate() {

        mOpenHelper = new SqlLiteDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case ONE_STOCK:
                return DataContract.StocksEntry.CONTENT_ITEM_TYPE;
            case All_STOCKS:
                return DataContract.StocksEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            //stock/*
            case ONE_STOCK:
                retCursor = null;//todo to be fixed
                break;
            //stock
            case All_STOCKS:
                retCursor = null;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }



    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
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

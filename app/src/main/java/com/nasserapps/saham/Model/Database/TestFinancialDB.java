package com.nasserapps.saham.Model.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestFinancialDB extends AndroidTestCase {

    public static final String LOG_TAG = TestFinancialDB.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(SqlLiteDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
        Students: Uncomment this test once you've written the code to create the Location
        table.  Note that you will have to have chosen the same column names that I did in
        my solution for this test to compile, so if you haven't yet done that, this is
        a good time to change your column names to match mine.
        Note that this only tests that the Location table has the correct columns, since we
        give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(DataContract.StocksEntry.TABLE_NAME);
        tableNameHashSet.add(DataContract.CommoditiesEntry.TABLE_NAME);

//        mContext.deleteDatabase(SqlLiteDbHelper.DATABASE_NAME);
        FinancialDBHelper financialDBHelper = new FinancialDBHelper(getContext());
        SQLiteDatabase db = financialDBHelper.getWritableDatabase();
        assertEquals(true, db.isOpen());
        assertTrue(db.getPath().equalsIgnoreCase(financialDBHelper.getDatabasePath()));
        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the stock entry
        // and commodity entry tables
        String tables = "";
        c.moveToFirst();
        do {
            tables= tables + c.getString(0)+ " ," ;
        } while( c.moveToNext() );

        assertTrue("Error: Your database was created with "+ tables.substring(0,tables.length()-2), tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + DataContract.CommoditiesEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<>();
        //locationColumnHashSet.add(DataContract.StocksEntry._ID);
        locationColumnHashSet.add(DataContract.StocksEntry.COLUMN_STOCK_NAME);
        locationColumnHashSet.add(DataContract.StocksEntry.COLUMN_STOCK_ASK);
        locationColumnHashSet.add(DataContract.StocksEntry.COLUMN_STOCK_BID);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required stock entry columns",
                locationColumnHashSet.isEmpty());
        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can uncomment out the "createNorthPoleLocationValues" function.  You can
        also make use of the ValidateCurrentRecord function from within TestUtilities.
    */
    public void testLocationTable() {
        // First step: Get reference to writable database
        SQLiteDatabase db = new SqlLiteDbHelper(getContext()).getWritableDatabase();
        SqlLiteDbHelper.addTables(db);
        // Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.CommoditiesEntry.COLUMN_COMMODITY_NAME,"Oil");
        contentValues.put(DataContract.CommoditiesEntry.COLUMN_COMMODITY_CURRENT_PRICE,30);
        contentValues.put(DataContract.CommoditiesEntry.COLUMN_COMMODITY_SYMBOL,"OIL");
        // Insert ContentValues into database and get a row ID back
        long commodityRowID;
        commodityRowID = db.insert(DataContract.CommoditiesEntry.TABLE_NAME ,null,contentValues);
        // Query the database and receive a Cursor back
        assertTrue(commodityRowID != -1);
        // Move the cursor to a valid database row
        Cursor cursor = db.query(
                DataContract.CommoditiesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue("Error: No records returned", cursor.moveToFirst());
        // Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)

        // Finally, close the cursor and database

    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testWeatherTable() {
        // First insert the location, and then use the locationRowId to insert
        // the weather. Make sure to cover as many failure cases as you can.

        // Instead of rewriting all of the code we've already written in testLocationTable
        // we can move this code to insertLocation and then call insertLocation from both
        // tests. Why move it? We need the code to return the ID of the inserted location
        // and our testLocationTable can only return void because it's a test.

        // First step: Get reference to writable database

        // Create ContentValues of what you want to insert
        // (you can use the createWeatherValues TestUtilities function if you wish)

        // Insert ContentValues into database and get a row ID back

        // Query the database and receive a Cursor back

        // Move the cursor to a valid database row

        // Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)

        // Finally, close the cursor and database
    }


    /*
        Students: This is a helper method for the testWeatherTable quiz. You can move your
        code from testLocationTable to here so that you can call this code from both
        testWeatherTable and testLocationTable.
     */
    public long insertLocation() {
        return -1L;
    }
}
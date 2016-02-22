package com.nasserapps.apitester.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nasserapps.apitester.MarketTime;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

import java.util.ArrayList;

public class StocksCompareActivity extends AppCompatActivity {

    private User mUser;
    private TextView mStockNameViewL;
    private TextView mStockPriceViewL;
    private TextView mStockPercentageViewL;
    private TextView mStockTodayViewL;
    private TextView mStock52WViewL;

    private TextView mStockPriceViewR;
    private TextView mStockPercentageViewR;
    private TextView mStockTodayViewR;
    private TextView mStock52WViewR;
    private Ticker mStockL;
    private Ticker mStockR;
    private TextView mStockTodayTitlesViewL;
    private Spinner mStockNameChooserL;

    private TextView mStockTodayTitlesViewR;
    private Spinner mStockNameChooserR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_compare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser = User.getUser(this);
        Intent i = getIntent();

        mStockNameViewL = (TextView) findViewById(R.id.stockNameL);
        mStockNameChooserR = (Spinner) findViewById(R.id.stockNameR);

        // Stock Price, Percentage and Change
        mStockPriceViewL = (TextView) findViewById(R.id.stockPriceL);
        mStockPriceViewR = (TextView) findViewById(R.id.stockPriceR);

        mStockPercentageViewL = (TextView)findViewById(R.id.percentageL);
        mStockPercentageViewR = (TextView)findViewById(R.id.percentageR);

        mStockTodayTitlesViewL = (TextView)findViewById(R.id.todayTitles);
        mStockTodayTitlesViewR = (TextView)findViewById(R.id.todayTitlesR);


        mStockTodayViewL = (TextView) findViewById(R.id.todayValuesL);
        mStockTodayViewR = (TextView) findViewById(R.id.todayValuesR);

        mStock52WViewL = (TextView) findViewById(R.id.fiftytwoWeeksValuesL);
        mStock52WViewR = (TextView) findViewById(R.id.fiftytwoWeeksValuesR);


        mStockL = Tools.getStockFromList(i.getStringExtra("LSymbol"), mUser.getAllStocks());
        mStockR= Tools.getStockFromList(i.getStringExtra("RSymbol"), mUser.getAllStocks());

        mStockNameViewL.setText(mStockL.getName());
        // Create an ArrayAdapter using the string array and a default spinner layout
        //TODO and put all stocks
        ArrayList<String> names = new ArrayList<>();
        for(Ticker ticker:mUser.getAllStocks()){
            names.add(ticker.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.my_spinner,names);
        //ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStockNameChooserR.setAdapter(adapter);
        //mStockNameChooserR.setText(mStockR.getName());
        int stockPositionR = names.indexOf(mStockR.getName());
        mStockNameChooserR.setSelection(stockPositionR,false);
        mStockNameChooserR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStockR=mUser.getAllStocks().get(position);
                setDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setDisplay();


    }

    private void setDisplay() {
        //Stock Name

        // Stock Price, Percentage and Change
        mStockPriceViewL.setText(mStockL.getPrice() + "");
        mStockPriceViewL.setTextColor(getResources().getColor(mStockL.getPriceColor()));
        mStockPriceViewR.setText(mStockR.getPrice() + "");
        mStockPriceViewR.setTextColor(getResources().getColor(mStockR.getPriceColor()));

        mStockPercentageViewL.setText(mStockL.getChange() + " (" + mStockL.getPercentage() + ")");
        mStockPercentageViewL.setTextColor(getResources().getColor(mStockL.getPriceColor()));
        mStockPercentageViewR.setText(mStockR.getChange()+" (" + mStockR.getPercentage() + ")");
        mStockPercentageViewR.setTextColor(getResources().getColor(mStockR.getPriceColor()));


        //Today's and 52W Values
        double openOrCloseL = mStockL.getPrice();
        double openOrCloseR = mStockR.getPrice();
        String openOrCloseString = "Close";
        MarketTime marketTime = new MarketTime();

        if(marketTime.isInTheExchangePeriod()){
            openOrCloseL = mStockL.getOpenPrice();
            openOrCloseR = mStockR.getOpenPrice();
            openOrCloseString = "Open";
        }

        mStockTodayTitlesViewL.setText(String.format("%s:%nDay High:%nDay Low:%nVolume:%nPE Ratio:%nP-Book Value:",openOrCloseString));
        mStockTodayTitlesViewR.setText(String.format("%s:%nDay High:%nDay Low:%nVolume:%nPE Ratio:%nP-Book Value:",openOrCloseString));


        mStockTodayViewL.setText(String.format("%.2f%n%.2f%n%.2f%n%,d%n%.2f%n%.2f",
                openOrCloseL,
                mStockL.getDayHigh(),
                mStockL.getDayLow(),
                mStockL.getVolume(),
                mStockL.getPERatio(),
                mStockL.getPBV()));
        mStockTodayViewR.setText(String.format("%.2f%n%.2f%n%.2f%n%,d%n%.2f%n%.2f",
                openOrCloseR,
                mStockR.getDayHigh(),
                mStockR.getDayLow(),
                mStockR.getVolume(),
                mStockR.getPERatio(),
                mStockR.getPBV()));

        mStock52WViewL.setText(String.format("%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f",
                mStockL.getM52WHigh(),
                mStockL.getM52WLow(),
                mStockL.getBestPE(),
                mStockL.getWorstPE(),
                mStockL.getBestPBV(),
                mStockL.getWorstPBV()));
        mStock52WViewR.setText(String.format("%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f",
                mStockR.getM52WHigh(),
                mStockR.getM52WLow(),
                mStockR.getBestPE(),
                mStockR.getWorstPE(),
                mStockR.getBestPBV(),
                mStockR.getWorstPBV()));
    }


}

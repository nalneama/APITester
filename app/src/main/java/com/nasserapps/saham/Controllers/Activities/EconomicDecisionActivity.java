package com.nasserapps.saham.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nasserapps.saham.Model.MarketTime;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.Tools;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.R;

import java.util.ArrayList;

public class EconomicDecisionActivity extends AppCompatActivity {

    private User mUser;
    private TextView mStockPriceViewL;
    private TextView mStockPercentageViewL;
    private TextView mStockTodayViewL;
    private TextView mStock52WViewL;

    private TextView mStockPriceViewR;
    private TextView mStockPercentageViewR;
    private TextView mStockTodayViewR;
    private TextView mStock52WViewR;
    private Stock mStockL;
    private Stock mStockR;
    private TextView mStockTodayTitlesViewL;
    private Spinner mStockNameChooserL;

    private TextView mStockTodayTitlesViewR;
    private Spinner mStockNameChooserR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_decision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser = User.getUser(this);

        mStockNameChooserL = (Spinner) findViewById(R.id.stockNameL);
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


        mStockL = Tools.getStockFromList("MARK", mUser.getAllStocks());
        mStockR= Tools.getStockFromList("QIBK", mUser.getAllStocks());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<String> names = new ArrayList<>();
        for(Stock stock :mUser.getAllStocks()){
            names.add(stock.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_comparison,names);
        //ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStockNameChooserR.setAdapter(adapter);
        mStockNameChooserL.setAdapter(adapter);
        //mStockNameChooserR.setText(mStockR.getName());
        int stockPositionR = names.indexOf(mStockR.getName());
        int stockPositionL = names.indexOf(mStockL.getName());

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
        mStockNameChooserL.setSelection(stockPositionL,false);
        mStockNameChooserL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStockL=mUser.getAllStocks().get(position);
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
        mStockPriceViewL.setTextColor(Tools.getTextColor(this, mStockL.getChange()));
        mStockPriceViewR.setText(mStockR.getPrice() + "");
        mStockPriceViewR.setTextColor(Tools.getTextColor(this, mStockR.getChange()));

        mStockPercentageViewL.setText(mStockL.getChange() + " (" + mStockL.getPercentage() + ")");
        mStockPercentageViewL.setTextColor(Tools.getTextColor(this, mStockL.getChange()));
        mStockPercentageViewR.setText(mStockR.getChange()+" (" + mStockR.getPercentage() + ")");
        mStockPercentageViewR.setTextColor(Tools.getTextColor(this, mStockR.getChange()));


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

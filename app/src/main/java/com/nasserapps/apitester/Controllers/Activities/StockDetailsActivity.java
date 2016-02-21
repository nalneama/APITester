package com.nasserapps.apitester.Controllers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nasserapps.apitester.MarketTime;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.Tools;

public class StockDetailsActivity extends AppCompatActivity {

    private User mUser;
    private TextView mStockNameView;
    private TextView mStockPriceView;
    private TextView mStockChangeView;
    private TextView mStockPercentageView;
    private TextView mStockSummaryView;
    private TextView mStockTodayView;
    private TextView mStock52WView;
    private Ticker mStock;
    private TextView mStockTodayTitlesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser = User.getUser(this);
        Intent i = getIntent();

        mStock= Tools.getStockFromList(i.getStringExtra("Symbol"),mUser.getWatchList());
        //Toast.makeText(this, mStock.getSymbol()+" price is : "+mStock.getPrice(), Toast.LENGTH_SHORT).show();

        //Stock Name
        mStockNameView = (TextView) findViewById(R.id.stockName);
        mStockNameView.setText(mStock.getName());


        // Stock Price, Percentage and Change
        mStockPriceView = (TextView) findViewById(R.id.stockPrice);
        mStockPriceView.setText(mStock.getPrice() + "");
        mStockPriceView.setTextColor(getResources().getColor(mStock.getPriceColor()));

        mStockChangeView = (TextView) findViewById(R.id.change);
        mStockChangeView.setText(mStock.getChange() + "");
        mStockChangeView.setTextColor(getResources().getColor(mStock.getPriceColor()));

        mStockPercentageView = (TextView)findViewById(R.id.percentage);
        mStockPercentageView.setText(" (" + mStock.getPercentage() + ")");
        mStockPercentageView.setTextColor(getResources().getColor(mStock.getPriceColor()));



        //Stock Investment Summary
        String lossOrProfit = "loss";
        if(mStock.getInvestmentProfit()>=0){lossOrProfit="profit";}

        mStockSummaryView=(TextView)findViewById(R.id.stockSummary);
        if(mStock.isInInvestments()) {
            mStockSummaryView.setText(String.format("Change from purchase%nprice is %.2f QR (%s)%nA total %s of%n%,.0f QR",
                    mStock.getChangeFromPurchasedPrice(),
                    mStock.getPercentageChangeFromPurchasedPrice(),
                    lossOrProfit,
                    mStock.getInvestmentProfit()));
        }
        else{
            mStockSummaryView.setText(String.format("You don't have investments%nin %s.",mStock.getName()));
        }



        //Today's and 52W Values
        double openOrClose = mStock.getPrice();
        String openOrCloseString = "Close";
        MarketTime marketTime = new MarketTime();

        if(marketTime.isInTheExchangePeriod()){
            openOrClose = mStock.getOpenPrice();
            openOrCloseString = "Open";
        }

        mStockTodayTitlesView = (TextView)findViewById(R.id.todayTitles);
        mStockTodayTitlesView.setText(String.format("%s:%nDay High:%nDay Low:%nVolume:%nPE Ratio:%nP-Book Value:",openOrCloseString));


        mStockTodayView = (TextView) findViewById(R.id.todayValues);
        mStockTodayView.setText(String.format("%.2f%n%.2f%n%.2f%n%,d%n%.2f%n%.2f",
                openOrClose,
                mStock.getDayHigh(),
                mStock.getDayLow(),
                mStock.getVolume(),
                mStock.getPERatio(),
                mStock.getPBV()));

        mStock52WView = (TextView) findViewById(R.id.fiftytwoWeeksValues);
        mStock52WView.setText(String.format("%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f",
                mStock.getM52WHigh(),
                mStock.getM52WLow(),
                mStock.getBestPE(),
                mStock.getWorstPE(),
                mStock.getBestPBV(),
                mStock.getWorstPBV()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stocks_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.compare) {

            String[] names = new String[mUser.getAllStocks().size()];
            for(int i=0; i<mUser.getAllStocks().size();i++){
                names[i]=mUser.getAllStocks().get(i).getName();
            }

            new AlertDialog.Builder(this)
                    .setTitle("Compare to:")
                    .setItems(names , new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            Intent i = new Intent(getBaseContext(), StocksCompareActivity.class);
                            i.putExtra("LSymbol",mStock.getSymbol());
                            i.putExtra("RSymbol",mUser.getAllStocks().get(which).getSymbol());
                            startActivity(i);
                        }
                    }).create().show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

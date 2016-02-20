package com.nasserapps.apitester.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nasserapps.apitester.Controllers.InProgress.InvestmentListActivity;
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

        mStockNameView = (TextView) findViewById(R.id.stockName);
        mStockNameView.setText(mStock.getName());

        mStockPriceView = (TextView) findViewById(R.id.stockPrice);
        mStockPriceView.setText(mStock.getPrice() + "");
        mStockPriceView.setTextColor(getResources().getColor(mStock.getPriceColor()));

        mStockChangeView = (TextView) findViewById(R.id.change);
        mStockChangeView.setText(mStock.getChange() + "");
        mStockChangeView.setTextColor(getResources().getColor(mStock.getPriceColor()));

        mStockPercentageView = (TextView)findViewById(R.id.percentage);
        mStockPercentageView.setText("("+mStock.getPercentage() + ")");
        mStockPercentageView.setTextColor(getResources().getColor(mStock.getPriceColor()));

        //TODO fix the numbers and change the statement based on isInvestment and profit or loss
        mStockSummaryView=(TextView)findViewById(R.id.stockSummary);
        mStockSummaryView.setText(String.format("Change from purchase%nprice is %.2f QR (%s)%nA total loss of%n%,.0f QR", mStock.getChange(), mStock.getPercentage(), mStock.getQuantity() * mStock.getPurchasedPrice()));

        //TODO change from open to close based on time of access
        mStockTodayView = (TextView) findViewById(R.id.todayValues);
        mStockTodayView.setText(String.format("%.2f%n%.2f%n%.2f%n%,d%n%.2f%n%.2f",mStock.getOpenPrice(),mStock.getDayHigh(),mStock.getDayLow(),mStock.getVolume(),mStock.getPERatio(),mStock.getPBV()));

        mStock52WView = (TextView) findViewById(R.id.fiftytwoWeeksValues);
        mStock52WView.setText(String.format("%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f",mStock.getM52WHigh(),mStock.getM52WLow(),mStock.getBestPE(),mStock.getWorstPE(),mStock.getBestPBV(),mStock.getWorstPBV()));

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
            Intent i = new Intent(this, InvestmentListActivity.class);
            i.putExtra("Symbol",mStock.getSymbol());
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

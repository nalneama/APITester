package com.nasserapps.saham.Controllers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nasserapps.saham.Model.FinancialStatement;
import com.nasserapps.saham.Model.MarketTime;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.Tools;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.R;

public class StockDetailsActivity extends AppCompatActivity {

    private User mUser;
    private TextView mStockNameView;
    private TextView mStockPriceView;
    private TextView mStockChangeView;
    private TextView mStockPercentageView;
    private TextView mStockSummaryView;
    private TextView mStockTodayView;
    private TextView mStock52WView;
    private Stock mStock;
    private TextView mStockTodayTitlesView;
    private TextView mIncomeStatmentValues;
    private TextView mIncomeStatmentPercentage;
    private TextView mIncomeStatmentRaios;

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
        //Toast.makeText(this, mStock.getSymbol()+" price is : "+mStock.getValue(), Toast.LENGTH_SHORT).show();

        //Stock Name
        mStockNameView = (TextView) findViewById(R.id.stockName);
        mStockNameView.setText(mStock.getName());


        // Stock Price, Percentage and Change
        mStockPriceView = (TextView) findViewById(R.id.stockPrice);
        mStockPriceView.setText(mStock.getPrice() + "");
        mStockPriceView.setTextColor(Tools.getTextColor(this,mStock.getChange()));

        mStockChangeView = (TextView) findViewById(R.id.change);
        mStockChangeView.setText(mStock.getChange() + "");
        mStockChangeView.setTextColor(Tools.getTextColor(this, mStock.getChange()));

        mStockPercentageView = (TextView)findViewById(R.id.percentage);
        mStockPercentageView.setText(" (" + mStock.getPercentage() + ")");
        mStockPercentageView.setTextColor(Tools.getTextColor(this, mStock.getChange()));



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






        //Income Statement
        FinancialStatement financialStatement = new FinancialStatement(4508326-222000-104936-4033,0,-1206383-951511+75425,-75425,21208,0,-10331,-76016,236293);
        mIncomeStatmentValues = (TextView) findViewById(R.id.statementValues); //12 Values
        mIncomeStatmentValues.setText(String.format("%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f%n%.2f",
                financialStatement.getRevenues(),
                financialStatement.getCOGS(),
                financialStatement.getGrossProfit(),
                financialStatement.getSGA(),
                financialStatement.getDA(),
                financialStatement.getOperatingProfit(),
                financialStatement.getSpecialItems(),
                financialStatement.getEBIT(),
                financialStatement.getInterest(),
                financialStatement.getEBT(),
                financialStatement.getTax(),
                financialStatement.getEAT()));

        mIncomeStatmentPercentage = (TextView) findViewById(R.id.statementPercentage); //6 Values
        mIncomeStatmentPercentage.setText(String.format("%.2f%n%n%.2f%n%n%n%.2f%n%n%.2f%n%n%.2f%n%n%.2f",
                financialStatement.getRevenues()/financialStatement.getRevenues()*100,
                financialStatement.getGrossProfit()/financialStatement.getRevenues()*100,
                financialStatement.getOperatingProfit()/financialStatement.getRevenues()*100,
                financialStatement.getEBIT()/financialStatement.getRevenues()*100,
                financialStatement.getEBT()/financialStatement.getRevenues()*100,
                financialStatement.getEAT()/financialStatement.getRevenues()*100));

        mIncomeStatmentRaios = (TextView) findViewById(R.id.statementRatios); //6 Values
        mIncomeStatmentRaios.setText(String.format("%.2f%n%n%.2f%n%n%n%.2f%n%n%.2f%n%n%.2f%n%n%.2f",
                mStock.getPrice()/financialStatement.getRevenues(),
                mStock.getPrice()/financialStatement.getGrossProfit(),
                mStock.getPrice()/financialStatement.getOperatingProfit(),
                mStock.getPrice()/financialStatement.getEBIT(),
                mStock.getPrice()/financialStatement.getEBT(),
                mStock.getPrice()/financialStatement.getEAT()));

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

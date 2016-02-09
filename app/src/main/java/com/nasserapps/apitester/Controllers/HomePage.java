package com.nasserapps.apitester.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nasserapps.apitester.AI.CustomNotificationStatement.Checklist;
import com.nasserapps.apitester.AI.CustomNotificationStatement.ExpressionParser;
import com.nasserapps.apitester.AI.CustomNotificationStatement.Rule;
import com.nasserapps.apitester.Controllers.Adapters.StockAdapter;
import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.Investment;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DataSource mDataSource;
    List<Ticker> mStockWatchList;
    List<Ticker> mIndexWatchList;
    Wallet mWallet;

    private RecyclerView mStockWatchListView;
    private RecyclerView mIndexWatchListView;
    private TextView mCapitalView;
    private TextView mCapitalChangeView;
    private TextView mCapitalProfitView;
    private ImageView mInvestmentArrow;
    private TextView mExplorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        //1.0 Linking the views to variables in code
        //1.1 The ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //1.2 The Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //1.3 The StockWatchList Card
        mStockWatchListView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mStockWatchListView.setHasFixedSize(true);
        mStockWatchListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mStockWatchListView.setItemAnimator(new DefaultItemAnimator());
        //1.4 The IndexWatchList Card
        mIndexWatchListView = (RecyclerView) findViewById(R.id.my_index_view);
        mIndexWatchListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        //1.5 The Wallet Card
        mCapitalProfitView = (TextView)findViewById(R.id.capitalProfit);
        mCapitalView = (TextView) findViewById(R.id.capitalInvested);
        mCapitalChangeView = (TextView)findViewById(R.id.percentageCIChange);
        mInvestmentArrow = (ImageView)findViewById(R.id.InvestmentArrow);
        mExplorButton = (TextView)findViewById(R.id.exploreButton);
        mExplorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), WalletAnalysisActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //2.0 Initialize variables for display
        //2.1 The DataSource (Object responsible to work with the application memory)
        mDataSource = new DataSource(getApplicationContext());
        //2.2 The Wallet
        mWallet = new Wallet();
        //2.2a Checking if data is available in memory: if yes, then get wallet data from memory
        if(mDataSource.isStoredDataAvailable()) {
            mWallet = mDataSource.getWallet();
            mStockWatchList =mWallet.getWatchList();
        }
        //2.2b Else,this is first time opening of app, set the wallet to initial data
        else{
           // mWallet.setInitialWatchList();
            mStockWatchList = new ArrayList<>();
        String[] companies = getApplicationContext().getResources().getStringArray(R.array.Companies_API_Codes);
        for (String code:companies){
            mStockWatchList.add(new Ticker(code));
        }
            mWallet.setInitialWatchList(mStockWatchList);
        }
        //2.3 The WatchList
        //mStockWatchList =mWallet.getWatchList(); Moved inside the if statemnet
        //Log.e("zxc", "mStockWatchList=mWallet.getWatchList():"+ mWallet.getWatchList().get(0).getAPICode());



        //3.0 Set the Display with Initial Data
        //TODO set the Wallet Card and Watchlist
        setDisplay();


        //4.0 Get the updated data and set the display with the updated data
        //getUpdatedData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.calculator) {
            Intent i = new Intent(this, CalculatorActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.detailed_view) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.refresh_data) {
            Snackbar.make(this.mStockWatchListView, "Refreshing Data", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            getUpdatedData();
            return true;
        }

        if (id == R.id.add_stocks) {
            Intent i = new Intent(this, EditStockListActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.add_rules) {
            Intent i = new Intent(this, RulesActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.evaluate_stocks) {
            //TODO Filter stocks by checklists
            ArrayList<Rule> rules = new ArrayList<>();
            rules.add(new ExpressionParser().getRule("PE Ratio", "<", "15.0"));
            rules.add(new ExpressionParser().getRule("PE Ratio", ">","10.0"));
            Checklist checklist = new Checklist(rules);
            mStockWatchList=checklist.getPassingStocks(mWallet);
            updateDisplay();
            return true;
        }

        if (id == R.id.sorting_options) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(HomePage.this);
            dialog.setSingleChoiceItems(new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price" }, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            mStockWatchList=mWallet.sort(mStockWatchList, "A-Z");
                            break;
                        case 1:
                            mStockWatchList=mWallet.sort(mStockWatchList,"Book Value");
                            break;
                        case 2:
                            mStockWatchList=mWallet.sort(mStockWatchList,"Gain");
                            break;
                        case 3:
                            mStockWatchList=mWallet.sort(mStockWatchList,"PE Ratio");
                            break;
                        case 4:
                            mStockWatchList= mWallet.sort(mStockWatchList,"Price");
                            break;}
                    dialog.dismiss();
                    updateDisplay();
                }
            });
            dialog.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUpdatedData() {

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mDataSource.getAPIURL(mWallet.getAPIKey()))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        final String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            //TODO add the index list
                            mWallet.updateWatchList(jsonData);
                            mStockWatchList = mWallet.getWatchList();
                            //mStockWatchList = mDataSource.loadStockDataFromResponse(jsonData);
                            mIndexWatchList = mDataSource.loadIndexDataFromResponse(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    mDataSource.saveWallet(mWallet);
                                    //Log.e("zxc", "mWallet. String is: " + mDataSource.getWallet().getAPIKey() + "");
                                }
                            });
                        } else {
                            //TODO alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e("error", "IO Exception caught", e);
                    } catch (JSONException e) {
                        Log.e("error", "JSON Exception caught", e);
                    }
                }
            });
        }

        else{

        }
    }

    public void updateDisplay() {
        if(mDataSource.isStoredDataAvailable()) {
            setDisplay();
            //mIndexWatchListView.setAdapter(new IndexAdapter(mIndexWatchList));
        }
        else {
            mStockWatchListView.swapAdapter(new StockAdapter(getApplicationContext(),mStockWatchList), false);
            //mIndexWatchListView.swapAdapter(new IndexAdapter(mIndexWatchList), false);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable =false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    public void setDisplay(){
        mStockWatchListView.setAdapter(new StockAdapter(getApplicationContext(),mStockWatchList));
        //mIndexWatchListView.setAdapter(new IndexAdapter(mIndexWatchList));
        ArrayList<Investment> investmentList = new ArrayList<Investment>();
        investmentList = (ArrayList) mWallet.getInvestmentList();
        if(investmentList.size()>0) {
            //http://developer.android.com/reference/java/util/Formatter.html
            mCapitalView.setText(String.format(getString(R.string.Format_Capital),mWallet.getCurrentWorth()));
            String profitorLoss = getString(R.string.Profit);
            int color=R.color.green;
            int arrow = R.drawable.green_arrow;
            if (mWallet.getProfit()<0){
                profitorLoss=getString(R.string.Loss);
                color=R.color.red;
                arrow = R.drawable.red_arrow;
            }


            mCapitalProfitView.setText(profitorLoss+":     "+String.format(getString(R.string.Format_Capital), mWallet.getProfit()));
            mCapitalChangeView.setText(String.format("%.2f%%", mWallet.getPercentageChange()));
            mCapitalChangeView.setTextColor(getResources().getColor(color));
            mCapitalView.setTextColor(getResources().getColor(color));
            mInvestmentArrow.setImageDrawable(getResources().getDrawable(arrow));

        }
        else{
            mCapitalView.setText("-------");
            mCapitalProfitView.setText("Profit:     "+"-----");
            mCapitalChangeView.setText("---");
            mInvestmentArrow.setVisibility(View.INVISIBLE);
        }
        ImageView walletSetUpButton = (ImageView)findViewById(R.id.walletSettings);
        walletSetUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditInvestmentListActivity.class);
                startActivity(i);
            }
        });
    }

}

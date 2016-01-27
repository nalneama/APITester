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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.Comparator;
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




        //2.0 Initialize variables for display
        //2.1 The DataSource (Object responsible to work with the application memory)
        mDataSource = new DataSource(getApplicationContext());
        //2.2 The Wallet
        mWallet = new Wallet();
        //2.2a Checking if data is available in memory: if yes, then get wallet data from memory
            if(mDataSource.isStoredDataAvailable()) {
                    mWallet = mDataSource.getWallet();
            }
        //2.2b Else,this is first time opening of app, set the wallet to initial data
            else{
                mWallet.setInitialWatchList();
            }
        //2.3 The WatchList
        mStockWatchList =mWallet.getWatchList();
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

        if (id == R.id.sorting_options) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(HomePage.this);
            dialog.setSingleChoiceItems(new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price" }, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Collections.sort(mStockWatchList, new Comparator<Ticker>() {
                                @Override
                                public int compare(Ticker stock1, Ticker stock2) {
                                    return stock1.getSymbol().compareTo(stock2.getName());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;
                        case 1:
                            Collections.sort(mStockWatchList, new Comparator<Ticker>() {
                                @Override
                                public int compare(Ticker stock1, Ticker stock2) {
                                    return Double.compare(stock1.getPBV(), stock2.getPBV());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;

                        case 2:
                            Collections.sort(mStockWatchList, new Comparator<Ticker>() {
                                @Override
                                public int compare(Ticker stock1, Ticker stock2) {
                                    return Double.compare(Double.parseDouble(stock2.getPercentage().substring(0,stock2.getPercentage().length()-1)), Double.parseDouble(stock1.getPercentage().substring(0,stock1.getPercentage().length()-1)));
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;

                        case 3:
                            Collections.sort(mStockWatchList, new Comparator<Ticker>() {
                                @Override
                                public int compare(Ticker stock1, Ticker stock2) {
                                    return Double.compare(stock1.getPERatio(), stock2.getPERatio());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;
                        case 4:
                            Collections.sort(mStockWatchList, new Comparator<Ticker>() {
                                @Override
                                public int compare(Ticker stock1, Ticker stock2) {
                                    return Double.compare(stock2.getPrice(), stock1.getPrice());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;
                    }
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
                            //TODO get the Data from internet based on the current API of the mWatchlist followed by the index list;based on received JSON Object, convert string to list of Stocks
                            //TODO update the display
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
                            //alertUserAboutError();
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
            mStockWatchListView.setAdapter(new StockAdapter(mStockWatchList));
            //mIndexWatchListView.setAdapter(new IndexAdapter(mIndexWatchList));
            //mCapitalView.setText(mWallet.getCapital() + "");
        }
        else {
            mStockWatchListView.swapAdapter(new StockAdapter(mStockWatchList), false);
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
        mStockWatchListView.setAdapter(new StockAdapter(mStockWatchList));
        //mIndexWatchListView.setAdapter(new IndexAdapter(mIndexWatchList));
        ArrayList<Investment> investmentList = new ArrayList<Investment>();
        investmentList.add(new Investment(mStockWatchList.get(0), 20, 300));
        investmentList.add(new Investment(mStockWatchList.get(1), 60, 1000));
        mWallet.setInvestmentList(investmentList);

        mCapitalView = (TextView)findViewById(R.id.capitalInvested);
        mCapitalView.setText(mWallet.getCurrentWorth() + "");

        ImageView walletSetUpButton = (ImageView)findViewById(R.id.walletSettings);
        walletSetUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditInvestmentListActivity.class);
                startActivity(i);
            }
        });

        //mCapitalChangeView.setText(mWallet.getReturn() + "");
        //mCapitalProfitView.setText("Profit:    "+mWallet.getProfit()+"QR");


    }




    private class StockHolder extends RecyclerView.ViewHolder{

        //View Declaration
        private TextView mStockNameView;
        private TextView mStockSymbol;
        private TextView mStockPrice;
        private TextView mStockPBValue;
        private TextView mStockPERatio;
        private TextView mStockChange;
        private TextView mStockPercentage;

        private Ticker mStock;

        public StockHolder(View itemView) {
            super(itemView);
            //Find view by ID
            mStockNameView = (TextView)itemView.findViewById(R.id.stockName);
            mStockSymbol = (TextView) itemView.findViewById(R.id.stockSymbol);
            mStockPrice = (TextView) itemView.findViewById(R.id.stockPrice);
            mStockPBValue = (TextView) itemView.findViewById(R.id.bpvalue);
            mStockPERatio = (TextView) itemView.findViewById(R.id.peratio);
            mStockChange = (TextView) itemView.findViewById(R.id.change);
            mStockPercentage = (TextView) itemView.findViewById(R.id.percentage);
        }

        public void bindStock(Ticker stock){
            mStock=stock;
            mStockNameView.setText(mStock.getName());
            mStockSymbol.setText(mStock.getSymbol());
            mStockPrice.setText(mStock.getPrice()+"");
            mStockPBValue.setText("BP: " + mStock.getPBV());
            mStockPERatio.setText("PE: " + mStock.getPERatio());

            mStockPrice.setTextColor(getResources().getColor(mStock.getPriceColor()));



            mStockChange.setText("" + mStock.getChange());
            mStockPercentage.setText("(" + mStock.getPercentage() + ")");

            mStockChange.setTextColor(getResources().getColor(mStock.getPriceColor()));
            mStockPercentage.setTextColor(getResources().getColor(mStock.getPriceColor()));
        }
    }

    private class StockAdapter extends RecyclerView.Adapter<StockHolder>{

        private List<Ticker> mStockList;

        public StockAdapter(List<Ticker> stockList) {
            mStockList = stockList;
        }

        @Override
        public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_stocks,parent,false);
            return new StockHolder(view);
        }

        @Override
        public void onBindViewHolder(StockHolder holder, int position) {
            Ticker stock = mStockList.get(position);
            holder.bindStock(stock);
        }

        @Override
        public int getItemCount() {
            return mStockList.size();
        }
    }


    private class IndexAdapter extends RecyclerView.Adapter<IndexHolder> {

        private List<Ticker> mIndexList;

        public IndexAdapter(List<Ticker> indexes) {
            mIndexList=indexes;
        }

        @Override
        public IndexHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_indexs,parent,false);
            return new IndexHolder(view);
        }

        @Override
        public void onBindViewHolder(IndexHolder holder, int position) {
            Ticker index= mIndexList.get(position);
            holder.bindIndex(index);
        }

        @Override
        public int getItemCount() {
            return mIndexList.size();
        }
    }

    private class IndexHolder extends RecyclerView.ViewHolder{
        //View Declaration
        private TextView mIndexSymbol;
        private TextView mIndexPrice;
        private TextView mIndexPercentage;
        private TextView mIndexChange;

        private Ticker mIndex;

        public IndexHolder(View itemView) {
            super(itemView);
            //Find view by ID

            mIndexSymbol = (TextView) itemView.findViewById(R.id.indexSymbol);
            mIndexPrice = (TextView) itemView.findViewById(R.id.indexPrice);
            mIndexPercentage = (TextView) itemView.findViewById(R.id.indexPercentage);
            mIndexChange = (TextView) itemView.findViewById(R.id.indexChange);
        }

        public void bindIndex(Ticker index){
            mIndex=index;
            mIndexSymbol.setText(mIndex.getSymbol());
            mIndexPrice.setText(mIndex.getPrice()+"");

            mIndexPrice.setTextColor(getResources().getColor(mIndex.getPriceColor()));


            mIndexChange.setText("" + mIndex.getChange());
            mIndexPercentage.setText("(" + mIndex.getPercentage() + ")");

            mIndexChange.setTextColor(getResources().getColor(mIndex.getPriceColor()));
            mIndexPercentage.setTextColor(getResources().getColor(mIndex.getPriceColor()));
        }
    }
}

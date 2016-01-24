package com.nasserapps.apitester.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;

import com.nasserapps.apitester.Model.Stock;
import com.nasserapps.apitester.Model.StocksDataSource;
import com.nasserapps.apitester.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    StocksDataSource mStocksDataSource;
    List<Stock> mStocks;
    List<Stock> mIndexes;

    private RecyclerView recyclerView;
    private RecyclerView indexView;
    //TextView indexPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //Setting the View
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        indexView = (RecyclerView) findViewById(R.id.my_index_view);
        indexView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        //indexPrice = (TextView)findViewById(R.id.indexPrice);


        //Initiating DataSource Object
        mStocksDataSource = new StocksDataSource(getApplicationContext());

        //Checking if data is available in memory: if yes, then setup the display
        if(mStocksDataSource.isStoredDataAvailable()) {
            try {

                mStocks = mStocksDataSource.loadStocksDataFromMemory();
                mIndexes = mStocksDataSource.loadIndexesDataFromMemory();
                setDisplay();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //TODO remove the comments below to have live data (it should be in on resume.
            //getStock();
        }

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

        //noinspection SimplifiableIfStatement

        if (id == R.id.detailed_view) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.refresh_data) {
            //Snackbar.make(, "Refreshing Data", Snackbar.LENGTH_LONG)
                    //.setAction("Action", null).show();
            getStock();
            return true;
        }
        if (id == R.id.sorting_options) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(HomePage.this);
            dialog.setSingleChoiceItems(new String[]{"A-Z","Book Value", "Gain","PE Ratio","Price" }, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Collections.sort(mStocks, new Comparator<Stock>() {
                                @Override
                                public int compare(Stock stock1, Stock stock2) {
                                    return stock1.getSymbol().compareTo(stock2.getName());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;
                        case 1:
                            Collections.sort(mStocks, new Comparator<Stock>() {
                                @Override
                                public int compare(Stock stock1, Stock stock2) {
                                    return Double.compare(stock1.getPBV(), stock2.getPBV());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;

                        case 2:
                            Collections.sort(mStocks, new Comparator<Stock>() {
                                @Override
                                public int compare(Stock stock1, Stock stock2) {
                                    return Double.compare(Double.parseDouble(stock2.getPercentage().substring(0,stock2.getPercentage().length()-1)), Double.parseDouble(stock1.getPercentage().substring(0,stock1.getPercentage().length()-1)));
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;

                        case 3:
                            Collections.sort(mStocks, new Comparator<Stock>() {
                                @Override
                                public int compare(Stock stock1, Stock stock2) {
                                    return Double.compare(stock1.getPERatio(), stock2.getPERatio());
                                }
                            });
                            dialog.dismiss();
                            updateDisplay();
                            break;
                        case 4:
                            Collections.sort(mStocks, new Comparator<Stock>() {
                                @Override
                                public int compare(Stock stock1, Stock stock2) {
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

    private void getStock() {

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mStocksDataSource.getAPIURL())
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
                            //JSONParser jsonParser = new JSONParser(jsonData);
                            mStocks = mStocksDataSource.loadStockDataFromResponse(jsonData);
                            mIndexes = mStocksDataSource.loadIndexDataFromResponse(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    mStocksDataSource.saveStockDataInMemory(jsonData);
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
        if(mStocksDataSource.isStoredDataAvailable()) {
            recyclerView.setAdapter(new StockAdapter(mStocks));
            indexView.setAdapter(new IndexAdapter(mIndexes));
        }
        else {
            recyclerView.swapAdapter(new StockAdapter(mStocks), false);
            indexView.swapAdapter(new IndexAdapter(mStocks), false);
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
        recyclerView.setAdapter(new StockAdapter(mStocks));
        indexView.setAdapter(new IndexAdapter(mIndexes));



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

        private Stock mStock;

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

        public void bindStock(Stock stock){
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

        private List<Stock> mStockList;

        public StockAdapter(List<Stock> stockList) {
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
            Stock stock = mStockList.get(position);
            holder.bindStock(stock);
        }

        @Override
        public int getItemCount() {
            return mStockList.size();
        }
    }


    private class IndexAdapter extends RecyclerView.Adapter<IndexHolder> {

        private List<Stock> mIndexList;

        public IndexAdapter(List<Stock> indexes) {
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
            Stock index= mIndexList.get(position);
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
        private Stock mIndex;

        public IndexHolder(View itemView) {
            super(itemView);
            //Find view by ID

            mIndexSymbol = (TextView) itemView.findViewById(R.id.indexSymbol);
            mIndexPrice = (TextView) itemView.findViewById(R.id.indexPrice);
        }

        public void bindIndex(Stock index){
            mIndex=index;
            mIndexSymbol.setText(mIndex.getSymbol());
            mIndexPrice.setText(mIndex.getPrice()+"");

            mIndexPrice.setTextColor(getResources().getColor(mIndex.getPriceColor()));
        }
    }
}

package com.nasserapps.apitester.Controllers.InProgress;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nasserapps.apitester.AI.InformAI;
import com.nasserapps.apitester.Model.JSONParser;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.DataSource;
import com.nasserapps.apitester.Model.UserData;
import com.nasserapps.apitester.Model.Wallet;
import com.nasserapps.apitester.R;
import com.nasserapps.apitester.exchangeTime;
import com.nasserapps.apitester.stockHistoricalData;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nasser on 10/1/15.
 * The home page of the application
 * Updates Stock on start from SharedPreferences, then update from internet if SharedPreferences data are not up to hour.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Ticker mStock;
    DataSource mDataSource;
    private UserData mUserData;

    @Bind(R.id.nameView) TextView nameView;
    @Bind(R.id.PercentageView) TextView percentageView;
    @Bind(R.id.pbvRatioView) TextView pbvRatio;
    @Bind(R.id.PriceView) TextView priceView;
    @Bind(R.id.peRatioView) TextView peratioView;
    @Bind(R.id.volumeView) TextView volumeView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.fab) FloatingActionButton fab;
    //@Bind(R.id.table) TableLayout table;

    //Chart Trial
    @Bind(R.id.chart) LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mDataSource = new DataSource(getApplicationContext());
        mUserData = new UserData(this);

        //update Display with data from memory
        JSONParser jsonParser;

        try {
            //jsonParser = new JSONParser(mDataSource.getStoredStockData());
            //mStock = jsonParser.getStocks().get(0);
            mStock = mDataSource.loadStocksDataFromMemory().get(0);
            updateDisplay();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //populateTable();

        //Chart Trial
        constructChart();
        //priceView.setText(memory.getString("mStockData","nnn"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get stock data when the time is in the exchange period;
                exchangeTime time = new exchangeTime();
                if (time.isInTheExchangePeriod()) {
                    getStock();
                    Snackbar.make(fab, "1st logic is ok", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(fab, time.getCurrentTimeString() + " is not in the range // " + time.getmOpenTimeString() + " and " + time.getmCloseTimeString(), Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }




    //populate historical table
    private void populateTable() {

        stockHistoricalData QIG = new stockHistoricalData();
        QIG.setYears(new int[]{2014, 2013, 2012, 2011, 2010, 2009, 2008, 2007, 2006});
        QIG.setEPS(new double[]{1.83, 1.77, 1.22, 0.6, 0.57, 0.58, 0.43, 0.39, 0.25});
        QIG.setBestPERatio(new double[]{18.03, 11.19, 11.27, 21.62, 23.16, 20.0, 38.84, 36.41, 57.2});
        QIG.setBestPBVRatio(new double[]{1.89,1.20,0.89,0.86,0.88,0.78,1.53,1.31,1.38});
        QIG.setDividend(new double[]{0.75, 0.75, 0.75, 0.5, 0.5, 0.0, 0.0, 0.0, 0.0});

        // Go through each item in the array
        for (int current = 0; current < QIG.getYears().length; current++)
        {
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);

            // Create a TextView to house the name of the province
            TextView yearLabel = new TextView(this);
            yearLabel.setText(QIG.getYears()[current] + "");
            yearLabel.setPadding(0, 0, 0, 44);
            yearLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(yearLabel);

            // Create a TextView to house the value of the after-tax income
            TextView EPSLabel = new TextView(this);
            EPSLabel.setText(QIG.getEPS()[current] + "");
            EPSLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(EPSLabel);

            TextView PELabel = new TextView(this);
            PELabel.setText(QIG.getBestPERatio()[current] + "");
            PELabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(PELabel);

            TextView PBVLabel = new TextView(this);
            PBVLabel.setText(QIG.getBestPBVRatio()[current] + "");
            PBVLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(PBVLabel);

            TextView dividendLabel = new TextView(this);
            dividendLabel.setText(QIG.getDividend()[current] + "");
            dividendLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(dividendLabel);

            // Add the TableRow to the TableLayout
            //table.addView(tr, new TableRow.LayoutParams(
                    //TableRow.LayoutParams.MATCH_PARENT,
                    //TableRow.LayoutParams.WRAP_CONTENT));
        }
        //table.setStretchAllColumns(true);



    }

    //Get Stock data from Yahoo API TODO save data to SharedPreferences
    private void getStock() {

        if(isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mDataSource.getAPIURL((new Wallet()).getAPIKey()))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            JSONParser jsonParser = new JSONParser(jsonData);
                            mStock = jsonParser.getStocks().get(0);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                            mDataSource.saveStockDataInMemory(jsonData);
                        } else {
                            //alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception caught", e);
                    }
                }
            });
        }

        else{
            Snackbar.make(fab, "No Connection is available", Snackbar.LENGTH_LONG).show();

        }
    }



    //Checks if network is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable =false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    //Change the UI of the ProgressBar (Not Working) TODO Fix ProgressBar which require some type of window access
    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    //Update the UI with new Data. TODO add colors to the UI depending on the data and get data from SharedPreferences
    private void updateDisplay() {
        priceView.setText(mStock.getPrice()+"");
        volumeView.setText(String.format("%,d", mStock.getVolume())+"");
        peratioView.setText(mStock.getPERatio()+"");
        nameView.setText(mStock.getName()+"");
        percentageView.setText(mStock.getChange()+" ("+mStock.getPercentage()+")");
        pbvRatio.setText(mStock.getPBV()+"");

        if(mStock.getPERatio()<=15){
            peratioView.setTextColor(Color.GREEN);
        }
        else {
            peratioView.setTextColor(Color.RED);
        }
        if(mStock.getVolume()>=500000){
            volumeView.setTextColor(Color.GREEN);
        }
        else {
            volumeView.setTextColor(Color.RED);
        }
        if(mStock.getChange()>0){
            percentageView.setTextColor(Color.GREEN);
        }
        else {
            percentageView.setTextColor(Color.RED);
        }
        if(mStock.getPBV()<=1.5){
            pbvRatio.setTextColor(Color.GREEN);
        }
        else {
            pbvRatio.setTextColor(Color.RED);
        }
    }



    //Start AI Assistant if it is not activated (Only allow AI Assistant activation if SharedPreferences indicates that the AI is off).
    public void startAI() {

        boolean isAIActivated = mUserData.isAIActivated();

        if(isAIActivated){
            Snackbar.make(fab, "AI Assistance already working", Snackbar.LENGTH_LONG).show();
        }
        else {
            mUserData.setIsAIActivated(true);
            // Construct an intent that will execute the AlarmReceiver
            Intent intent = new Intent(getApplicationContext(), InformAI.class);
            // Create a PendingIntent to be triggered when the alarm goes off
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, InformAI.REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
            // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
            int interval = 500;
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    interval, pIntent);
            Snackbar.make(fab, "AI Assistance Started", Snackbar.LENGTH_LONG).show();
        }

    }

    //Stop AI Assistant if it is activated (Only allow AI Assistant de-activation if SharedPreferences indicates that the AI is on).
    public void stopAI() {

        boolean isAIActivated = mUserData.isAIActivated();

        if(!isAIActivated){
            Snackbar.make(fab, "AI Assistance already not working", Snackbar.LENGTH_LONG).show();
        }

        else{
            mUserData.setIsAIActivated(false);
            Intent intent = new Intent(getApplicationContext(), InformAI.class);
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, InformAI.REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pIntent);
            Snackbar.make(fab, "AI Assistance Stopped", Snackbar.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.start_ai) {
            startAI();
            return true;
        }
        if (id == R.id.stop_ai) {
            stopAI();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("2005");
        m.add("2006");
        m.add("2007");
        m.add("2008");
        m.add("2009");
        m.add("2010");
        m.add("2011");
        m.add("2012");
        m.add("2013");
        m.add("2014");

        return m;
    }

    private LineData generateDataLine() {

        ArrayList<Entry> e1 = new ArrayList<>();

        e1.add(new Entry(0, 0));
        e1.add(new Entry(0.25f, 1));
        e1.add(new Entry(0.39f, 2));
        e1.add(new Entry(0.43f, 3));
        e1.add(new Entry(0.58f, 4));
        e1.add(new Entry(0.57f, 5));
        e1.add(new Entry(0.6f, 6));
        e1.add(new Entry(1.22f, 7));
        e1.add(new Entry(1.77f, 8));
        e1.add(new Entry(1.83f, 9));

        LineDataSet d1 = new LineDataSet(e1, "EPS");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setDrawValues(true);
        d1.setColor(R.color.colorAccent);
        d1.setCircleColor(R.color.colorPrimaryDark);

        LineData cd = new LineData(getMonths(), d1);
        return cd;
    }

    private void constructChart() {
        chart.setData(generateDataLine());
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelsToSkip(0);
        xAxis.setDrawGridLines(false);
        chart.setScaleYEnabled(false);
    }

}

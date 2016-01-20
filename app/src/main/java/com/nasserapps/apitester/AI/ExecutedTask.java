package com.nasserapps.apitester.AI;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nasserapps.apitester.Model.Stock;
import com.nasserapps.apitester.Model.JSONParser;
import com.nasserapps.apitester.Controllers.MainActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Nasser on 10/2/15.
 * The Artificial Intelligent part of the application that works in the background
 * 1) Get data from Yahoo API in the background.
 * 2) Analyze the data.
 * 3) Decide to notify the user if there is opportunity to invest.
 * 4) Save the data in the SharedPreferences.
 */
public class ExecutedTask extends IntentService {

    private Stock mStock;
    public SharedPreferences memory;
    SharedPreferences.Editor memoryWriter;

    public static final String TAG = MainActivity.class.getSimpleName();

    public ExecutedTask() {
        super("ExecutedTask");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Executed Task ( AI Algorithm)
        memory = getApplicationContext().getSharedPreferences("appMemory", MODE_PRIVATE);
        memoryWriter = memory.edit();

        getStock();

        //Notification for testing only, if not commented will result in error because it display Price before it has value
        //createNotification(1,android.support.design.R.drawable.abc_ic_go_search_api_mtrl_alpha,"Updated Price", "The price is "+mStock.getPrice() );
    }

    private void getStock() {
        String stockURL ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%3D%22QIGD.QA%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(stockURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            JSONParser jsonParser = new JSONParser(jsonData);
                            //mStock = jsonParser.tieData();
                            createNotification(1, android.support.design.R.drawable.abc_ic_ab_back_mtrl_am_alpha,mStock.getSymbol() + " price is "+ mStock.getPrice() + "", "Great Opportunity for investing PE is " + mStock.getPERatio());
                            memoryWriter.putString("mStockData", jsonData).commit();
                        } else {
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

    private void createNotification(int nId, int iconRes, String title, String body) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }
}


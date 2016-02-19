package com.nasserapps.apitester.Controllers.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nasserapps.apitester.Controllers.InProgress.MainActivity;
import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Checklists.Rule;
import com.nasserapps.apitester.Model.Database.JSONParser;
import com.nasserapps.apitester.Model.Ticker;
import com.nasserapps.apitester.Model.User;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nasser on 10/2/15.
 * The Artificial Intelligent part of the application that works in the background
 * 1) Get data from Yahoo API in the background.
 * 2) Analyze the data.
 * 3) Decide to notify the user if there is opportunity to invest.
 * 4) Save the data in the SharedPreferences.
 */
public class ExecutedTask extends IntentService {

    private User mUser;

    public static final String TAG = MainActivity.class.getSimpleName();

    public ExecutedTask() {
        super("ExecutedTask");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Executed Task ( AI Algorithm)

        mUser = User.getUser(this);

        getStock();

        //Notification for testing only, if not commented will result in error because it display Price before it has value
        //createNotification(1,android.support.design.R.drawable.abc_ic_go_search_api_mtrl_alpha,"Updated Price", "The price is "+mStock.getPrice() );
    }

    private void getStock() {
        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mUser.getAPIURL())
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
                            mUser.updateStocksData(jsonData);
                            Checklist checklist = new Checklist("");
                            ArrayList<Rule> rules = new ArrayList<>();
                            rules.add(Rule.getRule("PE Ratio", ">", "15.0"));
                            checklist.setRules(rules);
                            //TODO if conditions are met construct notification for each stock
                            for(Ticker ticker:mUser.getWatchList()) {
                                if(checklist.isPassing(ticker)) {
                                    createNotification(1, android.support.design.R.drawable.abc_ic_ab_back_mtrl_am_alpha, ticker.getSymbol() + " price is " + ticker.getPrice() + "", "Great Opportunity for investing PE is " + ticker.getPERatio());
                                }
                            }
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


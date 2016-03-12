package com.nasserapps.saham.Controllers.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;

import com.nasserapps.saham.Controllers.Activities.HomeActivity;
import com.nasserapps.saham.Controllers.Activities.StockDetailsActivity;
import com.nasserapps.saham.Model.Checklists.Checklist;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.User;
import com.nasserapps.saham.R;

import java.util.HashMap;

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

    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final int REFRESHING_INTERVAL = 500; //1 minutes * 60000  millie seconds in a minute

    public ExecutedTask() {
        super("ExecutedTask");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Executed Task ( AI Algorithm)

        mUser = User.getUser(this);

        //getStock();


        //Testing-----------
        testingBlock();
        //------------------

        //Notification for testing only, if not commented will result in error because it display Price before it has value
        //createNotification(1,android.support.design.R.drawable.abc_ic_go_search_api_mtrl_alpha,"Updated Price", "The price is "+mStock.getValue() );
    }

    private void testingBlock() {
        HashMap<Stock,Checklist> notificationRules = mUser.getNotificationRules();
        int i=0;
        for(Stock stock :notificationRules.keySet()) {
            if(stock.isNotificationEnabled() && notificationRules.get(stock).isPassing(stock)) { //notificationRules.get(stock).isPassing(stock)
                createNotification(i,stock,notificationRules.get(stock));
                i++;
            }
        }
    }





//    private void getStock() {
//        if(isNetworkAvailable()) {
//
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(getResources().getString(R.string.API_url))
//                    .build();
//
//            Call call = client.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//                    try {
//                        String jsonData = response.body().string();
//                        if (response.isSuccessful()) {
//                            mUser.updateStocksData(jsonData);
//                            HashMap<Stock,Checklist> notificationRules = mUser.getNotificationRules();
//                            for(Stock stock :notificationRules.keySet()) {
//                                if(notificationRules.get(stock).isPassing(stock)) {
//                                    createNotification(stock.hashCode(), R.drawable.bell_ring, stock.getName(), "The stock is price is > 56 and volume is < 2000 ", true, stock);
//                                }
//                            }
//                        } else {
//                        }
//                    } catch (IOException e) {
//                        Log.e(TAG, "IO Exception caught", e);
//                    } catch (JSONException e) {
//                        Log.e(TAG, "JSON Exception caught", e);
//                    }
//                }
//            });
//        }
//
//        else{
//        }
//    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable =false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    private void createNotification(int nId, Stock stock, Checklist checklist) {

        Intent resultIntent = new Intent(this, StockDetailsActivity.class);
        resultIntent.putExtra("Symbol", stock.getSymbol());
        PendingIntent stockDetailsIntent = PendingIntent.getActivity(this, nId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent muteIntent = new Intent(this,MuteService.class);
        muteIntent.putExtra("Symbol",stock.getSymbol());
        muteIntent.putExtra("notification_id",nId);

            NotificationCompat.BigTextStyle notiStyle =
                    new NotificationCompat.BigTextStyle();
            notiStyle.setBigContentTitle(stock.getName())
            .bigText(String.format("Price = %.2f%nChange = %.2f (%s)%nAsk = %.2f%nBid = %.2f%nPE Ratio = %.2f%nVolume = %,d", stock.getPrice(), stock.getChange(),stock.getPercentage(),stock.getAsk(), stock.getBid(), stock.getPERatio(), stock.getVolume()));

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this).setSmallIcon(R.drawable.bell_ring)
                    .setContentTitle(stock.getName())
                    .setContentText("Price = "+stock.getPrice()+" & Change = "+stock.getChange()+" ("+stock.getPercentage()+")")
                    .setContentIntent(stockDetailsIntent)
                    .addAction(R.drawable.bell_outline, "Mute", PendingIntent.getService(this, nId, muteIntent,PendingIntent.FLAG_UPDATE_CURRENT))
                    .setStyle(notiStyle)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(1);//High priority

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(nId, mBuilder.build());

    }
}


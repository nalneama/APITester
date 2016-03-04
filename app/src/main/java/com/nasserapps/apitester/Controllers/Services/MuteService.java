package com.nasserapps.apitester.Controllers.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Stock;
import com.nasserapps.apitester.Model.Tools;
import com.nasserapps.apitester.Model.User;

public class MuteService extends IntentService {

    public MuteService() {
        super("Mute");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(intent.getIntExtra("notification_id",0));
        String stockSymbol = intent.getStringExtra("Symbol");
        User mUser = User.getUser(this);
        DataSource mDatasource = new DataSource(this);
        Stock stock = Tools.getStockFromList(stockSymbol,mUser.getAllStocks());
        stock.setIsNotificationEnabled(false);
        mDatasource.updateStock(stock);
    }
}

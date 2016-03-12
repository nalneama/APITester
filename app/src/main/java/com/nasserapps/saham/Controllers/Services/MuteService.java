package com.nasserapps.saham.Controllers.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.nasserapps.saham.Model.Database.DataSource;
import com.nasserapps.saham.Model.Stock;
import com.nasserapps.saham.Model.Tools;
import com.nasserapps.saham.Model.User;

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

package com.nasserapps.apitester.Controllers.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Nasser on 10/2/15.
 * BroadcastReceiver to notify AI (ExecutedTask.java) to start after reboot
 * Receives Intent from System that phone is rebooted.
 * 1) Check SharedPreferences if the user activated AI assistant.
 * 2) If yes, then notify ExecutedTask.
 *    If no,  then do nothing.
 */
public class InformAIAfterBooting extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */
            SharedPreferences memory = context.getSharedPreferences("isAIActivated", context.MODE_PRIVATE);
            boolean isAIActivated = memory.getBoolean("isAIActivated", false);

            if(isAIActivated) {
                Intent alarmIntent = new Intent(context, InformAI.class);
                final PendingIntent pIntent = PendingIntent.getBroadcast(context, InformAI.REQUEST_CODE,
                        alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 500;
                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pIntent);

                Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

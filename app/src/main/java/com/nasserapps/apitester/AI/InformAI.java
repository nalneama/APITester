package com.nasserapps.apitester.AI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nasser on 10/2/15.
 * BroadcastReceiver to notify AI (ExecutedTask.java) about the requirement to do tasks
 * Receives Intent from AlarmManager in MainActivity.
 * 1) Sends Intent to ExecutedTasks.
 * 2) Change SharedPreferences to activated AI assistant.
 */
public class InformAI extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.nasserapps.AI.wake";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, ExecutedTask.class);
            context.startService(i);
    }
}

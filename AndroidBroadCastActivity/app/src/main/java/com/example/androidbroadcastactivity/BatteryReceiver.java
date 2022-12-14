package com.example.androidbroadcastactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null && intent.getAction() == Intent.ACTION_BATTERY_CHANGED){
            Toast.makeText(context,"Battery status or level changed",Toast.LENGTH_LONG).show();
        }
    }
}
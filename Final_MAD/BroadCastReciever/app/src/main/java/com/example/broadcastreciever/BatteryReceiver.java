package com.example.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
     if(intent.getAction()!=null&&intent.getAction()==Intent.ACTION_BATTERY_CHANGED){
         Toast.makeText(context,"Battery Level Changed",Toast.LENGTH_SHORT).show();
     }
    }
}
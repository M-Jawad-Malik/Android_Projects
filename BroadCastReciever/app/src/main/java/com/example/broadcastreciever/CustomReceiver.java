package com.example.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null&&intent.getAction()=="Broadcast.CUSTOM_INTENT")
        {
        Toast.makeText(context,"BroadCast Reciever Activated",Toast.LENGTH_SHORT).show();
    }
    }
}
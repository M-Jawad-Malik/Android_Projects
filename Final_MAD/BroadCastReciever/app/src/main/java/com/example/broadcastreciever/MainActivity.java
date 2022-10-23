package com.example.broadcastreciever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CustomReceiver customReceiver=new CustomReceiver();
    IntentFilter customintent=new IntentFilter("Broadcast.CUSTOM_INTENT");
    BatteryReceiver batteryReceiver=new BatteryReceiver();
    IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void customIntent(View view){
        Intent intent=new Intent();
        intent.setAction("Broadcast.CUSTOM_INTENT");
        sendBroadcast(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(customReceiver,customintent);
        registerReceiver(batteryReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(customReceiver);
    unregisterReceiver(batteryReceiver);
    }
}
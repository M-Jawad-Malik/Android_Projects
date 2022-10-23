package com.example.androidbroadcastactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    MyCustomReciever batteryReceiver=new MyCustomReciever();
    IntentFilter filter = new IntentFilter("com.example.AndroidBroadCastActiviy.CUSTOM_INTENT");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    public void BroadcastIntent(View view){
        Intent intent=new Intent();
        intent.setAction("com.example.AndroidBroadCastActiviy.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
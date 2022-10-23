package com.example.androidringermode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button vibrateBtn=(Button)findViewById(R.id.btn1);
        Button normalBtn=(Button)findViewById(R.id.btn2);
        Button silentBtn=(Button)findViewById(R.id.btn3);
        AudioManager audioManger=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        vibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManger.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(MainActivity.this,"Vibrate Mode",Toast.LENGTH_LONG).show();
            }
        });
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManger.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(MainActivity.this,"Normal Mode",Toast.LENGTH_LONG).show();
            }
        });
        silentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                if(notificationManager.isNotificationPolicyAccessGranted())
                {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                audioManger.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(MainActivity.this,"Silent Mode",Toast.LENGTH_LONG).show();}
            }
        });
    }
}
package com.example.ringermodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button silentbtn,vibratebtn,normalbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        silentbtn=(Button)findViewById(R.id.silenbtn);
        vibratebtn=(Button)findViewById(R.id.vibratebtn);
        normalbtn=(Button)findViewById(R.id.normalbtn);
        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        silentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(MainActivity.this,"Silent Mode On",Toast.LENGTH_SHORT).show();
            }
        });
        vibratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(MainActivity.this,"Vibrate Mode On",Toast.LENGTH_SHORT).show();

            }
        });
        normalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(MainActivity.this,"Normal Mode On",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
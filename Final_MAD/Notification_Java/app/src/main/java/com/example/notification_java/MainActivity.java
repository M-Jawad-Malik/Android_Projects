package com.example.notification_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendNotification(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("MyNotifications","MyNotifications",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel);

        }
        //Important Code for final Papers
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"MyNotifications")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("LGU")
                .setContentText("Your Message");
        NotificationManagerCompat manager= NotificationManagerCompat.from(this);
        manager.notify(1,builder.build());
    }
    public void sendEmail(View view)
    {
        Intent emailintent=new Intent(Intent.ACTION_SEND);
        String[] arr={"jawaddmuhammad@gmail","meesam14125@gmail.com"};
        emailintent.setData(Uri.parse("mailto:"));
        emailintent.setType("text/plain");
        emailintent.putExtra(Intent.EXTRA_EMAIL,arr);
        emailintent.putExtra(Intent.EXTRA_CC,"");
        emailintent.putExtra(Intent.EXTRA_SUBJECT,"Email Subject");
        emailintent.putExtra(Intent.EXTRA_TEXT,"Hello");
        try {
            startActivity(Intent.createChooser(emailintent,"choose email app"));
            finish();
        }catch (ActivityNotFoundException exception){
            Toast.makeText(this,"Email Not found", Toast.LENGTH_LONG);
        }


    }
}
package com.example.shockrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    MPU6050 mpu6050;
    Vector<Double> highImpact;
    Vector<Double>  medImpact;
    Vector<Double>  lowImpact;
    Vector<Double> temperature;
    TextView highImpactValue;
    TextView lowImpactValue;
    TextView medImpactValue;
    TextView CurrentTempValue;
    TextView minTempValue;
    TextView maxTempValue;
    TextView avgTempValue;
    Double oldValue;
    Double oldValueTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpu6050=new MPU6050();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        highImpact=new Vector<Double>();
        lowImpact=new Vector<Double>();
        medImpact=new Vector<Double>();
        temperature=new Vector<Double>();
        highImpactValue=findViewById(R.id.highimpact_value);
        lowImpactValue=findViewById(R.id.lowimpact_value);
        medImpactValue=findViewById(R.id.medimpact_value);
        CurrentTempValue=findViewById(R.id.tempCurrent_value);
        minTempValue=findViewById(R.id.tempMin_value);
        maxTempValue=findViewById(R.id.tempMax_value);
        avgTempValue=findViewById(R.id.tempAvg_value);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.highimpact_value_ll);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.medimpact_value_ll);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.lowimpact_value_ll);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("MPU6050");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 mpu6050=snapshot.getValue(MPU6050.class);
                 try {


//                 String s;
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    if (ds.getKey() == "Real_Time_Temperature") {
//                        mpu6050.setReal_Time_Temperature(Double.valueOf( ds.getValue().toString()));
//                        Toast.makeText(getApplicationContext(),"Yes",Toast.LENGTH_LONG).show();
//                    }
//                    Toast.makeText(getApplicationContext(),String.valueOf(ds.getKey().equals("Real_Time_Acceleration_X")),Toast.LENGTH_LONG).show();
//
//                }
                     if (oldValue != mpu6050.getReal_Time_Acceleration_X()) {
                         if (mpu6050.getReal_Time_Acceleration_X() > 6) {
                             highImpact.add(mpu6050.getReal_Time_Acceleration_X());

                         } else if (mpu6050.getReal_Time_Acceleration_X() >= 4 && mpu6050.getReal_Time_Acceleration_X() <= 6) {
                             medImpact.add(mpu6050.getReal_Time_Acceleration_X());
                         } else if (mpu6050.getReal_Time_Acceleration_X() < 4 && mpu6050.getReal_Time_Acceleration_X() >= 1) {
                             lowImpact.add(mpu6050.getReal_Time_Acceleration_X());
                         }
                         Object s1[] = highImpact.toArray();
                         Object s2[] = medImpact.toArray();
                         Object s3[] = lowImpact.toArray();

                         highImpactValue.setText(String.valueOf(s1.length));
                         medImpactValue.setText(String.valueOf(s2.length));
                         lowImpactValue.setText(String.valueOf(s3.length));
                         linearLayout1.removeAllViews();
                         linearLayout2.removeAllViews();
                         linearLayout3.removeAllViews();
                         // Add textview 1
                         for (int i = 0; i < s1.length; i++) {
                             TextView textView1 = new TextView(getApplicationContext());
                             textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                     LinearLayout.LayoutParams.WRAP_CONTENT));
                             textView1.setText(s1[i].toString() + " m/s^2");
                             linearLayout1.addView(textView1);
                         }
                         for (int i = 0; i < s2.length; i++) {
                             TextView textView1 = new TextView(getApplicationContext());
                             textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                     LinearLayout.LayoutParams.WRAP_CONTENT));
                             textView1.setText(s2[i].toString() + " m/s^2");
                             linearLayout2.addView(textView1);
                         }
                         for (int i = 0; i < s3.length; i++) {
                             TextView textView1 = new TextView(getApplicationContext());
                             textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                     LinearLayout.LayoutParams.WRAP_CONTENT));
                             textView1.setText(s3[i].toString() + " m/s^2");
                             linearLayout3.addView(textView1);
                         }
                     }
                     if (oldValueTemp != mpu6050.getReal_Time_Temperature()) {
                         temperature.add(mpu6050.getReal_Time_Temperature());
                         Object s1[] = temperature.toArray();
                         Double max = 0.0;
                         Double min = 0.0;
                         Double sum = 0.0;
                         Double avg;
                         for (int i = 0; i < s1.length; i++) {
                             sum = sum + Double.valueOf(s1[i].toString());
                             if (max < Double.valueOf(s1[i].toString())) {
                                 max = Double.valueOf(s1[i].toString());
                             }
                             min=max;
                             if (min > Double.valueOf(s1[i].toString())) {
                                 min = Double.valueOf(s1[i].toString());
                             }

                         }
                         avg = sum / s1.length;
                         CurrentTempValue.setText(mpu6050.getReal_Time_Temperature().floatValue() + " Celcius");
                         minTempValue.setText(min.floatValue()+ " Celcius");
                         maxTempValue.setText(max.floatValue() + " Celcius");
                         avgTempValue.setText(avg.floatValue() + " Celcius");

                     }
                     oldValueTemp = mpu6050.getReal_Time_Temperature();
                     oldValue = mpu6050.getReal_Time_Acceleration_X();


                 }catch (Exception ce)
                 {
                     Toast.makeText(getApplicationContext(),ce.getMessage().toString(),Toast.LENGTH_LONG).show();
                 }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
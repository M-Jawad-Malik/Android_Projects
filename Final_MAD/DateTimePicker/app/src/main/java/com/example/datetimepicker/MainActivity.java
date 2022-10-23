package com.example.datetimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button startbtn,stopbtn,nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* nextbtn=(Button)findViewById(R.id.next);
        startbtn=(Button)findViewById(R.id.Start);
        stopbtn=(Button)findViewById(R.id.Stop);*/
    }
    public void showDatePicker(View view){
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"DatePicker");
    }
    public void showTimePicker(View view){
        TimePickerFragment timePickerFragment=new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(),"TimePicker");
    }


    public void processDatePickerResult(int year,int month,int day){
        String year_String=Integer.toString(year);
        String month_string=Integer.toString(month);
        String day_string=Integer.toString(day);
        String date_format=(day_string+"/"+month_string+"/"+year_String);
        Toast.makeText(this,date_format,Toast.LENGTH_LONG).show();

    }

    public void processTimePickerResult(int hour,int min){
        String hour_string=Integer.toString(hour);
        String min_string=Integer.toString(min);
        String time_format=(hour_string+":"+min_string+":00");
        Toast.makeText(this,time_format,Toast.LENGTH_LONG).show();

    }
}
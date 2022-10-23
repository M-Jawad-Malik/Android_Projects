package com.example.datetimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showTimePicker(View view){
        DatePickerFragment dialog=new DatePickerFragment();
        dialog.show(getSupportFragmentManager(),"Date Picker");

    }
    public void showDatePicker(View view){
        TimePickerFragment dialog=new TimePickerFragment();
        dialog.show(getSupportFragmentManager(),"Time Picker");

    }
    public void processTime(int hour,int minutes){
        String Hours= Integer.toString(hour);
        String Minutes=Integer.toString(minutes);
        String Time_Format=(Hours+":"+Minutes+":00");
        Toast.makeText(this, Time_Format,Toast.LENGTH_LONG).show();

    }
    public void processDate(int year,int month,int day){
    String Year=Integer.toString(year);
    String Month=Integer.toString(month);
    String Day=Integer.toString(day);
    String Date_Format=(Day+"/"+Month+"/"+Year);
    Toast.makeText(this,Date_Format,Toast.LENGTH_LONG).show();
    }
}
package com.example.ageindays;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ageindays.R.id;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public final class MainActivity extends AppCompatActivity {
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.btnDatePicker);
        btn.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View view) {

                clickDatePicker(view);
            }
        }));
    }

    public final void clickDatePicker( View view) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog((Context)this, (OnDateSetListener)(new OnDateSetListener() {
            public final void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                Toast.makeText((Context) MainActivity.this, (CharSequence) ("Choosen year:" + selectedYear + ", month:" + (selectedMonth + 1) + ", day: " + selectedDayOfMonth), Toast.LENGTH_LONG).show();
                String selectedDate = "" + selectedDayOfMonth + '/' + (selectedMonth + 1) + '/' + selectedYear;
                ((TextView) findViewById(R.id.tvSelectedDate)).setText((CharSequence) selectedDate);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                try {
                    Date theDate = sdf.parse(selectedDate);
                    long selectedDateInMinutes = theDate.getTime() / (long) 86400000;
                    Date currentDate = sdf.parse(sdf.format(System.currentTimeMillis()));
                    long currentDateInMinutes = currentDate.getTime() / (long) 86400000;
                    long dateInMinutes = currentDateInMinutes - selectedDateInMinutes;
                    ((TextView) findViewById(R.id.tvSelectedDateInMinutes)).setText((CharSequence) String.valueOf(dateInMinutes));
                } catch (Exception e) {
                     Toast.makeText((Context) MainActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }}), year, month, dayOfMonth);
        dpd.getDatePicker().setMaxDate((new Date()).getTime());
        dpd.show();
    }




}

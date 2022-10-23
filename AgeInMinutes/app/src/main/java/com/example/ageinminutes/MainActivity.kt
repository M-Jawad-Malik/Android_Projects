package com.example.ageinminutes
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDatePicker.setOnClickListener { view->clickDatePicker(view) }
    }
    //View Something displayed on screen
    fun clickDatePicker(view:View){
        val myCalendar=Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val dayOfMonth=myCalendar.get(Calendar.DAY_OF_MONTH)
       val dpd= DatePickerDialog(this,DatePickerDialog.OnDateSetListener {
            view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this,"Choosen year:${selectedYear}, month:${selectedMonth+1}, day: $selectedDayOfMonth",Toast.LENGTH_LONG).show()
            val selectedDate="$selectedDayOfMonth/${selectedMonth+1}/${selectedYear}"
            tvSelectedDate.setText(selectedDate)
            val sdf=SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val theDate=sdf.parse(selectedDate)
           //.time return time in milliseconds
            val selectedDateInMinutes=theDate!!.time/86400000
            val currentDate=sdf.parse(sdf.format(System.currentTimeMillis()))
            val currentDateInMinutes=currentDate!!.time/86400000
            val dateInMinutes=currentDateInMinutes-selectedDateInMinutes
            tvSelectedDateInMinutes.setText("$dateInMinutes")
        },year,month,dayOfMonth)
        dpd.datePicker.setMaxDate(Date().time)
        dpd.show()

    }


}
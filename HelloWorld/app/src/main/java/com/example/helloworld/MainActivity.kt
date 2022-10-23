package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity //Ensure Compatible on different Devices
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnclickme = findViewById(R.id.button) as Button
        val myTextView=findViewById(R.id.textView) as TextView
        var timeClicked=0
        // set on-click listener
        btnclickme.setOnClickListener {
            timeClicked=timeClicked+1
            myTextView.text=timeClicked.toString()
            Toast.makeText(this@MainActivity, "Hi Jawad.", Toast.LENGTH_SHORT).show()
        }
    }
}
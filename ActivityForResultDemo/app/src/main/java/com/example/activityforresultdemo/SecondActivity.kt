package com.example.activityforresultdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btn_submit.setOnClickListener {
            val intent= Intent() //Transfer of data, significant for launching of activities
            intent.putExtra(MainActivity.name,et_name.text.toString())
            intent.putExtra(MainActivity.email,et_email.text.toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
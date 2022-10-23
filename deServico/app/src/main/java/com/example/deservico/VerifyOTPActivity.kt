package com.example.deservico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class VerifyOTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)
        Utils.blackIconStatusBar(this,R.color.Light_background)
    }
}
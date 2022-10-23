package com.example.deservico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.deservico.databinding.ActivitySendOTPBinding

class SendOTPActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendOTPBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.blackIconStatusBar(this,R.color.Light_background)
        binding= ActivitySendOTPBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextBtnSendOTP.setOnClickListener {
            val intent=Intent(this,VerifyOTPActivity::class.java)
            startActivity(intent)
            Log.d("Hello","Workig")
        }
    }
}
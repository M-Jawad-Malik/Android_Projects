package com.example.deservico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.deservico.databinding.ActivityServProSignUpBinding

class ServProSignUp : AppCompatActivity() {
    private lateinit var binding:ActivityServProSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityServProSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.blackIconStatusBar(this,R.color.Light_background)
        binding.mClientbtnC.setOnClickListener {
            var intent=Intent(this,ClientSignUp::class.java)
            startActivity(intent)

        }
        binding.SerPronextbtn.setOnClickListener {
            val email=binding.SerProEmail.text.toString()
            val name=binding.SerProName.text.toString()
            val password=binding.SerProPassword.text.toString()
            val cnfrmpassword=binding.cnfrmpassword.text.toString()


    if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && cnfrmpassword.isNotEmpty()) {
      if(password!=cnfrmpassword)
        {
            binding.passwordmatch.visibility=View.VISIBLE;
            }else {
                binding.passwordmatch.visibility=View.GONE;
        var intent = Intent(this, ServProSignUp2::class.java)
        var arr: ArrayList<String> = ArrayList()
        arr.add(binding.SerProEmail.text.toString())
        arr.add(binding.SerProPassword.text.toString())
        arr.add(binding.SerProName.text.toString())
        intent.putStringArrayListExtra("val", arr)
        startActivity(intent)
            }
    } else {
        object : Thread() {
            override fun run() {
                this@ServProSignUp.runOnUiThread(Runnable {
        Toast.makeText(this@ServProSignUp, "Please Fill al boxes", Toast.LENGTH_SHORT).show()
                })
            }
        }.start()

    }

        }
        binding.serProSignIntxt.setOnClickListener {
            var intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
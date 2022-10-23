package com.example.deservico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.deservico.databinding.ActivitySplashscreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.blackIconStatusBar(this,R.color.Light_background);
        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE
        val backgroundImage: ImageView = findViewById(R.id.SplashScreenImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(slideAnimation)
        try {
            val Useruid= FirebaseAuth.getInstance().uid?:""
            checkUserIsLogin(Useruid)
        }catch (e:Exception)
        {
            Log.d("radius",e.toString())
        }


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

    }
    private fun checkUserIsLogin(uid:String?){
        try {
            if(uid!="") {
                val assignClientRef= FirebaseDatabase.getInstance().getReference().child("SerType").child(uid!!)
                val lis=assignClientRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            Handler(Looper.getMainLooper()).postDelayed({
                                var intent = IntentD(this@SplashScreen, DriverMapsActivity::class.java)
                                intent!!.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }, 3000)


                        }else{
                            Handler(Looper.getMainLooper()).postDelayed({
                                val intent = IntentC(this@SplashScreen, CustomerMapActivity::class.java)
                                intent!!.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }, 3000)

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }})
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                intent!!.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                }, 3000)
            }
        }catch (e:Exception){
        }

    }

    private fun IntentC(context: Context, java: Class<CustomerMapActivity>): Intent? {
        val intent=Intent(context,java)
        return intent
    }
    private fun IntentD(context: Context, java: Class<DriverMapsActivity>): Intent? {
        val intent=Intent(context,java)
        return intent
    }
}
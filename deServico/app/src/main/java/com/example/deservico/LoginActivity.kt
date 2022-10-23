package com.example.deservico

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.deservico.databinding.ActivityCustomerMapBinding
import com.example.deservico.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Driver

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.blackIconStatusBar(this,R.color.Light_background)
        binding.signuptxt.setOnClickListener {
            var intent=Intent(this,ClientSignUp::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.loginbtn.setOnClickListener {
            PerformLogin()
        }


    }
    private fun checkUserIsLogin(uid:String?){
        try {
        if(uid!=null) {
            val assignClientRef= FirebaseDatabase.getInstance().getReference().child("SerType").child(uid!!)
            val lis=assignClientRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var intent = IntentD(this@LoginActivity, DriverMapsActivity::class.java)
                        intent!!.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    }else{
                        val intent = IntentC(this@LoginActivity, CustomerMapActivity::class.java)
                        intent!!.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }})
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
    private fun PerformLogin(){
        binding.wrongemailPass.isInvisible=true
        val email=binding.email.text.toString().trim(){it<=' '}
        val password=binding.password.text.toString().trim(){it<=' '}
        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this,"Please Fill the Box", Toast.LENGTH_LONG).show()
            return
        }
        val auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        val uid=auth!!.currentUser!!.uid
                      checkUserIsLogin(uid!!)
                    }
                }
                .addOnFailureListener{
                   binding.wrongemailPass.isVisible=true
                    Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
                }

    }
}
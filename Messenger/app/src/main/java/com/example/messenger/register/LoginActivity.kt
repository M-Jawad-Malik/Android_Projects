package com.example.messenger.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.messenger.register.MainActivity
import com.example.messenger.R
import com.example.messenger.message.LatestMessageActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.txtemail
import kotlinx.android.synthetic.main.activity_main.txtpassword

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signinbtn.setOnClickListener {
            PerformLogin()
        }
        SingUptxt.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun PerformLogin(){
       wrongemailPass.isInvisible=true
        val email=txtemail.text.toString().trim(){it<=' '}
        val password=txtpassword.text.toString().trim(){it<=' '}
        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this,"Please Fill the Box", Toast.LENGTH_LONG).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {

                    val intent=Intent(this, LatestMessageActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
            }
            }
            .addOnFailureListener{
                wrongemailPass.isVisible=true
                Toast.makeText(this,"Incorrect Email or Password!",Toast.LENGTH_SHORT).show()
            }

    }
}
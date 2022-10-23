package com.example.linearlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class signupPg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_pg);
        TextView signIntxt=(TextView) findViewById(R.id.txtSingIn);
        signIntxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInActivity();
            }
        });
        Button signUpbtn=(Button)findViewById(R.id.signupBtn);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorInActivity();
            }
        });

    }
    public void signInActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void CalculatorInActivity(){
        Intent intent=new Intent(this,calculator.class);
        startActivity(intent);
    }
}
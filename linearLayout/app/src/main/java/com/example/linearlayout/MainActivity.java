package com.example.linearlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pg);

        TextView button = (TextView) findViewById(R.id.txtSingUp);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          intentClick();
                                      }
                                  }

        );
        Button signUpbtn=(Button)findViewById(R.id.signinbtn);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorInActivity();
            }
        });
    }
    public void intentClick() {
        Intent intent = new Intent(this, signupPg.class);
        startActivity(intent);

    }
    public void CalculatorInActivity(){
        Intent intent=new Intent(this,ScrollViewExmp.class);
        startActivity(intent);
    }
}
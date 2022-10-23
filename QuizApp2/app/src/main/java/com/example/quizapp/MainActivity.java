package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.example.quizapp.R.id;


public final class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
    public void startbtn(View v){
        EditText et_name = (EditText)findViewById(R.id.et_name);
        if (et_name.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this,"Please Enter Your Name", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MainActivity.this, QuizQuestionActivity.class);
            intent.putExtra("username", et_name.getText().toString());
            startActivity(intent);
            finish();
        }
    }


}

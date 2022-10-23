package com.example.quizapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R.id;
public class ResultActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.activity_result);
            String username = getIntent().getStringExtra("username");
            int correctAnswer = getIntent().getIntExtra("correct_answer", 0);
            int totalQuestions = getIntent().getIntExtra("total_questions", 0);
            TextView tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setText((CharSequence)username);
        TextView  tv_score = (TextView)findViewById(R.id.tv_score);
        tv_score.setText((CharSequence)("Your Score is " + String.valueOf(correctAnswer) + " out of  " + totalQuestions));
            ((Button)findViewById(R.id.btn_finish)).setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                }
            }));
        }




    }


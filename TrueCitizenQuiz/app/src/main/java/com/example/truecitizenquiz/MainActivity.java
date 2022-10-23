 package com.example.truecitizenquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button false_button,true_button;
    private TextView question_txtvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Question question=new Question(R.string.my_test_question,true);
        setContentView(R.layout.activity_main);
        false_button=findViewById(R.id.false_button);
        true_button=findViewById(R.id.true_button);
        question_txtvw=findViewById(R.id.answer_text_view);
        false_button.setOnClickListener(this);
        true_button.setOnClickListener(this);
    }

     @Override
     public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.false_button:
                 break;
             case R.id.true_button:
                 break;
         }
     }
 }
package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.quizapp.R.id;
import java.util.ArrayList;
import java.util.Iterator;


public final class QuizQuestionActivity extends AppCompatActivity implements OnClickListener {
    private int mCurrentPosition = 1;
    private ArrayList mQuestionList;
    private int mSelectedOptionPosition;
    private int mCorrectAnswers;

    private String mUserName;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_quiz_question);

        mUserName = this.getIntent().getStringExtra("username");

        Constants constant=new Constants();
        mQuestionList = constant.getQuestions();

        ((TextView)findViewById(R.id.tv_option_one)).setOnClickListener((OnClickListener)this);
        ((TextView)findViewById(R.id.tv_option_two)).setOnClickListener((OnClickListener)this);
        ((TextView)findViewById(R.id.tv_option_three)).setOnClickListener((OnClickListener)this);
        ((TextView)findViewById(R.id.tv_option_four)).setOnClickListener((OnClickListener)this);
        ((Button)findViewById(R.id.btn_submit)).setOnClickListener((OnClickListener)this);

        setQuestion();
    }

    public final void setQuestion() {
        defualtOptionView();
        Question quesiton= (Question)mQuestionList.get(mCurrentPosition-1);

        ProgressBar progressBar = (ProgressBar)findViewById(id.progressBar);

        progressBar.setProgress(mCurrentPosition);

        TextView tv_progress = (TextView)findViewById(id.tv_progress);

        tv_progress.setText((mCurrentPosition+"/"+progressBar.getMax()).toString());

        TextView tv_question = (TextView)findViewById(id.tv_question);

        tv_question.setText(quesiton.getQuestion());

        ((ImageView)findViewById(id.tv_image)).setImageResource(quesiton.getImage());

        TextView tv_option_one = (TextView)findViewById(id.tv_option_one);

        tv_option_one.setText(quesiton.getOptionOne());

        TextView tv_option_two = (TextView)findViewById(id.tv_option_two);

        tv_option_two.setText(quesiton.getOptionTwo());

        TextView tv_option_three = (TextView)findViewById(id.tv_option_three);

        tv_option_three.setText(quesiton.getOptionThree());

        TextView tv_option_four = (TextView)findViewById(id.tv_option_four);

        tv_option_four.setText(quesiton.getOptionFour());

        /*Button var7=(Button)findViewById(id.btn_submit);
        if (mCurrentPosition == mQuestionList.size()) {
            var7.setText("Finish");
        } else {
            var7.setText("SUBMIT");
        }*/

    }

    private final void defualtOptionView() {
        ArrayList options = new ArrayList();
        options.add(0, (TextView)findViewById(R.id.tv_option_one));
        options.add(1, (TextView)findViewById(R.id.tv_option_two));
        options.add(2, (TextView)findViewById(R.id.tv_option_three));
        options.add(3, (TextView)findViewById(R.id.tv_option_four));
        Iterator var3 = options.iterator();
        while(var3.hasNext()) {
            TextView option = (TextView)var3.next();
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable((Context)this,  R.drawable.default_option_border_bg));
        }

    }

    private final void selectedOptionView(TextView tv, int selectedOptionNum) {
        this.defualtOptionView();
        this.mSelectedOptionPosition = selectedOptionNum;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable((Context)this, R.drawable.selected_option_border_bg));
    }

    private final void answerView(int answer, int drawable) {
        TextView var10000;
        switch(answer) {
            case 1:
                TextView tv_option_one = (TextView)findViewById(R.id.tv_option_one);

                tv_option_one.setBackground(ContextCompat.getDrawable(this, drawable));
                break;
            case 2:
                TextView tv_option_two = (TextView)findViewById(R.id.tv_option_two);

                tv_option_two.setBackground(ContextCompat.getDrawable(this, drawable));
                break;
            case 3:
                TextView  tv_option_three = (TextView)findViewById(R.id.tv_option_three);

                tv_option_three.setBackground(ContextCompat.getDrawable(this, drawable));
                break;
            case 4:
                TextView tv_option_four = (TextView)findViewById(R.id.tv_option_four);

                tv_option_four.setBackground(ContextCompat.getDrawable(this, drawable));
        }

    }

    public void onClick(View v) {
        Integer var2 = v != null ? v.getId() : null;
        int var3 = R.id.tv_option_one;

        TextView var10;
        if (var2 != null) {

            if (var2 == var3) {
                var10 = (TextView)findViewById(R.id.tv_option_one);
                selectedOptionView(var10, 1);
                return;
            }
        }

        var3 =R.id.tv_option_two;

        if (var2 != null) {
            if (var2 == var3) {
                var10 = (TextView)findViewById(R.id.tv_option_two);

                this.selectedOptionView(var10, 2);
                return;
            }
        }

        var3 = R.id.tv_option_three;
        if (var2 != null) {
            if (var2 == var3) {
                var10 = (TextView)findViewById(R.id.tv_option_three);

                this.selectedOptionView(var10, 3);
                return;
            }
        }

        var3 = R.id.tv_option_four;
        if (var2 != null) {
            if (var2 == var3) {
                var10 = (TextView)findViewById(R.id.tv_option_four);

                this.selectedOptionView(var10, 4);
                return;
            }
        }

        var3 = id.btn_submit;
        if (var2 != null) {
            if (var2 == var3) {
                ArrayList var9;
                int var10000;
                int var10001;
                if (mSelectedOptionPosition == 0) {
                     mCurrentPosition++;

                    if (mCurrentPosition <= mQuestionList.size()) {
                        setQuestion();
                    } else {
                        Toast.makeText(this, "You have Successfully Completed Quiz", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, ResultActivity.class);
                        intent.putExtra("username", this.mUserName);
                        intent.putExtra("correct_answer", this.mCorrectAnswers);
                        intent.putExtra("total_questions", mQuestionList.size());
                        this.startActivity(intent);
                        this.finish();
                    }
                } else {

                    Question question = mQuestionList != null ? (Question)mQuestionList.get(this.mCurrentPosition - 1) : null;

                    if (question.getCorrectAnswer() != mSelectedOptionPosition) {
                        this.answerView(mSelectedOptionPosition,R.drawable.wrong1_option_border_bg);
                    } else {
                        mCorrectAnswers++;
                    }

                    this.answerView(question.getCorrectAnswer(), R.drawable.correct1_option_border_bg);



                    if (mCurrentPosition == mQuestionList.size()) {


                        ((Button)findViewById(R.id.btn_submit)).setText("Finish");
                    } else {

                        ( (Button)findViewById(R.id.btn_submit)).setText("Go to Next Question");
                    }

                 mSelectedOptionPosition = 0;
                }
            }
        }

    }
}

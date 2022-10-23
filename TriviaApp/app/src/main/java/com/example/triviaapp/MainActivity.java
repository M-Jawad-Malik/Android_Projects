package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaapp.data.AnswerListAsyncResponse;
import com.example.triviaapp.data.QuestionBank;
import com.example.triviaapp.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView questionTxtVw,questionCounterTxtVw,currentScore,highestScore,currentIndex,noofQuestionAttempted;
    Button false_btn,true_btn;
    ImageButton nxt_btn,pre_btn;
    int currentQuestionIndex=0;
    int score,noOfQuestionAnswered;
    boolean isAnswered;
    private List<Question> questionList;
    private final String Message_ID="score_pref";
    String highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuestionBank questionBank=new QuestionBank();
        questionTxtVw=findViewById(R.id.questionTxtVw);
        questionCounterTxtVw=findViewById(R.id.questionCounterTxtVw);
        nxt_btn=findViewById(R.id.nxt_btn);
        pre_btn=findViewById(R.id.pre_btn);
        false_btn=findViewById(R.id.false_btn);
        true_btn=findViewById(R.id.true_btn);
        currentScore=findViewById(R.id.currentScore);
        highestScore=findViewById(R.id.higestScore);
        nxt_btn.setOnClickListener(this);
        pre_btn.setOnClickListener(this);
        false_btn.setOnClickListener(this);
        true_btn.setOnClickListener(this);
        currentIndex=findViewById(R.id.currentIndex);
        noofQuestionAttempted=findViewById(R.id.noofQuestionAnswered);
        score=0;
        noOfQuestionAnswered=0;
        isAnswered=false;
        SharedPreferences sharedPreferences=getSharedPreferences(Message_ID,MODE_PRIVATE);
        highScore=sharedPreferences.getString("highScore","0");
        highestScore.setText("Highest Score: "+highScore);
        questionList=questionBank.getQuestions(new AnswerListAsyncResponse() {
           @Override
           public void processFinished(ArrayList<Question> questionArrayList) {
              // Log.d("Main","response"+ questionArrayList.get(0).getAnswer());
           questionTxtVw.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
           questionCounterTxtVw.setText((currentQuestionIndex+1)+" / "+ questionList.size());
           }
       });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.nxt_btn:
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                currentIndex.setText("Current Index: "+currentQuestionIndex);
                updateQuestion();
                if(currentQuestionIndex==noOfQuestionAnswered) {
                    isAnswered = false;
                }else {
                    isAnswered=true;
                }
                break;
            case R.id.pre_btn:
                if(currentQuestionIndex>0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    currentIndex.setText("Current Index: "+currentQuestionIndex);
                    updateQuestion();
                    if(currentQuestionIndex==noOfQuestionAnswered) {
                        isAnswered = false;
                    }else {
                        isAnswered=true;
                    }
                }
                break;
            case R.id.false_btn:
                if(!isAnswered) {
                    noOfQuestionAnswered++;
                    noofQuestionAttempted.setText("No of Questions Attempted: "+noOfQuestionAnswered);
                    isAnswered = true;
                    checkAnswer(false);
                    updateQuestion();
                }else if(currentQuestionIndex>noOfQuestionAnswered)
                {
                    Toast.makeText(this, "Answer the question no: "+ (noOfQuestionAnswered+1) +" at First! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Already Answered", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.true_btn:
                if(!isAnswered) {
                    noOfQuestionAnswered++;
                    noofQuestionAttempted.setText("No of Questions Attempted: "+noOfQuestionAnswered);
                    isAnswered = true;
                    checkAnswer(true);
                    updateQuestion();
                }else if(currentQuestionIndex>noOfQuestionAnswered)
                {
                    Toast.makeText(this, "Answer the question no: "+ (noOfQuestionAnswered+1) +" at First! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Already Answered", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences=getSharedPreferences(Message_ID,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
       // Toast.makeText(getApplicationContext(),Integer.valueOf(score).toString(),Toast.LENGTH_LONG).show();
       Log.d("Score",String.valueOf(score));
       Log.d("HighScore",highScore);
            if(Integer.valueOf(highScore)<score) {
                //Toast.makeText(getApplicationContext(),Integer.valueOf(highScore).toString(),Toast.LENGTH_LONG).show();
                editor.putString("highScore", String.valueOf(score));
            }

        editor.apply();

    }

    private void checkAnswer(boolean b) {
        boolean answerIsTrue=questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId=0;
        if(answerIsTrue==b)
        {
            fadeView();
            toastMessageId=R.string.correctAnswer;
            score++;
            currentScore.setText("Score: "+score);
        }else{
            shakeAnimation();
            toastMessageId=R.string.wrongAnswer;
            score--;
            currentScore.setText("Score: "+score);
        }
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        questionTxtVw.setText(questionList.get(currentQuestionIndex).getAnswer());
        questionCounterTxtVw.setText((currentQuestionIndex+1)+" / "+ questionList.size());
    }
    private void shakeAnimation()
    {
        Animation shake= AnimationUtils.loadAnimation(this,R.anim.shake_animation);
        CardView cardView=findViewById(R.id.cardvw);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void fadeView()
    {CardView cardView=findViewById(R.id.cardvw);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
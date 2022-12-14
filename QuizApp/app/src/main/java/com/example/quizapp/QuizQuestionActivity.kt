package com.example.quizapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*
import java.lang.reflect.Type
import kotlin.math.log

class QuizQuestionActivity : AppCompatActivity(),View.OnClickListener {
    private var mCurrentPosition:Int=1
    private var mQuestionList:ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int=0
    private var mCorrectAnswers:Int=0
    private var mUserName:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        mUserName=intent.getStringExtra(Constants.USER_NAME)
        mQuestionList=Constants.getQuestions()
        setQuestion()
        tv_option_one.setOnClickListener(this,)
        tv_option_two.setOnClickListener(this,)
        tv_option_three.setOnClickListener(this,)
        tv_option_four.setOnClickListener(this,)
        btn_submit.setOnClickListener(this,)

    }
    fun setQuestion()
    {
        defualtOptionView()
        val quesiton=mQuestionList!![mCurrentPosition-1]
        progressBar.progress=mCurrentPosition
        tv_progress.text="$mCurrentPosition/"+progressBar.max
        tv_question.text=quesiton.question
        tv_image.setImageResource(quesiton.image)
        tv_option_one.text=quesiton.optionOne
        tv_option_two.text=quesiton.optionTwo
        tv_option_three.text=quesiton.optionThree
        tv_option_four.text=quesiton.optionFour
        if(mCurrentPosition==mQuestionList!!.size)
        {
            btn_submit.text="Finish"
        }else{
            btn_submit.text="SUBMIT"
        }
    }

    private fun defualtOptionView(){

        val options = ArrayList<TextView>()
        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2,tv_option_three)
        options.add(3,tv_option_four)
        for (option in options)
        {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT //default appearance of text
            option.background=ContextCompat.getDrawable(
                    this,
                    R.drawable.default_option_border_bg
            )
        }
    }
    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defualtOptionView()
        mSelectedOptionPosition=selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD) //default appearance of text
        tv.background=ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_border_bg
        )
    }
    private fun answerView(answer:Int,drawable:Int)
    {
        when(answer)
        {
            1-> {
                tv_option_one.background=ContextCompat.getDrawable(this,drawable)
            }
            2-> {
                tv_option_two.background=ContextCompat.getDrawable(this,drawable)
            }
            3-> {
                tv_option_three.background=ContextCompat.getDrawable(this,drawable)
            }
            4-> {
                tv_option_four.background=ContextCompat.getDrawable(this,drawable)
            }
        }
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.tv_option_one->{
                selectedOptionView(tv_option_one,1)
            }
            R.id.tv_option_two->{
                selectedOptionView(tv_option_two,2)
            }
            R.id.tv_option_three->{
                selectedOptionView(tv_option_three,3)
            }
            R.id.tv_option_four-> {
                selectedOptionView(tv_option_four, 4)
            }
            R.id.btn_submit-> {

                if(mSelectedOptionPosition==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition<=mQuestionList!!.size->{
                            setQuestion()
                        }
                        else->{
                            Toast.makeText(this@QuizQuestionActivity,"You have Successfully Completed Quiz",Toast.LENGTH_SHORT).show()
                            val intent=Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_Questions,mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                            }

                        }

                }
                else{
                    val question=mQuestionList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer !=mSelectedOptionPosition)
                    {
                        answerView(mSelectedOptionPosition,R.drawable.wrong1_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question!!.correctAnswer,R.drawable.correct1_option_border_bg)
                    if(mCurrentPosition==mQuestionList!!.size)
                    {
                        btn_submit.text="Finish"
                    }else{
                        btn_submit.text="Go to Next Question"
                    }
                    mSelectedOptionPosition=0

                }

            }
            }
        }
    }




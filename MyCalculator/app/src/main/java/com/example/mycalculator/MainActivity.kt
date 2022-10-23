package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var lastNumeric:Boolean=true
    var lastDot=true
    var arithmeticoperator=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view:View)
    {
        when((view as Button).text)
        {
            "="->{
                if(lastNumeric&&arithmeticoperator){
                var tvValue=tvInput.text.toString()
                var prefix=""
                try{
                    if(tvValue.startsWith("+"))
                    {
                        prefix="+"
                        tvValue=tvValue.substring(1)
                    }else if(tvValue.startsWith("-"))
                    {
                        prefix="-"
                        tvValue=tvValue.substring(1)
                    }else if(tvValue.startsWith("x"))
                    {
                        prefix="x"
                        tvValue=tvValue.substring(1)
                    }else if(tvValue.startsWith("/"))
                    {
                        prefix="/"
                        tvValue=tvValue.substring(1)
                    }
                    //x
                    if(tvValue.contains("+"))
                    {
                        var splitValue=tvValue.split("+")
                        var str=splitValue[0]
                        if(!prefix.isEmpty())
                        {
                            str = prefix+str
                        }
                            tvInput.text=removeZeroAfterDot((str.toDouble()+splitValue[1].toDouble()).toString())
                    }else  if(tvValue.contains("-"))
                    {
                        var splitValue=tvValue.split("-")
                        var str=splitValue[0]
                        if(!prefix.isEmpty())
                        {
                            str = prefix+str
                        }
                        tvInput.text=removeZeroAfterDot((str.toDouble()-splitValue[1].toDouble()).toString())
                    }else  if(tvValue.contains("x"))
                    {
                        var splitValue=tvValue.split("x")
                        var str=splitValue[0]
                        if(!prefix.isEmpty())
                        {
                            str = prefix+str
                        }
                        tvInput.text=removeZeroAfterDot((str.toDouble()*splitValue[1].toDouble()).toString())
                    }else  if(tvValue.contains("/"))
                    {
                        var splitValue=tvValue.split("/")
                        var str=splitValue[0]
                        if(!prefix.isEmpty())
                        {
                            str = prefix+str
                        }
                        tvInput.text=removeZeroAfterDot((str.toDouble()/splitValue[1].toDouble()).toString())
                    }


                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
                    arithmeticoperator=false
            }}
            in "+","-","/","x"->{
                if(!arithmeticoperator){
                tvInput.append((view as Button).text)
                arithmeticoperator=true}}
            "CLR"->{tvInput.setText("")
                lastNumeric=true
                lastDot=true
                arithmeticoperator=false
                    }
            "."->if(lastNumeric && !lastDot)  { tvInput.append(".")
                    lastNumeric=false
                    lastDot=true}
            else->{
                tvInput.append((view as Button).text)
                    lastDot=false

        }
        }

    }
    private fun removeZeroAfterDot(result:String):String{
       var value=result
        if(result.contains(".0"))
        {
            value=result.substring(0,result.length-2)

        }
        return value
    }


}
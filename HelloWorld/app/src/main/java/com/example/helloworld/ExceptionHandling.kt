package com.example.helloworld

import android.icu.number.IntegerWidth
import java.lang.ArithmeticException
import java.lang.NumberFormatException
import kotlin.reflect.typeOf

//Exception: runtime problems leads to program termination
/*
    running of memory
    array out of bound
    condition like divided by zero
    Exception handling is process of maintaing runtime errors and maintain flow of program.
    Throwable class: is used own time of exception
    Keywords in exeption handling
    1. try
         contains statements that might throw exception
         must be followed by catch or finally
         e.g connecting to internet
    2. catch
        catches the exception thorwn by the try block
    3. finally
    it is always executed if exception is handled or not :e.g used for buffer closing
    4. Throw
    allow us to throw exception explicitly: for own exception, for testing purposes
* */
   /*
   Unchecked Exeption: Due to error in code
    Checked at runtime
   Examples:
   Runtime Exception
   Arithmetic Exception
   ArrayIndexOutOfBoundException
   SecurityExecption: Security Violation
   NullPointerException:
   NumberFormatException
   Checked Exception
   checked at compile time
   IOException
   * */
    /*
    We can use nested try blocks
    try{
    try{}catch(){}
    }
    catch(){}
    * */
fun main()
{
val str= getNumber("10")
    println(str)
    validateAge(17)
}
val getNumber:(String)->Int={str:String->
    try{
    Integer.parseInt(str)

      }
catch (e:NumberFormatException)
{println(e)
    0

}
    finally {
        println("Always Executes")
    }
    //we can use multiple catches with single try
}
fun validateAge(age:Int){
    if(age<18)
        throw ArithmeticException("Under age")
    else
        println("Eligible to Drive")
}
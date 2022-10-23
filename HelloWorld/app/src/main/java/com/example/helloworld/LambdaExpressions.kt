package com.example.helloworld
/*Lambda Function : Function that has no name
  Functions that are not declared but passed as an expression
  Are anonymous function
  is defined with curly brackets which takes variables as parameter and body of a function
  Body of function is written after parameter if any follwed by -> operator
  Syntax: {varaible->body of Lambda}
  */
val sum2={a:Int,b:Int-> println("Sum of a and b : ${a+b}")}
fun main()
{   // Addition of two numbers
    val sum:(Int,Int)->Int={a:Int,b:Int->a+b}
    //More short method
    val sum1={a:Int,b:Int->a+b}
    println(sum1(1,5))
    sum2(6,7)
}
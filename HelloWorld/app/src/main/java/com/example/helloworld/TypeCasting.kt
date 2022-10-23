package com.example.helloworld

import kotlin.math.floor

fun main()
{
    val stringList: List<String> = listOf("Ammar","Jawad","Meesam","Ali","Awais")
    val mixedTypeList:List<Any> = listOf("Jawad",31,5,"Bday",70.5,"weights","kg")
    for (value in mixedTypeList)
    {
        when(value)
        {
            is Int-> println("Integer: '$value'")
            is Double-> println("Double: '$value' with floor value ${floor(value)}")
            is String-> println("String: '$value' of Length ${value.length}")
            else-> println("Unknown Type")
        }
    }
    //Smart Cast
    val obj1:Any="I have a dream"
    if(obj1 !is String)
    {
        println("It is not a string")
    }
    else
    {
        println("it is string of Length: ${obj1.length}")
        }
    //Explicit (unsafe) Casting using the "as" keyword - can go wrong
    val str1:String=obj1 as String
    println(str1.length)
//    val obj2:Any=1337
//    val str2:String=obj2 as String
//    println(str2.length)
    //Explicit (safe) Casting using the "as?" keyword
    var obj3:Any=1377
    val str3:String?=obj3 as? String
    println(str3)
}
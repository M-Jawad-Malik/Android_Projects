package com.example.helloworld
fun main()
{

    //Unsafe Cast
   /*
   Problem:
   val obj:Any?=null
    val str:String=obj as String
    val obj1:Any?=111
    val str1:String=obj1 as String //ClassCast Exception*/
    /*
    * resolving Issue using Unsafe Cast
      val obj:Any?="Unsafe Casst"
      val obj:String?=obj as String?
    * */

    //Safe Cast: for safe casting safe cast operator (as?) with nullable varaible is used
    val location:Any="Kotlin"
    val safeString:String?=location as? String
    val safeInt:Int? =location as? Int
    println(safeString+safeInt)


}
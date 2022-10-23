package com.example.helloworld

fun main()
{
    //type inference: find out type of variable from its value
    var myName="Jawad" //var is mutable variable means i can reassigned its value at any stage in program.

    myName="Jawad!"

    val myname="My" //val is immutable variable means i can not change its value once assigned

    //print("Hello "+myname+" "+myName+"\n")

// TODO: tHIS IS TO DO TASK
    // TODO: // This is TODO element used to remark remaining work todo
    // TODO: //
    /*
    Is used for multiline comment
     */

    /* In kotlin, we have not to assign datatype we can use var and val if we not want to specify datatype but in that
    case we have to assign it a value at the declaration time.
    Data type is assigned to variable when we may want not assigning it value at declaration time.
     */

    // type string
    var str: String="Hello"
    //type byte 8 bit -128---127
    val myByte: Byte=13
    //type Short 16 bits
    val myShort: Short=125 //-32768	32767
    //type Int 32 bits
    val myInt: Int=12313131
    // type Long 64 bits
    val myLong: Long=32_45_65_12_000

    //Floating point number types: Float(32 bits) && Double(64 bits)
    //We have to write F at end of number else it will consider decimal number a Double Datatype number
    val myFloat: Float=13.37F
    val myDouble: Double=13.11111111111111111

    //Boolean type
    var isSunny: Boolean=true
    isSunny=false
    //Char type
    var letterChar='A'
    val digitChar='&'
    //Strig type : connection of multiple chars
    val myStr:String="Hello World"
    var firstCharInStr=myStr[0]
    var lastCharInStr=myStr[myStr.length-1]
    //print("First Character: "+firstCharInStr+"\n")
    //print("Last Character: "+lastCharInStr+"\n")

    //Operator (+,-,*,/,%)
    var result=8+3
    result /= 2
    //print(result)
    result *= 2
    //print(result)
    result -= 2
    //print(result)
    result %= 2
    //print(result)
    val a=5.0 //Double
    val b=3  //Integer
    result=(a/b).toInt()
    //print(result)

    //Comparison Operators (==,!=,<,>,<=,>=)
    val isEqual= 4==5

    ////String Interpolation: Binding Variable or values with print statement
    //we can use $ sign in statement with other line and don need to have concatenation symbol to be use
    println("word $isEqual is:")

    //if we have not declared we can bind calculaton in print staemenet as follow
    //println("isNotEqual is: ${5!=3}")
    //println automatically prints endl at the end


    //Assignment Operators (+=,-=,*=,/=,%=)
    var mynm=5
    mynm += 5
    //println("mynmis: $mynm")
    mynm -= 6
    //println("mynm is: $mynm")
    mynm*=7
    //println("mynm is: $mynm")
    mynm/=3
    //println("mynm is: $mynm")
    mynm%=2
    //println("mynm is: $mynm")

    //Increament && Deceament Operators (__++,++__,__--,--__)
    //if we use ++ after variable then it first prints the older value of mynm and then increamnet it
    //println("mynm is: ${mynm++}")
    //if we use ++ before variable then it firstly increament the value of variable and then it prints new value
    //println("mynm is: ${++mynm}")
    //println("mynm is: ${--mynm}")

    var heightPerson1=170
    var heightPerson2=189
    //Control Flow
    if(heightPerson1>heightPerson2)
    {
      //println("use raw force")
    }
    else if(heightPerson1==heightPerson2)
    {
        //println("use power technique 1337")
    }
    else
    {
        //println("use technique")
    }


    //Chellenge Block
    var Age=120
    if(Age>=21)
    {
        //println("You can Drink")
    }
    else if(Age>=18){
        //println("You can Vote")
    }

    else if(Age>=16&&Age<18){
        //println("you can Drive")
    }
    else{
        //println("You are too young")
    }

    var name="Jawad"
    if(name=="Jawad")
    {
        //println("Welcome Home: $name")
    }
    else{
       // println("Who are You!")
    }
    when(name){
        "Jawad"->println("Welcome Home: $name")
        else->println("Who are You!")
    }


    var isRainy=true
    if(isRainy)
    {
    //println("it's Rainy")
    }
    else
    {
       // println("it's not rainy")
    }

    //When keyword is just like switch statement
    var season=3
//    when(season){
//        1-> println("Spring")
//        2-> println("Summer")
//        3->{
//            println("Fall")
//            println("Autum")
//        }
//        4-> println("Winter")
//        else-> println("Invalid Season")
//    }
    var month=6
//    when(month){
//         in 3..5-> println("Spring")
//         in 6..8-> println("Summer")
//         in 9..11-> println("Fall")
//         12,1,2-> println("Winter")
//        else-> println("Invalid Season")
//
//    }
//    when(Age){
//        //!in 0..20
//        in 21..100-> println("You may drink in US")
//        in 18..20-> println("You can Vote now")
//        16,17-> println("You can Drive Now")
//        else-> println("You're too young")
//    }

    var x=13.37F

//    when(x){
//        is Byte-> println("$x is a Byte")
//        is Short-> println("$x is a Short")
//        is Int -> println("$x is an Int")
//        is Long-> println("$x is of type Long")
//        is Double-> println("$x is of type Double")
//        is Float-> println("$x is of type Float")
//        is String-> println("$x is of type String")
//        is Char-> println("$x is of type Char")
//        else -> println("$x is None of the Type")
//    }
    //While Loop
    var y=1
    while(y<=10)
    {
       // println("$y")
        y++
    }

    //Do While is used when we have to first established connection to a server and like this applications
    do{
       // println("$y")
        y++
    }while (y<=15)

    var feltTemp="cold"
    var roomTemp=10
    while (feltTemp=="cold"){
        roomTemp++
        if(roomTemp>=20)
        {
            feltTemp="compfy"
           // println("Its compfy now")
        }
    }

    //For Loop
    for (num in 10 downTo 1){
            print("$num ")
    }
    println()
    for(num in 1..10)
    {
        print("$num ")
    }
    println()
    for(i in 1 until 12)
    {
        print("$i ")
    }
    println()
    for (i in 1 until 10 step 2)
    {
        print("$i ")
    }
    println()
    //Function: Dont have to specify type of function if no thing is to be return
    //All Function either built in or user built are italic when called
    // A Function is called Method when it is defined within a class
    myFunction()
    println()
    //We are passing Arguments
    var res=addUp(1,2)
   // println("result is: $res")
    var num= avgNum(3.0,4.0)
 //   println("Average is: $num")

    //Name=null ->Compilaton Error
    //nullable string are used when we want that varaible can have null values
    var nullName:String?="Jawad"
    //nullName=null
    var len=name.length
    //length error in null string
    var len2=nullName?.length
    //Only works when nullvaraible has not null value
    //println(nullName?.toLowerCase())
    nullName?.let { println(it.length) }

    //?: is called Elvis Operator is used like if that otherwise that
    var Name=nullName?:"Guest"
    println("$Name")
    //Null assertion operator only use when certain there will be value in variable
    nullName!!.toLowerCase()
    //Chain Safe Call


}
fun myFunction()
{
  //  print("Called from myfunction")
}

//Parameter (Input)
fun addUp(a:Int,b:Int):Int{
    return a+b
}
fun avgNum(a:Double,b:Double):Double{
    return (a+b)/2
}

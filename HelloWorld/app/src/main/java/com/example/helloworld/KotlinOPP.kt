package com.example.helloworld

import android.app.backup.BackupAgent
import java.lang.IllegalArgumentException

//Data class must have at least one parameter   
data class User(val id:Int,var name:String)
data class Login(var usrnm:String,var email:String)
fun main()
{

    //Data Class Pracatice
    var user1=User(1, "Jawad")
    println(user1.name)
    user1.name="Hashir"
    var user2=User(1,"Malik")
    user2.name="Hashir"
    println(user1.equals(user2))
    println("User Detail: $user1")
    val updatedUser=user1.copy(id=2,name = "Malik")
    println("Updated User:$updatedUser and Previous User:$user1")
    println(updatedUser.component1())
    println(updatedUser.component2())
    //Assign variable a value from object autmatically
    val (id,name)=updatedUser
    var loginuser=Login("Jawad","jawaddmuhammad@gmail.com")
    val loginuser1=loginuser.copy()
    println(loginuser1.component1())
    val email=loginuser1.component2()
    println(email)
    //Data Class Paractice

    //Class with arguments must be passed
    var classObj = Person("Muhammad","Jawad",31)
    //Class with default argument
    var classObj1=Person1()
    //Class with default argument but can change default values
    var classObj2=Person2(lastName = "Haroon")
    myFunction(8)
    classObj.hobby="Play Cricket"
    classObj.stateHobby()
    var Jawad=Person("Malik","Jawad",21)
    Jawad.hobby="To write Program"
    Jawad.stateHobby()
    var myCar=Car()
    println(myCar.myBrand)
    myCar.maxSpeed=5
    println("MaxSpeed is: ${myCar.maxSpeed}")
}
class Car{
    //lateint is used to bound a variable to be initilized before it can be used and set it to not nullable
    lateinit var owner:String
    val myBrand:String="BMW"
        //Custom getter
    get(){

        return field.toLowerCase()
    }
    //field help to refer properties in getter and setter funct
    var maxSpeed:Int=250
    get()=field
    set(value){
        field=if(value>0) value else throw IllegalArgumentException("MaxSpeed cannot be less than zero")}


    var myModel:String="M5"
    private set

    init {
        this.owner="Jawad"
    }
}


    class Person(firstName:String,lastName: String)// this is primary constructor, number of parameters decide which constructor is to be used
{
        //Member Variables: Properties
        var age:Int?=null
        var hobby:String="watch Netflix"
        var firstName:String?=null
        //Initializer Block
        init {
            println("Initialized a new Person Object with " +
                    "fisrtName =$firstName and lastName = $lastName")

        }
        //Member Secondary Constructor
        constructor(firstName: String,lastName: String,age:Int):this(firstName,lastName)
        {
            this.age=age;
            this.firstName=firstName
        }
        //Member Functions --Methods
        fun stateHobby(){
            println("$firstName\'s hobby is $hobby")
        }
    }
    class  Person1(firstName: String="Malik",lastName: String="Jawad")
    {
        init {
            println("Initialized a new Person Object with"+
            "firstName =$firstName and lastName=$lastName")
        }
    }
    class Person2(firstName: String="Malik",lastName: String="Hashir")
    {
        init {
            println("Initialized a new Person Object with"+
            "firstName=$firstName and lastName=$lastName")
        }
    }
    fun myFunction(a:Int){
        //we cannot assign value to parameter within a fuction like a=5 is not workable here
        //when we decalre a variable with same name as parameter then parameter is not more workable and this is called shadowing
        //Like i declare a variable var a=5 if i passed 6 to function but have assigned var a=5 then a=5 will wor
        var b:Int=a
        println("b is: $b and a is: $a")

    }
class animal(name:String,specie:String)
{
    lateinit var name:String;
    lateinit var specie: String;
    lateinit var area: String;
    constructor(name: String,specie: String,area: String):this(name,specie){
        this.area=area;
        this.name=name;
        this.specie=specie;
    }
    init {
        this.name="Jawad";
    }

}

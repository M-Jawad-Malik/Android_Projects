package com.example.helloworld

import android.icu.number.NumberFormatter
import java.time.temporal.TemporalAmount

// The class that inherits the features of other class is called sub or child class or Derived class
// The class whose features are being inherited are called super , base or parent class
//To make a class inheritable we have to use open keyword with it.

//oopen class Vehicle //Parent, Super, Base
//{
//        //Properties
//        //Methods
//
//}
//to bound a class not to be able to inherited use sealed word before open
//To override a variable or method of super class in subclass we have to make this variable open in super class
open class Audi(override val maxSpeed: Double,val name:String, val brand:String) :Drivable//Sub Class for Vehicle  //Parent, Super, Base class of Vehicle
{
    open var range:Double=0.0
    fun extendRange(amount:Double){
        if(amount>0)
        {
            range+=amount
        }
    }

    override fun drive(): String = "Driving the Interface drive"

    open fun  drive(distance:Double)
    {
        println("Drove for distance $distance KM")
    }
}

class ElectricCar(maxSpeed: Double,name: String, brand: String,batteryLife:Double) :Audi(maxSpeed,name, brand)//Sub, Child, Derived Class of Car
{
    var chargerType="Type1"
    override var range=batteryLife*6
    //We can overide property and method of a class
    override fun drive(distance: Double) {
        println("Drove for distance $distance KM on electricity")
    }
    //We can declare a specific function with same name in subclass but with different signature
     override fun drive():String ="Drove for distance $range KM on electricity"
    override fun brake() {
        super.brake()
        println("Brake Inside of Electric Car")
    }
}
//Any class inherits from any class in kotlin
//Every class in kotlin has any class as super class
fun main()
{
    var myCar=Audi(200.0,"A3","Audi")
    var myECar=ElectricCar(200.0,"S-Model","Tesla",85.0)
    myCar.extendRange(200.0)
    myECar.drive()
    //POlymorphism: Different classes with same traits can be implemented differently
    myCar.drive(200.0)
    myECar.drive(200.0)
    myECar.brake()


    val human= Human("Denis","Russia",70.0,28.0)
    val elephant=Elephant("Rosy","India",5400.0,25.0)
    human.run()
    elephant.run()
    human.breath()
    elephant.breath()

}

//Interfaces is a feature that allow us to extend functionality of a class
//May provide functionality for by default some or all of its properties to classes
//If a class inherit, extend an interface it need to implement that interface in it
//We cannot create object of interfaces

interface Drivable{
    val maxSpeed:Double
    fun drive():String
    fun brake(){
        println("The Drivable is braking")
    }
}
/*
    Difference between interfaces and abstract class is that we can inherit one or more abstract in one subclass but
    we can inherit from only one abstract class
    Abstract donot have constructor
    While Abstract like other classes have constructor and fields(getter& setter)
*/

//Abstract class cannot be instantiated means you cannot create object of that class
//But we can inherit subclasses from abstract classes
//The members (properties&&methods) of abstract class are non-abstract
//unless you explicitly use the abstract keyword to make them abstract
//Abstract cannot be used in main class this is mainly used for security
abstract class Mammal(private val name: String,private val origin:String,private val weight:Double)
{
    //abstract prpperty must be overridden by its subclasses
    abstract var maxSpeed:Double

    //Abstract Methods (Must be implemented by subclasses)
    abstract fun run()
    abstract fun breath()

    //(Concrete) Non-abstract Method
    fun displayDetails(){
        println("Name $name, Origin: $origin, Weight: $weight, Max Speed: $maxSpeed")
    }
}
class Human(name: String,origin: String,weight: Double,override var maxSpeed: Double):Mammal(name,origin,weight)
{
    override fun run() {
        println("Runs on two legs")
    }

    override fun breath() {
        println("Breath throgh nose")
    }
}
class Elephant(name: String,origin: String,weight: Double,override var maxSpeed: Double):Mammal(name,origin,weight)
{
    override fun run() {
        println("Runs on four legs")
    }

    override fun breath() {
        println("Breath through the trunk")
    }
}
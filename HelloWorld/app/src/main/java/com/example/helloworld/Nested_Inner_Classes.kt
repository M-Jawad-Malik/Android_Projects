package com.example.helloworld
/*
 Nested Class: A class which is created inside another class
 is by default static: its data members can be created woth ceating object of that class
 cannot access data members of outer classes
 Syntax:
 class outer{
   class nestedClass{
                        } }
 Inner Class:  A class which is created inside another class with keyword inner
 can access data members of its outer class
 Syntax:
 class outer{
   inner class innerClass{
                        } }

* */
fun main()
{
var obj=outerClass.nestedClass()
    obj.foo()
    var obj1=outerClass1().nestedClass1()
    obj1.foo()
}
class outerClass{
    private var name:String="Mr. Ahmad"
    class nestedClass{
        var description:String="HEllo"
        private var id:Int=101
        fun foo(){
           // print("name is ${name}") cannot access member of outer class
            println("id is $id")
        }
    }

}

class outerClass1{
    private var name:String="Mr. Ahmad"
    inner class nestedClass1{
        var description:String="HEllo"
        private var id:Int=101
        fun foo(){
             print("name is ${name}") //can access member of outer class
            println("id is $id")
        }
    }

}

package com.example.helloworld
/*In this section i have learnt
    1. How to create empty array list i.e val arrayList=ArrayList<String>()
    2. How to add elements to array list i.e. arrayList.add()
    3. How to print elements of Array List i.e. for(i in arrayList){print(i)}
    4. How to create arrayList with capacity i.e. val arrayList:ArrayList<String>=ArrayList<String>(5)
    5. How to create mutable list and add it as collection of list to arrayList i.e. arrayList.addall(mutablelist)
    6. How to access specific element of arrayList i.e. arrayList.get(index)
    7. How to create iterator of aarrayList i.e. val itr=arrayList.iterator()
    8. How to print iterator using while loop i.e while(itr.hasNext){println(itr.next)}
    9.How to remove specific element and how to clear all elements of array list
*/
fun main()
{
    //Use to create dynamic list
    //array class provide both read and write functionalities
    //Follows the sequence of insertion order
    //May contain duplicate elements and is not synchronized


    val arrayList=ArrayList<String> () //Use to create Empty Array List
    arrayList.add("Jawad") //Use to add object in arrayList
    arrayList.add("Muhammad")
    //println("Elements of Array list")
    for (i in arrayList)
    {
       // println(i)
    }

    //ArrayList using collection
    val arraylist:ArrayList<String> = ArrayList<String>(5) //Can Limit capcity of Array
    val list:MutableList<String> = mutableListOf<String>()
    list.add("Muhammad")
    list.add("Jawad")
    arraylist.addAll(list)
    println("Elements of Array list")
    arraylist.add("Muhammad")
    for (i in arraylist)
    {
         println(i)
    }

    val itr=arraylist.iterator()
    while (itr.hasNext()){
        println(itr.next())
    }
    println("Size of arrayList: ${arrayList.size}")
    println(arrayList.get(0))
    arrayList.remove("Jawad")
    println(arrayList)
    arrayList.clear()


}
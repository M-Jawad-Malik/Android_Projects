package com.example.resortingapp;

import java.util.ArrayList;
import java.util.List;

public class SeasonList {

    public static List<String> getList(){
        List<String> seasonList = new ArrayList<>();
        seasonList.add("All Seasons");
        seasonList.add("Summer");
        seasonList.add("Winter");
        seasonList.add("Autumn");
        seasonList.add("Spring");
        return seasonList;
    }
}

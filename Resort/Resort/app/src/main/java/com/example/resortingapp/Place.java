package com.example.resortingapp;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {

    public List<ActivityClass> activities;
    public int airQuality;
    public String categoryAR;
    public String categoryEN;
    public String categoryUR;
    public Cost cost;
    public String descAR;//description
    public String descEN;
    public String descUR;
    public String imageURL;
    public double latitude;
    public double longitude;
    public String nameAR;
    public String nameEN;
    public String nameUR;
    public String recommendedAge;
    public int recommendedSeason;
    public int recommendedTime;
    public String keywords;

    public Place(List<ActivityClass> activities, int airQuality, String categoryAR, String categoryEN, String categoryUR, Cost cost, String descAR, String descEN, String descUR, String imageURL, double latitude, double longitude, String nameAR, String nameEN, String nameUR, String recommendedAge, int recommendedSeason, int recommendedTime, String keywords) {
        this.airQuality = airQuality;
        this.categoryAR = categoryAR;
        this.categoryEN = categoryEN;
        this.cost = cost;
        this.descAR = descAR;
        this.descEN = descEN;
        this.imageURL = imageURL;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.recommendedAge = recommendedAge;
        this.recommendedSeason = recommendedSeason;
        this.recommendedTime = recommendedTime;
        this.keywords = keywords;
        this.activities = activities;
        this.categoryUR=categoryUR;
        this.descUR=descUR;
        this.nameUR=nameUR;
    }

    public Place() { }

}

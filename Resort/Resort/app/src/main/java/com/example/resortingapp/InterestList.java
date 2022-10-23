package com.example.resortingapp;


import java.util.ArrayList;
import java.util.List;

import resortingapp.R;

public class InterestList {

	public static List<Interest> getList(boolean all){

		List<Interest> categoryList = new ArrayList<>();

		if (all) {
			categoryList.add(new Interest(R.drawable.ic_resorting1, "All Interest"));
		}

		categoryList.add(new Interest(R.drawable.bird, "Swimming"));
		categoryList.add(new Interest(R.drawable.care, "Cycling"));
		categoryList.add(new Interest(R.drawable.food, "Running"));
		categoryList.add(new Interest(R.drawable.food, "Reading"));
		return categoryList;
	}

}

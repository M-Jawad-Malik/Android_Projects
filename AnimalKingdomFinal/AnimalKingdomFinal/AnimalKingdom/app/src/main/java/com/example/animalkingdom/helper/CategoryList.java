package com.example.animalkingdom.helper;

import com.example.animalkingdom.R;
import com.example.animalkingdom.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {

	public static List<Category> getList(boolean all){

		List<Category> categoryList = new ArrayList<>();

		if (all) {
			categoryList.add(new Category(R.drawable.petslogo, ""));
		}

		categoryList.add(new Category(R.drawable.bird, "Birds"));
		categoryList.add(new Category(R.drawable.animal, "Animals"));
		categoryList.add(new Category(R.drawable.food, "Food"));
		return categoryList;
	}

}

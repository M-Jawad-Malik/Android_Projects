package com.example.tris.Model;
import com.example.tris.R;

import java.util.ArrayList;
import java.util.List;

public class EventCategoryList {
	public static List<Category> getList(){
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category(R.drawable.	ic_glass	,"General"));
		categoryList.add(new Category(R.drawable.	ic_food	,"A speaker session"));
		categoryList.add(new Category(R.drawable.	ic_garments	,"Networking sessions"));
		categoryList.add(new Category(R.drawable.	ic_accessories	,"Conferences"));
		categoryList.add(new Category(R.drawable.	ic_households	,"A seminar or half-day event"));
		categoryList.add(new Category(R.drawable.	ic_crockery	,"Workshops and classes"));
		categoryList.add(new Category(R.drawable.	ic_jewellery	,"VIP experiences"));
		categoryList.add(new Category(R.drawable.	ic_cosmetics	,"Sponsorships"));
		categoryList.add(new Category(R.drawable.	ic_furniture	,"Trade shows and expos"));
		return categoryList;
	}
}

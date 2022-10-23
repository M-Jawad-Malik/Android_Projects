package com.example.tris.ads.model;
import com.example.tris.R;
import com.example.tris.ads.model.Category;
import java.util.ArrayList;
import java.util.List;
public class AdsCategoryList {
	public static List<Category> getList(){
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category(R.drawable.ic_food	,"General"));
		categoryList.add(new Category(R.drawable.ic_food	,"Food"));
		categoryList.add(new Category(R.drawable.ic_garments	,"Garments"));
		categoryList.add(new Category(R.drawable.ic_accessories	,"Accessories"));
		categoryList.add(new Category(R.drawable.	ic_households	,"Households"));
		categoryList.add(new Category(R.drawable.	ic_crockery	,"Crockery"));
		categoryList.add(new Category(R.drawable.	ic_jewellery	,"Jewelry"));
		categoryList.add(new Category(R.drawable.	ic_cosmetics	,"Cosmetics"));
		categoryList.add(new Category(R.drawable.	ic_furniture	,"Furniture"));
		categoryList.add(new Category(R.drawable.	ic_menswear	,"Man’s Wear"));
		categoryList.add(new Category(R.drawable.	ic_womenswear	,"Women’s Wear"));
		categoryList.add(new Category(R.drawable.	ic_decoration	,"Decoration"));
		categoryList.add(new Category(R.drawable.	ic_sports	,"Sports"));
		categoryList.add(new Category(R.drawable.	ic_electronics	,"Electronics"));
		categoryList.add(new Category(R.drawable.	ic_plants	,"Plants"));
		categoryList.add(new Category(R.drawable.	ic_gym	,"Jim"));
		categoryList.add(new Category(R.drawable.	ic_saloon	,"Saloon"));
		categoryList.add(new Category(R.drawable.	ic_medicines	,"Medicines"));
		categoryList.add(new Category(R.drawable.	ic_vegatables	,"Vegetable"));
		categoryList.add(new Category(R.drawable.	ic_fruitname	,"Fruit"));
		categoryList.add(new Category(R.drawable.	ic_service	,"Service"));
		categoryList.add(new Category(R.drawable.	ic_toys	,"Toys"));
		categoryList.add(new Category(R.drawable.	ic_game	,	"Game"));
		categoryList.add(new Category(R.drawable.	ic_hardware	,"Hardware"));
		categoryList.add(new Category(R.drawable.	ic_sanitery	,"Sanitary"));
		categoryList.add(new Category(R.drawable.	ic_grocery	,"Grocery"));
		categoryList.add(new Category(R.drawable.	ic_stationary	,"Stationary"));
		categoryList.add(new Category(R.drawable.	ic_book	,"Book"));
		categoryList.add(new Category(R.drawable.	ic_pets	,"Pets"));
		categoryList.add(new Category(R.drawable.	ic_land	,"Land"));
		categoryList.add(new Category(R.drawable.	ic_rent	,"Rent"));
		categoryList.add(new Category(R.drawable.	ic_glass	,"Aluminum & Glass"));
		categoryList.add(new Category(R.drawable.	ic_construction	,"Construction Material"));
		return categoryList;
	}
}

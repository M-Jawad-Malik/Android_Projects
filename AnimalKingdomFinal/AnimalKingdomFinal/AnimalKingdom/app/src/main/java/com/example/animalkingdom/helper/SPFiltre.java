package com.example.animalkingdom.helper;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.animalkingdom.model.Province;
import com.example.animalkingdom.model.Filter;

public class SPFiltre {

	private static final String FILE_PREFERENCE = "PreferenceFile";

	public static void setFiltre(Activity activity, String chave, String valor){
		SharedPreferences preferences = activity.getSharedPreferences(FILE_PREFERENCE, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(chave, valor);
		editor.commit();
	}

	public static Filter getFiltre(Activity activity){
		SharedPreferences preferences = activity.getSharedPreferences(FILE_PREFERENCE, 0);

		String search = preferences.getString("search", "");
		String shortname_province = preferences.getString("shrtnmprovince", "");
		String category = preferences.getString("category", "");
		String fullname_province = preferences.getString("fullname_province", "");
		String region = preferences.getString("region", "");
		String ddd = preferences.getString("ddd", "");

		String valorMin = preferences.getString("minValue", "");
		String valorMax = preferences.getString("maxValue", "");
		//Toast.makeText(activity.getApplicationContext(), "region:"+region.toString(),Toast.LENGTH_LONG).show();
		Province province = new Province();
		province.setShortnm_province(shortname_province);
		province.setFullname_province(fullname_province);
		province.setAreacode(ddd);
		province.setRegion(region);

		Filter filter = new Filter();
		filter.setProvince(province);
		filter.setSearch(search);
		filter.setCategory(category);

		if (!valorMin.isEmpty()){
			filter.setValueMin(Integer.parseInt(valorMin));
		}
		if (!valorMax.isEmpty()){
			filter.setValueMax(Integer.parseInt(valorMax));
		}

		return filter;
	}

	public static void ClearFilters(Activity activity){
		setFiltre(activity, "search", "");
		setFiltre(activity, "shrtnmprovince", "");
		setFiltre(activity, "category", "");
		setFiltre(activity, "fullname_province", "");
		setFiltre(activity, "region", "");
		setFiltre(activity, "ddd", "");
	}
}

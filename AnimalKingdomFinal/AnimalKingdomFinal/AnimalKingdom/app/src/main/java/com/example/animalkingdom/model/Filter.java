package com.example.animalkingdom.model;

public class Filter {
	private Province province;
	private String category;
	private String search;
	private int valueMin;
	private int valueMax;

	public Filter() {
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getValueMin() {
		return valueMin;
	}

	public void setValueMin(int valorMin) {
		this.valueMin = valorMin;
	}

	public int getValueMax() {
		return valueMax;
	}

	public void setValueMax(int valorMax) {
		this.valueMax = valorMax;
	}
}

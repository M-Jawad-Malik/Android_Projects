package com.example.resortingapp;

public class Filter {
	private Province province;
	private String interest;
	private String search;
	private int valueMin;
	private int valueMax;
	private String season;
	public Filter() {
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
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

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSeason() {
		return season;
	}
}

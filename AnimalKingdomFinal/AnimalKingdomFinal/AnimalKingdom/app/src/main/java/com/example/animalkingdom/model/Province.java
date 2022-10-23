package com.example.animalkingdom.model;

import java.io.Serializable;

public class Province implements Serializable {
	private String shortnm_province;
	private String region;
	private String fullname_province;
	private String areacode;

	public Province() {

	}

	public Province(String nm, String province) {
		this.fullname_province = nm;
		this.shortnm_province = province;
	}

	public String getShortnm_province() {
		return shortnm_province;
	}

	public void setShortnm_province(String provnince) {
		this.shortnm_province = provnince;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getFullname_province() {
		return fullname_province;
	}

	public void setFullname_province(String nome) {
		this.fullname_province = nome;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String ddd) {
		this.areacode = ddd;
	}
}

package com.example.animalkingdom.model;

import java.io.Serializable;

public class Local implements Serializable {

		private String zipCode;
	private String district;
	private String localtimezone;
	private String state;
	private String areacode;

	public Local() {
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String cep) {
		this.zipCode = cep;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String bairro) {
		this.district = bairro;
	}

	public String getLocaltimezone() {
		return localtimezone;
	}

	public void setLocaltimezone(String localidade) {
		this.localtimezone = localidade;
	}

	public String getState() {
		return state;
	}

	public void setState(String uf) {
		this.state = uf;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String ddd) {
		this.areacode = ddd;
	}
}

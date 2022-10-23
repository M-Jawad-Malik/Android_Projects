package com.example.animalkingdom.model;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.animalkingdom.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class Address {
	private String Zipcode;
	private String province;
	private String city;
	private String district;

	public Address(){

	}

	public void Save(String idUser, Context context, ProgressBar progressBar){
		DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
				.child("address")
				.child(idUser);

		enderecoRef.setValue(this).addOnCompleteListener(task -> {
			if (task.isSuccessful()){
				Toast.makeText(context, "Address Saved Successfully!", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
			}
			progressBar.setVisibility(View.GONE);
		});
	}

	public String getZipcode() {
		return Zipcode;
	}

	public void setZipcode(String cep) {
		this.Zipcode = cep;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String uf) {
		this.province = uf;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}

package com.example.tris.ads.model;

import android.app.Activity;
import android.content.Intent;

import com.example.tris.HomePage;
import com.example.tris.ads.activities.Home;
import com.example.tris.ads.model.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ads implements Serializable {
	private String id;
	private String idUser;
	private String title;
	private double price;
	private String description;
	private String category;
	private double discount;
	private long publicData;
	private String address;
	private String approved;
	private List<String> urlImages = new ArrayList<>();

	public Ads() {
		DatabaseReference Ref = FirebaseHelper.getDatabaseReference();
		this.setId(Ref.push().getKey());
	}

	public void Save(Activity activity , boolean newAds){
		DatabaseReference publicAdsRef = FirebaseHelper.getDatabaseReference()
				.child("Ads_public")
				.child(this.getId());
		publicAdsRef.setValue(this);

		DatabaseReference myAdsRef = FirebaseHelper.getDatabaseReference()
				.child("my_ads")
				.child(FirebaseHelper.getIdFirebase())
				.child(this.getId());
		myAdsRef.setValue(this);

		if (newAds){
			DatabaseReference publicAdsData = publicAdsRef
					.child("publicData");
			publicAdsData.setValue(ServerValue.TIMESTAMP);

			DatabaseReference dataMeusAnuncio = myAdsRef
					.child("publicData");
			dataMeusAnuncio.setValue(ServerValue.TIMESTAMP).addOnCompleteListener(task -> {
				activity.finish();
				Intent intent = new Intent(activity, HomePage.class);
				intent.putExtra("id", 2);
				activity.startActivity(intent);
			});
		}else{
			activity.finish();
		}
	}

	public void remover(){
		DatabaseReference publicAdsRef = FirebaseHelper.getDatabaseReference()
				.child("Ads_public")
				.child(this.getId());
		publicAdsRef.removeValue();

		DatabaseReference myAdsRef = FirebaseHelper.getDatabaseReference()
				.child("my_ads")
				.child(FirebaseHelper.getIdFirebase())
				.child(this.getId());
		myAdsRef.removeValue();

		for (int i = 0; i < getImageUrls().size(); i++) {
			StorageReference storageReference = FirebaseHelper.getStorageReference()
					.child("images")
					.child("ads")
					.child(getId())
					.child("image" + i + ".jpeg");

			storageReference.delete();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return idUser;
	}

	public void setUserId(String idUsuario) {
		this.idUser = idUsuario;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String titulo) {
		this.title = titulo;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double valor) {
		this.discount = valor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descricao) {
		this.description = descricao;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String categoria) {
		this.category = categoria;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<String> getImageUrls() {
		return urlImages;
	}

	public void setImageUrls(List<String> urlImagens) {
		this.urlImages = urlImagens;
	}
	public long getPublicData() {
		return publicData;
	}

	public void setDataPublicacao(long publicData) {
		this.publicData = publicData;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
}

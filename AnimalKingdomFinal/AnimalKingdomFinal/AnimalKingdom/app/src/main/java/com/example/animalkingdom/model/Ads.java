package com.example.animalkingdom.model;

import android.app.Activity;
import android.content.Intent;

import com.example.animalkingdom.Home;
import com.example.animalkingdom.helper.FirebaseHelper;
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
	private double value;
	private String description;
	private String category;
	private Local local;
	private long publicData;
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
				Intent intent = new Intent(activity, Home.class);
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

	public double getValue() {
		return value;
	}

	public void setValue(double valor) {
		this.value = valor;
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

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public long getPublicData() {
		return publicData;
	}

	public void setDataPublicacao(long dataPublicacao) {
		this.publicData = dataPublicacao;
	}

	public List<String> getImageUrls() {
		return urlImages;
	}

	public void setImageUrls(List<String> urlImagens) {
		this.urlImages = urlImagens;
	}
}

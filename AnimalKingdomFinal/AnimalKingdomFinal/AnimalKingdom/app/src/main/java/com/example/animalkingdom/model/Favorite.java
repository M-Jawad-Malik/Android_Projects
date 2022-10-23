package com.example.animalkingdom.model;


import com.example.animalkingdom.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Favorite {

	private List<String> favorites;

	public void setval(){
		DatabaseReference favoriteRef = FirebaseHelper.getDatabaseReference()
				.child("favorites")
				.child(FirebaseHelper.getIdFirebase());
		favoriteRef.setValue(getFavoritos());
	}

	public List<String> getFavoritos() {
		return favorites;
	}

	public void setFavoritos(List<String> favorites) {
		this.favorites = favorites;
	}
}

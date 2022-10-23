package com.example.tris.Model;


import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Favorite {

	private List<String> favorites;

	public void setval(){
		DatabaseReference favoriteRef = com.example.tris.Model.FirebaseHelper.getDatabaseReference()
				.child("favorites")
				.child(com.example.tris.Model.FirebaseHelper.getIdFirebase());
		favoriteRef.setValue(getFavoritos());
	}

	public List<String> getFavoritos() {
		return favorites;
	}

	public void setFavoritos(List<String> favorites) {
		this.favorites = favorites;
	}
}

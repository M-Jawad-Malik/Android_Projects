package com.example.tris.ads.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {
	private static FirebaseAuth auth;
	private static DatabaseReference databaseReference;
	private static StorageReference storageReference;

	public static StorageReference getStorageReference() {
		if (storageReference == null) {
			storageReference = FirebaseStorage.getInstance().getReference();
		}
		return storageReference;
	}

	public static String getIdFirebase() {
		return getAuth().getUid();
	}

	public static FirebaseAuth getAuth() {
		if (auth == null) {
			auth = FirebaseAuth.getInstance();
		}
		return auth;
	}

	public static DatabaseReference getDatabaseReference() {
		if (databaseReference == null) {
			databaseReference = FirebaseDatabase.getInstance().getReference();
		}
		return databaseReference;
	}

	public static boolean getAutentication() {
		return getAuth().getCurrentUser() != null;
	}

	public static String validErros(String erro) {
		String message = "";
		if (erro.contains("There is no user record corresponding to this identifier")) {
			message = "No accounts found with this email.";
		} else if (erro.contains("The email address is badly formatted")) {
			message = "Please enter a valid email.";
		} else if (erro.contains("The password is invalid or the user does not have a password")) {
			message = "Invalid password, please try again.";
		} else if (erro.contains("The email address is already in use by another account")) {
			message = "This email is already in use.";
		} else if (erro.contains("Password should be at least 6 characters")) {
				message = "Enter a password of at least 6 characters.";
		}
		return message;
	}
}

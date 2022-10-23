package com.example.tris.ads.model;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {

	private String id;
	private String name;
	private String email;
	private String phoneNo;
	private String residential_id;

	public User() {

	}

	public void save(ProgressBar progressBar, Context context) {
		DatabaseReference referenceRef = FirebaseHelper.getDatabaseReference();
		referenceRef.child("users")
				.child(this.getId())
				.setValue(this).addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						progressBar.setVisibility(View.GONE);
						Toast.makeText(context, "Profile Save Successfully!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Uploading Error", Toast.LENGTH_SHORT).show();
					}
					progressBar.setVisibility(View.GONE);
				});
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return phoneNo;
	}

	public void setTelefone(String telefone) {
		this.phoneNo = telefone;
	}

	public String getResidential_id() {
		return residential_id;
	}

	public void setResidential_id(String residential_id) {
		this.residential_id = residential_id;
	}

}

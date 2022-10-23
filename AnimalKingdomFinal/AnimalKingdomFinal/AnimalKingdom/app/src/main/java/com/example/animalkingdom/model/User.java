package com.example.animalkingdom.model;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {

	private String id;
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	private String imgprofile;
	public User() {

	}

	public void salvar(ProgressBar progressBar, Context context) {
		DatabaseReference referenceRef = FirebaseHelper.getDatabaseReference();
		referenceRef.child("users")
				.child(this.getId())
				.setValue(this).addOnCompleteListener(task -> {
					if (task.isSuccessful()){
						progressBar.setVisibility(View.GONE);
						Toast.makeText(context, "Profile Save Successfully!", Toast.LENGTH_SHORT).show();
					}else{
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
		return nome;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public String getImgprofile() {
		return imgprofile;
	}

	public void setProfileImage(String profileImage) {
		this.imgprofile = profileImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Exclude
	public String getPassword() {
		return senha;
	}

	public void setPassword(String senha) {
		this.senha = senha;
	}
}

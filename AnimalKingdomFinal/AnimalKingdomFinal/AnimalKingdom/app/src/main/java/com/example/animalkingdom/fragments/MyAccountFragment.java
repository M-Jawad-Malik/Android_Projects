package com.example.animalkingdom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.animalkingdom.AddressActivity;
import com.example.animalkingdom.Home;
import com.example.animalkingdom.MainActivity;
import com.example.animalkingdom.NewProfileActivity;
import com.example.animalkingdom.R;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyAccountFragment extends Fragment {

	private TextView text_acc;
	private TextView text_user;
	private ImageView img_profile;

	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_account, container, false);

		InitializeComponent(view);
		configClicks(view);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		setuser();
	}

	private void setuser(){
		if (FirebaseHelper.getAutentication()){
			DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
					.child("users")
					.child(FirebaseHelper.getIdFirebase());
			userRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					user = snapshot.getValue(User.class);
					configAccount();
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}
	}

	private void configAccount(){
		text_user.setText(user.getName());

		if (user.getImgprofile() != null){
			img_profile.setScaleType(ImageView.ScaleType.CENTER_CROP);
			Picasso.get()
					.load(user.getImgprofile())
					.placeholder(R.drawable.loading)
					.into(img_profile);
		}
	}

	private void configClicks(View view){
		view.findViewById(R.id.menu_profile).setOnClickListener(v -> redirectUser(NewProfileActivity.class));
		view.findViewById(R.id.address_menu).setOnClickListener(v -> redirectUser(AddressActivity.class));

		if (FirebaseHelper.getAutentication()){
			text_acc.setText("Exit");
		}else{
			text_acc.setText("Click here");
		}

		text_acc.setOnClickListener(v -> {
			if (FirebaseHelper.getAutentication()){
				FirebaseHelper.getAuth().signOut();
				startActivity(new Intent(getActivity(), Home.class));
			}else{
				startActivity(new Intent(getActivity(), MainActivity.class));
			}
		});
	}

	private void redirectUser(Class<?> clazz){
		if (FirebaseHelper.getAutentication()){
			startActivity(new Intent(getActivity(), clazz));
		}else{
			startActivity(new Intent(getActivity(), MainActivity.class));
		}
	}

	private void InitializeComponent(View view){
		text_acc = view.findViewById(R.id.text_conta);
		text_user = view.findViewById(R.id.text_user);
		img_profile = view.findViewById(R.id.imagem_perfil);
	}
}
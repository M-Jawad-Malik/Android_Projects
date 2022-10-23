package com.example.animalkingdom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalkingdom.AdsDetailsActivity;
import com.example.animalkingdom.R;
import com.example.animalkingdom.adapter.AdapterAds;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.Ads;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesFragment extends Fragment implements AdapterAds.OnClickListener {

	private AdapterAds adapterAds;
	private List<Ads> adsList = new ArrayList<>();

	private RecyclerView rv_ads;
	private ProgressBar progressBar;
	private TextView text_info;

	private Button btn_login;

	private List<String> favoritesList = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_favories, container, false);

		startComponent(view);
		configRV();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		recoverFavoritos();
	}

	private void recoverFavoritos(){
		if (FirebaseHelper.getAutentication()){
			DatabaseReference favoritesRef = FirebaseHelper.getDatabaseReference()
					.child("favorites")
					.child(FirebaseHelper.getIdFirebase());
			favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					favoritesList.clear();
					adsList.clear();
					for (DataSnapshot ds : snapshot.getChildren()){
						favoritesList.add(ds.getValue(String.class));
					}

					if (favoritesList.size() > 0){
						recoverAds();
					}else{
						text_info.setText("None favorite.");
						progressBar.setVisibility(View.GONE);
						adapterAds.notifyDataSetChanged();
					}

				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}
	}

	private void startComponent(View view){
		rv_ads = view.findViewById(R.id.rv_ads);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
		btn_login = view.findViewById(R.id.btn_login);
	}

	private void configRV(){
		rv_ads.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_ads.setHasFixedSize(true);
		adapterAds = new AdapterAds(adsList, this);
		rv_ads.setAdapter(adapterAds);
	}

	private void recoverAds(){
		for (int i = 0; i < favoritesList.size(); i++) {
			DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
					.child("Ads_public")
					.child(favoritesList.get(i));
			adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					Ads ads = snapshot.getValue(Ads.class);
					adsList.add(ads);

					if (adsList.size() == favoritesList.size()){
						text_info.setText("");
						Collections.reverse(adsList);
						adapterAds.notifyDataSetChanged();
						progressBar.setVisibility(View.GONE);
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}
	}

	@Override
	public void OnClick(Ads ads) {
		Intent intent = new Intent(requireContext(), AdsDetailsActivity.class);
		intent.putExtra("selectedAds", ads);
		startActivity(intent);
	}
}
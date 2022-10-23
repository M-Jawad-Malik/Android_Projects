package com.example.tris.ads.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.R;
import com.example.tris.ads.adapter.AdapterAds;
import com.example.tris.ads.adapter.AdapterAds_Len;
import com.example.tris.ads.adapter.AdapterEvents;
import com.example.tris.ads.adapter.AdapterEvents_Len;
import com.example.tris.ads.model.Ads;
import com.example.tris.ads.model.Event;
import com.example.tris.ads.model.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesFragment extends Fragment implements AdapterAds_Len.OnClickListener, AdapterEvents_Len.OnClickListener {

	private AdapterAds_Len adapterAds;
	private List<Ads> adsList = new ArrayList<>();
	private AdapterEvents_Len adapterEvent;
	private List<Event> eventList = new ArrayList<>();
	private RecyclerView rv_ads,rv_events;
	private ProgressBar progressBar;
	private TextView text_info;

	private Button btn_login;

	private List<String> favoritesList = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_favorites, container, false);
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
					eventList.clear();
					for (DataSnapshot ds : snapshot.getChildren()){
						favoritesList.add(ds.getValue(String.class));
					}

					if (favoritesList.size() > 0){
						recoverAds();
							recoverEvents();
					}else{
						text_info.setText("None favorite.");
						progressBar.setVisibility(View.GONE);
						adapterAds.notifyDataSetChanged();
						adapterEvent.notifyDataSetChanged();
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
		rv_events = view.findViewById(R.id.rv_events);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
		btn_login = view.findViewById(R.id.btn_login);
	}

	private void configRV(){
		rv_ads.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_ads.setHasFixedSize(true);
		adapterAds = new AdapterAds_Len(adsList, this);
		rv_ads.setAdapter(adapterAds);
		rv_events.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_events.setHasFixedSize(true);
		adapterEvent = new AdapterEvents_Len(eventList, this);
		rv_events.setAdapter(adapterEvent);
	}
	int j=0;
	private void recoverAds(){

		for (int i = 0; i < favoritesList.size(); i++) {
			DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
					.child("Ads_public")
					.child(favoritesList.get(i));

			if(adsRef!=null)
			{
				j++;
				//Toast.makeText(getContext(),"salan"+String.valueOf(j),Toast.LENGTH_LONG).show();

				adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					Ads ads = snapshot.getValue(Ads.class);
					if(ads!=null) {
						adsList.add(ads);


						text_info.setText("");
						Collections.reverse(adsList);
						adapterAds.notifyDataSetChanged();
						progressBar.setVisibility(View.GONE);
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});}
		}
	}int s;
	private void recoverEvents(){
		for (int i = 0; i < favoritesList.size(); i++) {
			DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
					.child("Events_public")
					.child(favoritesList.get(i));
			if(adsRef!=null)
			{
				s++;
			adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					Event event = snapshot.getValue(Event.class);
					if(event!=null) {
						eventList.add(event);
						text_info.setText("");
						Collections.reverse(eventList);
						adapterEvent.notifyDataSetChanged();
						progressBar.setVisibility(View.GONE);
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
			}
		}
	}

	@Override
	public void OnClick(Ads ads) {
		Intent intent = new Intent(requireContext(), com.example.tris.ads.activities.AdsDetailsActivity.class);
		intent.putExtra("selectedAds", ads);
		startActivity(intent);
	}

	@Override
	public void OnClick(Event ads) {
		Intent intent = new Intent(requireContext(), com.example.tris.ads.activities.AdsDetailsActivity.class);
		intent.putExtra("selectedEvent", ads);
		startActivity(intent);
	}
}
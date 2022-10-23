package com.example.tris.ads.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tris.R;
import com.example.tris.ads.activities.AdFormActivity;
import com.example.tris.ads.activities.AdsEdit;
import com.example.tris.ads.adapter.AdapterAds;
import com.example.tris.ads.adapter.AdapterAds_Len;
import com.example.tris.ads.adapter.AdapterEvents_Len;
import com.example.tris.ads.model.Ads;
import com.example.tris.ads.model.Event;
import com.example.tris.ads.model.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAdsFragment extends Fragment implements AdapterAds_Len.OnClickListener, AdapterEvents_Len.OnClickListener  {

	private AdapterAds_Len adapterAds;
	private AdapterEvents_Len adapterEvents;
	private List<Ads> adsList = new ArrayList<>();
	private List<Event> eventList = new ArrayList<>();
	private SwipeableRecyclerView rv_ads;
	private ProgressBar progressBar;
	private TextView text_info,text_main;
	String pretype;
	private Button btn_login;
Bundle bundle;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_ads, container, false);

		startsComponents(view);

		bundle=this.getArguments();
		pretype=bundle.getString("type");
		if(pretype!=null)
		{
		if(pretype.contains("ad")) {
			retrieveAds();
			text_main.setText("Ads Inspection");
		}else if(pretype.contains("event")){
			retrieveEvents();
			text_main.setText("Events Inspection");
		}
		}
		configRV();
		return view;
	}
	private void retrieveEvents(){
		if (FirebaseHelper.getAutentication()) {
			DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
					.child("my_events")
					.child(FirebaseHelper.getIdFirebase());
//                    .child(FirebaseHelper.getIdFirebase());

			adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()){
						eventList.clear();
						for (DataSnapshot ds : snapshot.getChildren()){
							Event event = ds.getValue(Event.class);

								eventList.add(event);
						}
						text_info.setText("");
						Collections.reverse(eventList);
						adapterEvents.notifyDataSetChanged();
						progressBar.setVisibility(View.GONE);
					}else{
						text_info.setText("None Event Registered.");
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}else{
			btn_login.setVisibility(View.VISIBLE);
			text_info.setText("");
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		retrieveAds();
	}

	private void retrieveAds(){
		if (FirebaseHelper.getAutentication()) {
			DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
					.child("my_ads")
							.child(FirebaseHelper.getIdFirebase());

			adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()){
						adsList.clear();
						for (DataSnapshot ds : snapshot.getChildren()){
							Ads ads = ds.getValue(Ads.class);
							adsList.add(ads);
						}
						text_info.setText("");
						Collections.reverse(adsList);
						adapterAds.notifyDataSetChanged();
						progressBar.setVisibility(View.GONE);
					}else{
						text_info.setText("None Ads Registered.");
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}else{
			btn_login.setVisibility(View.VISIBLE);
			text_info.setText("");
			progressBar.setVisibility(View.GONE);
		}
	}

	private void configRV(){
		rv_ads.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_ads.setHasFixedSize(true);
		adapterAds = new AdapterAds_Len(adsList, this);
		adapterEvents=new AdapterEvents_Len(eventList,this);
		Toast.makeText(requireContext(), pretype, Toast.LENGTH_SHORT).show();
		if(pretype.contains("ad")) {
			rv_ads.setAdapter(adapterAds);
		}else if(pretype.contains("event")){
			rv_ads.setAdapter(adapterEvents);
		}

		rv_ads.setListener(new SwipeLeftRightCallback.Listener() {
			@Override
			public void onSwipedLeft(int position) {
				if(pretype.contains("ad")) {
					showDialogAdDelete(adsList.get(position));
				}else if(pretype.contains("event")){
					showDialogEventDelete(eventList.get(position));
				}
			}

			@Override
			public void onSwipedRight(int position) {
				if(pretype.contains("ad")) {
					showDialogAdEdit(adsList.get(position));
				}else if(pretype.contains("event")){
					showDialogEventEdit(eventList.get(position));
				}

			}
		});
	}

	private void showDialogAdEdit(Ads ads){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
		alertDialog.setTitle("want to edit Ads ?");
		alertDialog.setMessage("Click yes to Edit or close");
		alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
			dialog.dismiss();
			adapterAds.notifyDataSetChanged();
		})).setPositiveButton("Yes", ((dialog, whitch) -> {
			Intent intent = new Intent(requireContext(), AdsEdit.class);
			intent.putExtra("selectedAds", ads);
			startActivity(intent);

			adapterAds.notifyDataSetChanged();
		}));

		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}
		private void showDialogEventEdit(Event event){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
		alertDialog.setTitle("want to edit Event ?");
		alertDialog.setMessage("Click yes to Edit or close");
		alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
			dialog.dismiss();
			adapterEvents.notifyDataSetChanged();
		})).setPositiveButton("Yes", ((dialog, whitch) -> {
			Intent intent = new Intent(requireContext(), AdsEdit.class);
			intent.putExtra("selectedEvent", event);
			startActivity(intent);
			adapterEvents.notifyDataSetChanged();
		}));
		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}
	private void showDialogAdDelete(Ads ads){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
		alertDialog.setTitle("\nDo you want to remove the ad ?");
		alertDialog.setMessage("Click Yes to Remove or Close");
		alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
			dialog.dismiss();
			adapterAds.notifyDataSetChanged();
		})).setPositiveButton("Yes", ((dialog, whitch) -> {
			adsList.remove(ads);
			ads.remover();

			adapterAds.notifyDataSetChanged();
		}));

		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}
	private void showDialogEventDelete(Event event){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
		alertDialog.setTitle("\nDo you want to remove the ad ?");
		alertDialog.setMessage("Click Yes to Remove or Close");
		alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
			dialog.dismiss();
			adapterAds.notifyDataSetChanged();
		})).setPositiveButton("Yes", ((dialog, whitch) -> {
			eventList.remove(event);
			event.remover();

			adapterEvents.notifyDataSetChanged();
		}));

		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}

	private void startsComponents(View view){
		rv_ads = view.findViewById(R.id.rv_anuncios);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
		btn_login = view.findViewById(R.id.btn_login);
		text_main=view.findViewById(R.id.text_main);
	}

	@Override
	public void OnClick(Ads ads) {

	}

	@Override
	public void OnClick(Event event) {

	}
}
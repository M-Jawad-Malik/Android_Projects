package com.example.animalkingdom.fragments;

import android.app.AlertDialog;
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

import com.example.animalkingdom.AdFormActivity;
import com.example.animalkingdom.R;
import com.example.animalkingdom.adapter.AdapterAds;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.Ads;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAdsFragment extends Fragment implements AdapterAds.OnClickListener {

	private AdapterAds adapterAds;
	private List<Ads> adsList = new ArrayList<>();

	private SwipeableRecyclerView rv_ads;
	private ProgressBar progressBar;
	private TextView text_info;

	private Button btn_login;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_ads, container, false);

		startsComponents(view);
		configRV();
		return view;
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
		adapterAds = new AdapterAds(adsList, this);
		rv_ads.setAdapter(adapterAds);

		rv_ads.setListener(new SwipeLeftRightCallback.Listener() {
			@Override
			public void onSwipedLeft(int position) {
				showDialogDelete(adsList.get(position));
			}

			@Override
			public void onSwipedRight(int position) {
				showDialogEdit(adsList.get(position));
			}
		});
	}

	private void showDialogEdit(Ads ads){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
		alertDialog.setTitle("want to edit Ads ?");
		alertDialog.setMessage("Click yes to Edit or close");
		alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
			dialog.dismiss();
			adapterAds.notifyDataSetChanged();
		})).setPositiveButton("Yes", ((dialog, whitch) -> {
			Intent intent = new Intent(requireContext(), AdFormActivity.class);
			intent.putExtra("selectedAds", ads);
			startActivity(intent);

			adapterAds.notifyDataSetChanged();
		}));

		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}

	private void showDialogDelete(Ads ads){
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

	private void startsComponents(View view){
		rv_ads = view.findViewById(R.id.rv_anuncios);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
		btn_login = view.findViewById(R.id.btn_login);
	}

	@Override
	public void OnClick(Ads ads) {

	}
}
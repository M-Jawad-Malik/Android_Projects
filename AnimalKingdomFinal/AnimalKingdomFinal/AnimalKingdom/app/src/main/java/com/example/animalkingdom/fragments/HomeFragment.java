package com.example.animalkingdom.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.animalkingdom.CategoriesActivity;
import com.example.animalkingdom.AdsDetailsActivity;
import com.example.animalkingdom.ProvinceActivity;
import com.example.animalkingdom.FiltresActivity;
import com.example.animalkingdom.AdFormActivity;
import com.example.animalkingdom.MainActivity;
import com.example.animalkingdom.R;
import com.example.animalkingdom.adapter.AdapterAds;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.helper.SPFiltre;
import com.example.animalkingdom.model.Ads;
import com.example.animalkingdom.model.Category;
import com.example.animalkingdom.model.Filter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterAds.OnClickListener {

	private final int REQUEST_catgory = 100;

	private AdapterAds adapterAds;
	private List<Ads> adsList = new ArrayList<>();

	private RecyclerView rv_ads;
	private ProgressBar progressBar;
	private TextView text_info;

	private Filter filter = new Filter();

	private SearchView search_view;
	private EditText edit_search_view;

	private Button btn_filters;
	private Button btn_category;
	private Button btn_regions;

	private Button btn_new_ad;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		intializecomponent(view);

		configRV();

		configclicks();

		configSearchView();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		configFilters();
	}

	private void configSearchView() {
		edit_search_view = search_view.findViewById(androidx.appcompat.R.id.search_src_text);
		search_view.findViewById(androidx.appcompat.R.id.search_close_btn).setOnClickListener(v -> {
				CleanSearch();

		});

		search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				SPFiltre.setFiltre(requireActivity(), "search", query);

				configFilters();

				hideKeyboard();

				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});
	}

	private void CleanSearch() {
		search_view.clearFocus();

		edit_search_view.setText("");

		SPFiltre.setFiltre(requireActivity(), "search", "");

		configFilters();

		hideKeyboard();
	}

	private void configFilters() {
			filter = SPFiltre.getFiltre(requireActivity());

		if (!filter.getProvince().getRegion().isEmpty()) {
			btn_regions.setText(filter.getProvince().getRegion());
		} else {
			btn_regions.setText("Regions");
		}
		if (!filter.getCategory().isEmpty()) {
			btn_category.setText(filter.getCategory());
		} else {
			btn_category.setText("Categories");
		}
		recoverads();
	}

	private void recoverads() {
		DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
				.child("Ads_public");

		adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					adsList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						Ads ads = ds.getValue(Ads.class);
						adsList.add(ads);
					}


					if (!filter.getCategory().isEmpty()) {
						if (!filter.getCategory().equals("All Categories")) {
							for (Ads ads : new ArrayList<>(adsList)) {
								if (!ads.getCategory().equals(filter.getCategory())) {
									adsList.remove(ads);
								}
							}
						}
					}


					if (!filter.getProvince().getShortnm_province().isEmpty()) {
						for (Ads ads : new ArrayList<>(adsList)) {
							if (!ads.getLocal().getState().contains(filter.getProvince().getShortnm_province()
							))
								adsList.remove(ads);
						}
					}


					if (!filter.getProvince().getAreacode().isEmpty()) {
						for (Ads ads : new ArrayList<>(adsList)) {
							if (!ads.getLocal().getState().equals(filter.getProvince().getShortnm_province())) {
								adsList.remove(ads);
							}
						}
					}


					if (!filter.getSearch().isEmpty()) {
						for (Ads ads : new ArrayList<>(adsList)) {
							if (!ads.getTitle().toLowerCase().contains(filter.getSearch().toLowerCase())) {
								adsList.remove(ads);
							}
						}
					}


					if (filter.getValueMin() > 0) {
						for (Ads ads : new ArrayList<>(adsList)) {
							if (ads.getValue() < filter.getValueMin()) {
								adsList.remove(ads);
							}
						}
					}


					if (filter.getValueMax() > 0) {
						for (Ads ads : new ArrayList<>(adsList)) {
							if (ads.getValue() > filter.getValueMax()) {
								adsList.remove(ads);
							}
						}
					}

					text_info.setText("");
					Collections.reverse(adsList);
					adapterAds.notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
				} else {
					text_info.setText("No ad registered.");
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configclicks() {
		btn_new_ad.setOnClickListener(v -> {
			if (FirebaseHelper.getAutentication()) {
				startActivity(new Intent(getActivity(), AdFormActivity.class));
			} else {
				startActivity(new Intent(getActivity(), MainActivity.class));
			}
		});
		btn_category.setOnClickListener(view -> {
			Intent intent = new Intent(requireContext(), CategoriesActivity.class);
			intent.putExtra("all", true);
			startActivityForResult(intent, REQUEST_catgory);
		});
		btn_filters.setOnClickListener(v -> startActivity(new Intent(requireContext(), FiltresActivity.class)));
		btn_regions.setOnClickListener(v -> startActivity(new Intent(requireContext(), ProvinceActivity.class)));
	}

	private void configRV() {
		rv_ads.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_ads.setHasFixedSize(true);
		adapterAds = new AdapterAds(adsList, this);
		rv_ads.setAdapter(adapterAds);
	}

	private void intializecomponent(View view) {
		btn_new_ad = view.findViewById(R.id.btn_novo_anuncio);

		rv_ads = view.findViewById(R.id.rv_anuncios);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);

		btn_filters = view.findViewById(R.id.btn_filtres);
		btn_category = view.findViewById(R.id.btn_categories);
		btn_regions = view.findViewById(R.id.btn_regions);
	search_view = view.findViewById(R.id.search_view);
//		search_view = view.findViewById(R.id.search_view);
	}

	private void hideKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(search_view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void OnClick(Ads ads) {
		Intent intent = new Intent(requireContext(), AdsDetailsActivity.class);
		intent.putExtra("selectedAds", ads);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == requireActivity().RESULT_OK) {
			if (requestCode == REQUEST_catgory) {
				Category categorySelected = (Category) data.getExtras().getSerializable("selectedCategory");
				SPFiltre.setFiltre(requireActivity(), "category", categorySelected.getName());

				configFilters();
			}
		}
	}
}
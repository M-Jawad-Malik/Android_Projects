package com.example.tris.ads.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.Ecommerce.SliderItem;
import com.example.tris.Ecommerce.adapter.slider_adapter;
import com.example.tris.R;
import com.example.tris.ads.activities.AdFormActivity;
import com.example.tris.ads.activities.AdsDetailsActivity;
import com.example.tris.ads.adapter.AdapterAds;
import com.example.tris.ads.adapter.AdapterEvents;
import com.example.tris.ads.model.Ads;
import com.example.tris.ads.model.Event;
import com.example.tris.ads.model.FirebaseHelper;
import com.example.tris.manageExpense.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Empty;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterAds.OnClickListener,AdapterEvents.OnClickListener {

	private final int REQUEST_catgory = 100;

	private AdapterAds adapterAds;
	private AdapterEvents adapterevents;
	private List<Ads> adsList = new ArrayList<>();
	private List<Event> eventsList = new ArrayList<>();
	private RecyclerView rv_ads,rv_events;

	private ProgressBar progressBar;
	private TextView text_info;

	private SearchView search_view;
	private EditText edit_search_view;

	private Button btn_filters;
	private Button btn_category;
	private Button btn_regions;

	private Button btn_new_ad;
	SliderView sliderView;
	private slider_adapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		intializecomponent(view);
		configRV();
		sliderView = view.findViewById(R.id.imageSlider);
		adapter = new slider_adapter(getContext());

		sliderView.setSliderAdapter(adapter);

		sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
		sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
		sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
		sliderView.setIndicatorSelectedColor(Color.WHITE);
		sliderView.setIndicatorUnselectedColor(Color.GRAY);
		sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
		sliderView.startAutoCycle();

		sliderView.setOnIndicatorClickListener(new DrawController.ClickListener()
		{
			@Override
			public void onIndicatorClicked(int position)
			{
				Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
			}
		});
		addNewItem(view);
		renewItems(view);
		removeLastItem(view);
		return view;
	}
	public void renewItems(View view)
	{
		List<SliderItem> sliderItemList = new ArrayList<>();
		//dummy data
		for (int i = 0; i < 5; i++)
		{
			SliderItem sliderItem = new SliderItem();

			if (i % 2 == 0)
			{
				sliderItem.setDescription("A New Life Style");
				sliderItem.setImageUrl("https://images.pexels.com/photos/5167255/pexels-photo-5167255.jpeg?cs=srgb&dl=pexels-natalie-dmay-5167255.jpg&fm=jpg");
			}
			else
			{
				sliderItem.setDescription("Luxury Residential and Commercial Areas");
				sliderItem.setImageUrl("https://images.pexels.com/photos/6589288/pexels-photo-6589288.jpeg?cs=srgb&dl=pexels-jean-cont-6589288.jpg&fm=jpg");
			}
			sliderItemList.add(sliderItem);
		}
		adapter.renewItems(sliderItemList);
	}

	public void removeLastItem(View view)
	{
		if (adapter.getCount() - 1 >= 0)
			adapter.deleteItem(adapter.getCount() - 1);
	}

	public void addNewItem(View view)
	{
		SliderItem sliderItem = new SliderItem();
		sliderItem.setDescription("Slider Item Added Manually");
		sliderItem.setImageUrl("https://images.pexels.com/photos/5167255/pexels-photo-5167255.jpeg?cs=srgb&dl=pexels-natalie-dmay-5167255.jpg&fm=jpg");
		adapter.addItem(sliderItem);
	}
	@Override
	public void onStart() {
		super.onStart();
		recoverads();
		recoverEvents();
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

						if(!TextUtils.isEmpty(ads.getApproved()) &&ads.getApproved()!="false")
						{
						adsList.add(ads);
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
	private void recoverEvents() {
		DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
				.child("Events_public");

		adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					eventsList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						Event event = ds.getValue(Event.class);
						if(!TextUtils.isEmpty(event.getApproved()) &&event.getApproved()!="false") {
							eventsList.add(event);
						}
					}




					text_info.setText("");
					Collections.reverse(eventsList);
					adapterevents.notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
				} else {
					text_info.setText("No Event registered.");
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
	private void configRV() {
		rv_ads.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
		rv_ads.setHasFixedSize(true);
		adapterAds = new AdapterAds(adsList, this);
		rv_ads.setAdapter(adapterAds);
		rv_events.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
		rv_events.setHasFixedSize(true);
		adapterevents = new AdapterEvents(eventsList, this);
		rv_events.setAdapter(adapterevents);
	}

	private void intializecomponent(View view) {


		rv_ads = view.findViewById(R.id.rv_anuncios);
		rv_events=view.findViewById(R.id.rv_events);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
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

			}
		}
	}

	@Override
	public void OnClick(Event ads) {
		Intent intent = new Intent(requireContext(), AdsDetailsActivity.class);
		intent.putExtra("selectedEvent", ads);
		startActivity(intent);
	}
}
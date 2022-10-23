package com.example.animalkingdom;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animalkingdom.adapter.SliderAdapter;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.helper.GetMask;
import com.example.animalkingdom.model.Ads;
import com.example.animalkingdom.model.Doctor;
import com.example.animalkingdom.model.Favorite;
import com.example.animalkingdom.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class AdsDetailsActivity extends AppCompatActivity {

	private SliderView sliderView;
	private TextView text_title_ads;
	private TextView text_value_ads;
	private TextView text_public_data;
	private TextView text_ads_description;
	private TextView text_ads_category;
	private TextView text_cep_ads,textview20,textview22,homeservice;
	private TextView text_county_ads;
	private TextView text_district_ads,textView12;
	private LikeButton like_button;
	private Doctor doc;
	ImageView imageView7;
	private Ads ads;

	private User user;

	private List<String> favoritosList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads_details);

		InitializeComponentes();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			ads= (Ads) bundle.getSerializable("selectedAds");
			if(ads!=null)
			{
				configData();
				getUsers();
				getFavourites();
				like_button.setVisibility(View.VISIBLE);
				textview22.setVisibility(View.GONE);
				textview20.setVisibility(View.GONE);
				imageView7.setVisibility(View.GONE);
				homeservice.setVisibility(View.GONE);
			}
			doc=(Doctor)bundle.getSerializable("selectedDoctor") ;
			if(doc!=null)
			{
				textview22.setVisibility(View.VISIBLE);
				textview20.setVisibility(View.VISIBLE);
				imageView7.setVisibility(View.VISIBLE);
				homeservice.setVisibility(View.VISIBLE);
				configDoctorData();
				like_button.setVisibility(View.GONE);
			}

		}

		configLikeButton();

		configClicks();
	}

	private void configLikeButton() {
		like_button.setOnLikeListener(new OnLikeListener() {
			@Override
			public void liked(LikeButton likeButton) {
				if (FirebaseHelper.getAutentication()) {
					configSnackBar("", "ad saved.", R.drawable.like_button_on_red, true);
				} else {
					likeButton.setLiked(false);
					AuthenticationAlert("To add this ad to your favorites list, you must be logged in to the app, you want to do that now?");
				}
			}

			@Override
			public void unLiked(LikeButton likeButton) {
				configSnackBar("UNDO", "Ad removed.", R.drawable.like_button_off, false);
			}
		});
	}

	private void configSnackBar(String actionMsg, String msg, int icon, Boolean like) {

		configFavourites(like);

		Snackbar snackbar = Snackbar.make(like_button, msg, Snackbar.LENGTH_SHORT);
		snackbar.setAction(actionMsg, v-> {
			if(!like){
				configFavourites(true);
			}
		});

		TextView text_snack_bar = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
		text_snack_bar.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
		text_snack_bar.setCompoundDrawablePadding(24);
		snackbar.setActionTextColor(Color.parseColor("#F78323"))
				.setTextColor(Color.parseColor("#FFFFFF"))
				.show();
	}

	private void getFavourites(){
		if (FirebaseHelper.getAutentication()){
			DatabaseReference favoritesRef = FirebaseHelper.getDatabaseReference()
					.child("favorites")
					.child(FirebaseHelper.getIdFirebase());
			favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					for (DataSnapshot ds : snapshot.getChildren()){
						favoritosList.add(ds.getValue(String.class));
					}

					if (favoritosList.contains(ads.getId())){
						like_button.setLiked(true);
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}
	}

	private void configFavourites(Boolean like){
		if (like){
			like_button.setLiked(true);
			favoritosList.add(ads.getId());
		}else{
			like_button.setLiked(false);
			favoritosList.remove(ads.getId());
		}

		Favorite favorites = new Favorite();
		favorites.setFavoritos(favoritosList);
		favorites.setval();
	}

	private void AuthenticationAlert(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("You are not authenticated");
		builder.setMessage(msg);
		builder.setNegativeButton("No", ((dialog, which) -> {
			dialog.dismiss();
		})).setPositiveButton("Yes", ((dialog, which) -> {
			startActivity(new Intent(this, MainActivity.class));
		}));

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void configClicks() {
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
		findViewById(R.id.ib_ligar).setOnClickListener(v -> authenticateCheck());
		findViewById(R.id.ib_msg).setOnClickListener(v->msgSend());
	}
	private void msgSend(){
		Uri sms_uri=null;
		if(ads!=null) {
		sms_uri = Uri.parse("smsto:" + user.getTelefone());
		}else if(doc!=null){
			sms_uri = Uri.parse("smsto:" + doc.getContact());
		}
		Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
		if(ads!=null) {
			sms_intent.putExtra("sms_body", "Hey there! I am "+user.getName());
		}else if(doc!=null){
			sms_intent.putExtra("sms_body", "Hey there! I am "+doc.getFirstName());
		}

		startActivity(sms_intent);
	}

	private void authenticateCheck() {
		if(doc!=null)
		{
			Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.fromParts("tel", doc.getContact(), null));
			startActivity(intent);
		}
		if(ads!=null)
		{
		if (FirebaseHelper.getAutentication()) {
			Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.fromParts("tel", user.getTelefone(), null));
			startActivity(intent);
		} else {
			startActivity(new Intent(this, MainActivity.class));
		}
		}
	}

	private void getUsers() {
		DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
				.child("users")
				.child(ads.getUserId());
		userRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				user = snapshot.getValue(User.class);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configData() {
		sliderView.setSliderAdapter(new SliderAdapter(ads.getImageUrls()));
		sliderView.startAutoCycle();
		sliderView.setScrollTimeInSec(4);
		sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
		sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
		text_title_ads.setText(ads.getTitle());
		text_value_ads.setText(getString(R.string.ads_value, GetMask.getValue(ads.getValue())));
		text_ads_category.setText(ads.getCategory());
		text_public_data.setText(getString(R.string.public_data, GetMask.getDate(ads.getPublicData(), 3)));
		text_ads_description.setText(ads.getDescription());
		text_cep_ads.setText(ads.getLocal().getZipCode());
		text_county_ads.setText(ads.getLocal().getAreacode());
		text_district_ads.setText(ads.getLocal().getDistrict());
		textView12.setText("Description");
	}
	private void configDoctorData() {

		sliderView.setSliderAdapter(new SliderAdapter(doc.getImageUrls()));
		sliderView.startAutoCycle();
		sliderView.setScrollTimeInSec(4);
		sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
		sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
		homeservice.setText(doc.getService()?"Yes":"No");
		text_title_ads.setText(doc.getFirstName());
		text_value_ads.setText(doc.getTiming());
		//text_public_data.setText(getString(R.string.public_data, GetMask.getDate(ads.getPublicData(), 3)));
		text_ads_description.setText(doc.getEmail());
		text_ads_category.setText(doc.getSpecification());
		textView12.setText("Email");
//		text_cep_ads.setText(doc.getContact());
//		text_county_ads.setText(ads.getLocal().getAreacode());
//		text_district_ads.setText(ads.getLocal().getDistrict());
	}

	private void InitializeComponentes() {
		sliderView = findViewById(R.id.sliderView);
		text_title_ads = findViewById(R.id.text_title_ads);
		text_value_ads = findViewById(R.id.text_value_ads);
		text_public_data = findViewById(R.id.text_data_public);
		text_ads_description = findViewById(R.id.text_description_ads);
		text_ads_category = findViewById(R.id.text_category_ads);
		text_cep_ads = findViewById(R.id.text_cep_ads);
		text_county_ads = findViewById(R.id.text_county_ads);
		text_district_ads = findViewById(R.id.text_district_ads);
		like_button = findViewById(R.id.like_button);
		textView12=findViewById(R.id.textView12);
		textview22=findViewById(R.id.textView22);
		textview20=findViewById(R.id.textView20);
		imageView7=findViewById(R.id.imageView7);
		homeservice=findViewById(R.id.text_homeservice);

	}
}
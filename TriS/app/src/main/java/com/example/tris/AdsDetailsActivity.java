package com.example.tris;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tris.MainActivity;
import com.example.tris.Model.Ads;
import com.example.tris.Model.Event;
import com.example.tris.Model.Favorite;
import com.example.tris.Model.FirebaseHelper;
import com.example.tris.Model.User;
import com.example.tris.R;
import com.example.tris.SliderAdapter;
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
    private LikeButton like_button;
    private Ads ads;
    private Event event;
    private User user;
    private List<String> favoritosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_details);

        InitializeComponentes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            ads = (Ads) bundle.getSerializable("selectedAds");
            event=(Event) bundle.getSerializable("selectedEvent");
            if(ads!=null){
                getUsers();
                configData("ad");
            }
            if(event!=null)
            {
                configData("event");

            }
            //	getUsers();
        }
        configLikeButton();
        getFavourites();
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
                    if(ads!=null&&event==null) {
                        if (favoritosList.contains(ads.getId())) {
                            like_button.setLiked(true);
                        }
                    }else{
                        if (favoritosList.contains(event.getId())) {
                            like_button.setLiked(true);
                        }
                    }				}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void configFavourites(Boolean like){
        if (like){
            like_button.setLiked(true);
            if(ads!=null&&event==null) {
                favoritosList.add(ads.getId());
            }else if(event!=null&&ads==null){
                favoritosList.add(event.getId());
            }
        }
        else{
            like_button.setLiked(false);
            if(ads!=null&&event==null) {
                favoritosList.remove(ads.getId());
            }else if(event!=null&&ads==null){
                favoritosList.add(event.getId());
            }
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
            startActivity(new Intent(this, HomePage.class));
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void configClicks() {
        //findViewById(R.id.ib_back).setOnClickListener(v -> finish());
        findViewById(R.id.ib_ligar).setOnClickListener(v -> authenticateCheck());
    }
    private void authenticateCheck() {
        if (FirebaseHelper.getAutentication()) {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", user.getTelefone(), null));
            startActivity(intent);
        } else {
            startActivity(new Intent(this,HomePage.class));
        }
    }

    private void getUsers() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("users")
                .child(ads.getUserId());
        // Toast.makeText(getApplicationContext(), "helllo1", Toast.LENGTH_SHORT).show();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(), ads.getUserId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void configData(String s) {

        if(s=="ad") {
            sliderView.setSliderAdapter(new SliderAdapter(ads.getImageUrls()));
            sliderView.startAutoCycle();
            sliderView.setScrollTimeInSec(4);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

            text_title_ads.setText(ads.getTitle());
            text_value_ads.setText("Rs. " + String.valueOf((int) ads.getPrice()));
            text_public_data.setText(getString(R.string.public_data) + String.valueOf((int) ads.getPublicData()));
            text_ads_description.setText(ads.getDescription());
            text_ads_category.setText(ads.getCategory());
        }else if(s=="event")
        {
            sliderView.setSliderAdapter(new SliderAdapter(event.getUrlImages()));
            sliderView.startAutoCycle();
            sliderView.setScrollTimeInSec(4);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

            text_title_ads.setText(event.getTitle());
            text_value_ads.setText("Rs. " + String.valueOf((int) event.getPrice()));
            text_public_data.setText(getString(R.string.public_data) + String.valueOf((int) event.getPublicData()));
            text_ads_description.setText(event.getDescription());
            text_ads_category.setText(event.getCategory());
            textView17.setText(event.getAddress());
        }
    }
    TextView textView17;
    private void InitializeComponentes() {
        sliderView = findViewById(R.id.sliderView);
        text_title_ads = findViewById(R.id.text_title_ads);
        text_value_ads = findViewById(R.id.text_value_ads);
        text_public_data = findViewById(R.id.text_data_public);
        text_ads_description = findViewById(R.id.text_description_ads);
        text_ads_category = findViewById(R.id.text_category_ads);
        like_button = findViewById(R.id.like_button);
        textView17=findViewById(R.id.textView17);
    }
}
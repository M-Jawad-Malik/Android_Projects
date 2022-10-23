package com.example.animalkingdom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animalkingdom.adapter.AdapterAds;
import com.example.animalkingdom.adapter.AdapterDoctor;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.Ads;
import com.example.animalkingdom.model.Doctor;
import com.example.animalkingdom.model.Filter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class helpandfeedback extends AppCompatActivity implements AdapterDoctor.OnClickListener{
    private AdapterDoctor adapterAds;
    private List<Doctor> adsList = new ArrayList<>();
    private RecyclerView rv_ads;
    private ProgressBar progressBar;
    private TextView text_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpandfeedback);
        intializecomponent();
        configRV();

    }
    private void intializecomponent() {
        rv_ads = findViewById(R.id.rv_anuncios);
        progressBar =findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
    }
    private void configRV() {
        rv_ads.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rv_ads.setHasFixedSize(true);
        adapterAds = new AdapterDoctor(adsList, this);
        rv_ads.setAdapter(adapterAds);
    }
    @Override
    public void OnClick(Doctor doctor) {
        Intent intent = new Intent(getBaseContext(), AdsDetailsActivity.class);
        intent.putExtra("selectedDoctor", doctor);
        startActivity(intent);
    }
    private void recoverads() {
        DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
                .child("Doctor");

        adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    adsList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Doctor ads = ds.getValue(Doctor.class);
                        adsList.add(ads);
                    }

                    text_info.setText("");
                    Collections.reverse(adsList);
                    adapterAds.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    text_info.setText("No Doctor / Clinic registered.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recoverads();
    }
}
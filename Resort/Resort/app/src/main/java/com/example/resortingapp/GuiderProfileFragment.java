package com.example.resortingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.resortingapp.GuiderProfileDetailActivity;
import com.google.gson.Gson;

import resortingapp.R;

public class GuiderProfileFragment extends Fragment implements AdapterGuiderProfile.OnClickListener {

    private final int REQUEST_CATEGORIA = 100;

    private AdapterGuiderProfile adapterAds;
    private List<Guider> guiderList = new ArrayList<>();

    private RecyclerView rv_anuncios;
    private ProgressBar progressBar;
    private TextView text_info;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guider_profile, container, false);

        initalizeComponents(view);

        configRV();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recoverads();
       }


    private void recoverads() {
        DatabaseReference anunciosRef = FirebaseHelper.getDatabaseReference()
                .child("Guider");

        anunciosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    guiderList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) { // getChildren -> para percorrer todos anuncios do nó
                        Guider ads = ds.getValue(Guider.class);
                        guiderList.add(ads);
                    }


                    text_info.setText("");
                    Collections.reverse(guiderList);
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



    private void configRV() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_anuncios.setHasFixedSize(true);
        adapterAds = new AdapterGuiderProfile(guiderList, this);
        rv_anuncios.setAdapter(adapterAds);
    }

    private void initalizeComponents(View view) {


        rv_anuncios = view.findViewById(R.id.rv_anuncios);
        progressBar = view.findViewById(R.id.progressBar);
        text_info = view.findViewById(R.id.text_info);


    }



    @Override

    public void OnClick(Guider guider) {
        Intent intent = new Intent(requireContext(), GuiderProfileDetailActivity.class);
        String s = (new Gson().toJson(guider));
        intent.putExtra("selectedGuider",  s);
        startActivity(intent);
    }


}
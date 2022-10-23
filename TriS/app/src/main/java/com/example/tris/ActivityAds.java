package com.example.tris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tris.Model.Ads;
import com.example.tris.Model.Event;
import com.example.tris.Model.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityAds extends AppCompatActivity implements AdapterAds_Len.OnClickListener,AdapterEvents_Len.OnClickListener   {
    private AdapterAds_Len adapterAds;
    private AdapterEvents_Len adapterEvents;
    private List<Ads> adsList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private SwipeableRecyclerView rv_ads;
    private ProgressBar progressBar;
    private TextView text_info,text_main;
    private CardView floatingbtn;
    private Button btn_login;
    Bundle bundle;
    String pretype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        bundle=getIntent().getExtras();
        pretype=bundle.getString("type");
        startsComponents();
        configRV();
        if(pretype.contains("ad")) {
            retrieveAds();
            text_main.setText("Ads Inspection");
        }else if(pretype.contains("event")){
            retrieveEvents();
            text_main.setText("Events Inspection");
    }
    }
    private void retrieveAds(){
        if (FirebaseHelper.getAutentication()) {
            DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
                    .child("Ads_public");

//                    .child(FirebaseHelper.getIdFirebase());

            adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        adsList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            Ads ads = ds.getValue(Ads.class);
                            if(TextUtils.isEmpty(ads.getApproved())&&ads.getApproved()!="flase")
                            {
                            adsList.add(ads);
                            }
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
    private void retrieveEvents(){
        if (FirebaseHelper.getAutentication()) {
            DatabaseReference adsRef = FirebaseHelper.getDatabaseReference()
                    .child("Events_public");

//                    .child(FirebaseHelper.getIdFirebase());

            adsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        eventList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            Event event = ds.getValue(Event.class);
                            if(TextUtils.isEmpty(event.getApproved())&&event.getApproved()!="flase")
                            {
                                eventList.add(event);
                            }
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

    private void configRV(){

        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pretype.contains("ad")) {
                    Intent intent=new Intent(getApplicationContext(),AdsEdit.class);
                    intent.putExtra("type",pretype);
                    startActivity(intent);
                }else if(pretype.contains("event")){
                    Intent intent=new Intent(getApplicationContext(),AdsEdit.class);
                    intent.putExtra("type",pretype);
                    startActivity(intent);
                }


            }
        });
        rv_ads.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_ads.setHasFixedSize(true);
        adapterAds = new AdapterAds_Len(adsList, this);
        adapterEvents=new AdapterEvents_Len(eventList,this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityAds.this);
        alertDialog.setTitle("want to edit Ads ?");
        alertDialog.setMessage("Click yes to Edit or close");
        alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
            dialog.dismiss();
            adapterAds.notifyDataSetChanged();
        })).setPositiveButton("Yes", ((dialog, whitch) -> {
            Intent intent = new Intent(this, AdsEdit.class);
            intent.putExtra("selectedAds", ads);
            startActivity(intent);

            adapterAds.notifyDataSetChanged();
        }));

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
    private void showDialogEventEdit(Event event){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityAds.this);
        alertDialog.setTitle("want to edit Event ?");
        alertDialog.setMessage("Click yes to Edit or close");
        alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
            dialog.dismiss();
            adapterEvents.notifyDataSetChanged();
        })).setPositiveButton("Yes", ((dialog, whitch) -> {
            Intent intent = new Intent(this, AdsEdit.class);
            intent.putExtra("selectedEvent", event);
            startActivity(intent);
            adapterEvents.notifyDataSetChanged();
        }));
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
    private void showDialogAdDelete(Ads ads){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityAds.this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityAds.this);
        alertDialog.setTitle("\nDo you want to remove the ad ?");
        alertDialog.setMessage("Click Yes to Remove or Close");
        alertDialog.setNegativeButton("Close", ((dialog, whitch) -> {
            dialog.dismiss();
            adapterEvents.notifyDataSetChanged();
        })).setPositiveButton("Yes", ((dialog, whitch) -> {
            eventList.remove(event);
            event.remover();

            adapterEvents.notifyDataSetChanged();
        }));

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void startsComponents(){
        floatingbtn=findViewById(R.id.floating_btn);
        rv_ads = findViewById(R.id.rv_anuncios);
        progressBar = findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
        text_main=findViewById(R.id.text_main);
        btn_login = findViewById(R.id.btn_login);
    }
    @Override
    public void OnClick(Ads ads) {

    }

    @Override
    public void OnClick(Event event) {

    }
}
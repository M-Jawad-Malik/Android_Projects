package com.example.animalkingdom.model;

import android.app.Activity;
import android.content.Intent;

import com.example.animalkingdom.Home;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doctor implements Serializable {
    private String FirstName;
    private String id;
    private String Timing;
    private String Specification;
    private String email;
    private String contact;
    private Boolean service;
    private List<String> urlImages = new ArrayList<>();

    public Doctor() {
        DatabaseReference Ref = FirebaseHelper.getDatabaseReference();
        this.setId(FirebaseAuth.getInstance().getUid());
    }

    public void Save(Activity activity , boolean newAds){
        DatabaseReference publicAdsRef = FirebaseHelper.getDatabaseReference()
                .child("Doctor")
                .child(this.getId());
        publicAdsRef.setValue(this);
        if (newAds){
            publicAdsRef.setValue(this).addOnCompleteListener(task->{
                activity.finish();
                Intent intent = new Intent(activity, Home.class);
                intent.putExtra("id", 2);
                activity.startActivity(intent);
            });
        }else{
            activity.finish();
        }
    }

    public void remover(){
        DatabaseReference publicAdsRef = FirebaseHelper.getDatabaseReference()
                .child("Doctor")
                .child(this.getId());
        publicAdsRef.removeValue();

        for (int i = 0; i < getImageUrls().size(); i++) {
            StorageReference storageReference = FirebaseHelper.getStorageReference()
                    .child("images")
                    .child("Doctor")
                    .child(getId())
                    .child("image" + i + ".jpeg");

            storageReference.delete();
        }
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getService() {
        return service;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public List<String> getImageUrls() {
        return urlImages;
    }
    public void setImageUrls(List<String> urlImagens) {
        this.urlImages = urlImagens;
    }
}

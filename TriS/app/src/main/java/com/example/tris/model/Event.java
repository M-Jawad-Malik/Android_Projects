package com.example.tris.Model;

import android.app.Activity;
import android.content.Intent;

import com.example.tris.HomePage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {
    private String id;
    private String idUser;
    private String title;
    private double price;
    private String description;
    private String category;
    private String Address;
    private long publicData;
    private String approved;
    private List<String> urlImages = new ArrayList<>();

    public Event() {
        DatabaseReference Ref = FirebaseHelper.getDatabaseReference();
        this.setId(Ref.push().getKey());
    }

    public void Save(Activity activity , boolean newAds){
        DatabaseReference publicEventsRef = FirebaseHelper.getDatabaseReference()
                .child("Events_public")
                .child(this.getId());
        publicEventsRef.setValue(this);

        DatabaseReference myEventsRef = FirebaseHelper.getDatabaseReference()
                .child("my_events")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        myEventsRef.setValue(this);

        if (newAds){
            DatabaseReference publicAdsData = publicEventsRef
                    .child("publicData");
            publicAdsData.setValue(ServerValue.TIMESTAMP);

            DatabaseReference dataMeusAnuncio = myEventsRef
                    .child("publicData");
            dataMeusAnuncio.setValue(ServerValue.TIMESTAMP).addOnCompleteListener(task -> {
                activity.finish();
                Intent intent = new Intent(activity, HomePage.class);
                intent.putExtra("id", 2);
                activity.startActivity(intent);
            });
        }else{
            activity.finish();
        }
    }

    public void remover(){
        DatabaseReference publicAdsRef = FirebaseHelper.getDatabaseReference()
                .child("Events_public")
                .child(this.getId());
        publicAdsRef.removeValue();

        DatabaseReference myAdsRef = FirebaseHelper.getDatabaseReference()
                .child("my_events")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        myAdsRef.removeValue();

        for (int i = 0; i < getUrlImages().size(); i++) {
            StorageReference storageReference = FirebaseHelper.getStorageReference()
                    .child("images")
                    .child("events")
                    .child(getId())
                    .child("image" + i + ".jpeg");

            storageReference.delete();
        }
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return idUser;
    }
    public void setUserId(String idUsuario) {
        this.idUser = idUsuario;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String titulo) {
        this.title = titulo;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String descricao) {
        this.description = descricao;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String categoria) {
        this.category = categoria;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public List<String> getUrlImages() {
        return urlImages;
    }
    public void setUrlImages(List<String> urlImagens) {
        this.urlImages = urlImagens;
    }
    public void setAddress(String address){this.Address=address;};
    public String getAddress() {
        return Address;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public long getPublicData() {
        return publicData;
    }
    public void setDataPublicacao(long publicData) {
        this.publicData = publicData;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}

package com.example.resortingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Guider implements Parcelable {
    String firstName;
    String lastName;
    String gender;
    String email;
    String region;
    String language;
    String contact;
    String webLink;
    String fbLink;
    private List<String> urlImages = new ArrayList<>();
    public Guider(){

    }
    public Guider(String firstName,String lastName,String gender,String email,String region,String language,String contact,String webLink,String fbLink,List<String> imageUrls){
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.email=email;
        this.region=region;
        this.language=language;
        this.contact=contact;
        this.webLink=webLink;
        this.fbLink=fbLink;
        this.urlImages=imageUrls;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getFbLink() {
        return fbLink;
    }
    public List<String> getUrlImagens() {
        return urlImages;
    }

    public void setUrlImagens(List<String> urlImagens) {
        this.urlImages = urlImagens;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

package com.living.roomrental.activity.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProfileModel implements Parcelable {
    private String name;
    private String contactNo;
    private String address;
    private String bio;
    private String occupation;
    private String whoIsUser;
    private String imageUrl;

    private String email;

    public ProfileModel() {
    }

    public ProfileModel(String name, String contactNo, String address, String bio, String occupation, String whoIsUser , String imageUrl) {
        this.name = name;
        this.contactNo = contactNo;
        this.bio = bio;
        this.address = address;
        this.occupation = occupation;
        this.imageUrl = imageUrl;
        this.whoIsUser = whoIsUser;
    }

    protected ProfileModel(Parcel in) {
        name = in.readString();
        contactNo = in.readString();
        bio = in.readString();
        address = in.readString();
        occupation = in.readString();
        imageUrl = in.readString();
        whoIsUser = in.readString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWhoIsUser() {
        return whoIsUser;
    }

    public void setWhoIsUser(String whoIsUser) {
        this.whoIsUser = whoIsUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(contactNo);
        parcel.writeString(bio);
        parcel.writeString(address);
        parcel.writeString(occupation);
        parcel.writeString(imageUrl);
        parcel.writeString(whoIsUser);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel parcel) {
            return new ProfileModel(parcel);
        }

        @Override
        public ProfileModel[] newArray(int i) {
            return new ProfileModel[0];
        }
    };

    @Override
    public String toString() {
        return "CreateProfileModel{" +
                "name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", bio='" + bio + '\'' +
                ", occupation='" + occupation + '\'' +
                ", whoIsUser='" + whoIsUser + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

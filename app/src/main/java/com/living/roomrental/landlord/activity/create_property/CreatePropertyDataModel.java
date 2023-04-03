package com.living.roomrental.landlord.activity.create_property;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.living.roomrental.utilities.Validation;

import java.io.Serializable;
import java.util.ArrayList;

public class CreatePropertyDataModel implements Parcelable {

    private String propertyName , landmarkAddress, mapLocationAddress , rent ;
    private String size , agreement  , description , peopleFor , type , furnishing , furnishingDescription;

    private Double latitude , longitude ;

    private String key;

    private ArrayList<String> propertyImagesUrl = new ArrayList<>();

    private ArrayList<String> facilities = new ArrayList<>();

    public CreatePropertyDataModel() {
    }

    public CreatePropertyDataModel(String propertyName, String landmarkAddress, String mapLocationAddress, String rent, String size, String agreement, String description, String peopleFor, String type, String furnishing, String furnishingDescription, Double latitude, Double longitude, ArrayList<String> propertyImagesUrl, ArrayList<String> facilities) {
        this.propertyName = propertyName;
        this.landmarkAddress = landmarkAddress;
        this.mapLocationAddress = mapLocationAddress;
        this.rent = rent;
        this.size = size;
        this.agreement = agreement;
        this.description = description;
        this.peopleFor = peopleFor;
        this.type = type;
        this.furnishing = furnishing;
        this.furnishingDescription = furnishingDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.propertyImagesUrl = propertyImagesUrl;
        this.facilities = facilities;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLandmarkAddress() {
        return landmarkAddress;
    }

    public void setLandmarkAddress(String landmarkAddress) {
        this.landmarkAddress = landmarkAddress;
    }

    public String getMapLocationAddress() {
        return mapLocationAddress;
    }

    public void setMapLocationAddress(String mapLocationAddress) {
        this.mapLocationAddress = mapLocationAddress;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeopleFor() {
        return peopleFor;
    }

    public void setPeopleFor(String peopleFor) {
        this.peopleFor = peopleFor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public String getFurnishingDescription() {
        return furnishingDescription;
    }

    public void setFurnishingDescription(String furnishingDescription) {
        this.furnishingDescription = furnishingDescription;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getPropertyImages() {
        return propertyImagesUrl;
    }

    public void setPropertyImages(ArrayList<String> images) {
         this.propertyImagesUrl = images;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<String> facilities) {
        this.facilities = facilities;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.propertyName);
        dest.writeString(this.landmarkAddress);
        dest.writeString(this.mapLocationAddress);
        dest.writeString(this.size);
        dest.writeString(this.agreement);
        dest.writeString(this.rent);
        dest.writeString(this.description);
        dest.writeString(this.peopleFor);
        dest.writeString(this.type);
        dest.writeString(this.furnishing);
        dest.writeString(this.furnishingDescription);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.key);
        dest.writeStringList(this.propertyImagesUrl);
        dest.writeStringList(this.facilities);
    }

    public void readFromParcel(Parcel source) {
        this.propertyName = source.readString();
        this.landmarkAddress = source.readString();
        this.mapLocationAddress = source.readString();
        this.size = source.readString();
        this.agreement = source.readString();
        this.rent = source.readString();
        this.description = source.readString();
        this.peopleFor = source.readString();
        this.type = source.readString();
        this.furnishing = source.readString();
        this.furnishingDescription = source.readString();
        this.latitude = (Double) source.readValue(Double.class.getClassLoader());
        this.longitude = (Double) source.readValue(Double.class.getClassLoader());
        this.key = source.readString();
        this.propertyImagesUrl = source.createStringArrayList();
        this.facilities = source.createStringArrayList();
    }

    protected CreatePropertyDataModel(Parcel in) {
        this.propertyName = in.readString();
        this.landmarkAddress = in.readString();
        this.mapLocationAddress = in.readString();
        this.size = in.readString();
        this.agreement = in.readString();
        this.rent = in.readString();
        this.description = in.readString();
        this.peopleFor = in.readString();
        this.type = in.readString();
        this.furnishing = in.readString();
        this.furnishingDescription = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.key = in.readString();
        this.propertyImagesUrl = in.createStringArrayList();
        this.facilities = in.createStringArrayList();
    }

    public static final Creator<CreatePropertyDataModel> CREATOR = new Creator<CreatePropertyDataModel>() {
        @Override
        public CreatePropertyDataModel createFromParcel(Parcel source) {
            return new CreatePropertyDataModel(source);
        }

        @Override
        public CreatePropertyDataModel[] newArray(int size) {
            return new CreatePropertyDataModel[size];
        }
    };
}

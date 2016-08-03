package com.tstasks.sanchellios.navicostores.store_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 28.07.16.
 */
public class Location implements Parcelable{
    private String latitude;
    private String longtitude;

    public Location(String latitude, String longtitude){
        this.setLatitude(latitude);
        this.setLongtitude(longtitude);
    }

    protected Location(Parcel in) {
        latitude = in.readString();
        longtitude = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latitude);
        parcel.writeString(longtitude);
    }
}

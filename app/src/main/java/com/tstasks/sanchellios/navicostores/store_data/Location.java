package com.tstasks.sanchellios.navicostores.store_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 28.07.16.
 */
public class Location implements Parcelable{
    private final int DIVIDER = 1000000;
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude){
        this.latitude = latitude/DIVIDER;
        this.longitude = longitude/DIVIDER;

    }

    protected Location(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
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

    public double getLatitude() {
        return latitude/DIVIDER;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude/DIVIDER;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}

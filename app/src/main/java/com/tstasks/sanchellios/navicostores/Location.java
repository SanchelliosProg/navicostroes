package com.tstasks.sanchellios.navicostores;

/**
 * Created by alex on 28.07.16.
 */
public class Location {
    private String latitude;
    private String longtitude;

    public Location(String latitude, String longtitude){
        this.setLatitude(latitude);
        this.setLongtitude(longtitude);
    }

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
}

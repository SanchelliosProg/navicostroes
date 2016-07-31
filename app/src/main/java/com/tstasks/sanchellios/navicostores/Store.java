package com.tstasks.sanchellios.navicostores;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alex on 28.07.16.
 */
public class Store implements Parcelable{
    private int id;
    private String name;
    private String address;
    private String phone;
    private Location location;
    private ArrayList<InstrumentProfile> instruments;

    public Store(int id, String name, String address, String phone, Location location){
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPhone(phone);
        this.setLocation(location);
        instruments = new ArrayList<>();
    }

    protected Store(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        instruments = in.createTypedArrayList(InstrumentProfile.CREATOR);
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public void addInstrumentProfile(InstrumentProfile instrumentProfile){
        getInstruments().add(instrumentProfile);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<InstrumentProfile> getInstruments() {
        return instruments;
    }

    public void setInstruments(ArrayList<InstrumentProfile> instruments) {
        this.instruments = instruments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeParcelable(location, i);
        parcel.writeTypedList(instruments);
    }
}

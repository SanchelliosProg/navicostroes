package com.tstasks.sanchellios.navicostores.store_data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alex on 28.07.16.
 */
public class Store implements Parcelable{
    private int id;
    private String name;
    private String address;
    private String phone;
    private String website;
    private String email;
    private Location location;
    private ArrayList<InstrumentProfile> instruments = new ArrayList<>();

    public Store(int id, String name, String address, String phone, String website, String email, Location location){
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPhone(phone);
        this.setWebsite(website);
        this.setEmail(email);
        this.setLocation(location);
    }

    public Store(Store store, Location location, ArrayList<InstrumentProfile> instruments){
        this.name = store.getName();
        this.address = store.getAddress();
        this.phone = store.getPhone();
        this.website = store.getWebsite();
        this.email = store.getEmail();
        this.location = location;
        this.instruments = instruments;
    }

    protected Store(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        website = in.readString();
        email = in.readString();
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
        try{
            this.instruments.add(instrumentProfile);
        }catch (NullPointerException ex){
            Log.d(this.getClass().toString(), "Instrument profiles init");
            this.instruments = new ArrayList<>();
            this.instruments.add(instrumentProfile);
        }
    }

    public int getNumberOfInstruments(){
        int sum = 0;
        for(InstrumentProfile ip : instruments){
            sum += ip.getQuantity();
        }
        Log.d("Total quantity", String.valueOf(sum));
        return sum;
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


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<InstrumentProfile> getInstruments(){
        return this.instruments;
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
        parcel.writeString(website);
        parcel.writeString(email);
        parcel.writeParcelable(location, i);
        parcel.writeTypedList(instruments);
    }
}

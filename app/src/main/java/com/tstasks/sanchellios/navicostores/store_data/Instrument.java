package com.tstasks.sanchellios.navicostores.store_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 28.07.16.
 */
public class Instrument implements Parcelable{
    private int id;
    private String brand;
    private String model;
    private String type;
    private double price;

    public Instrument(int id, String brand, String model, String type, double price){
        this.setId(id);
        this.setBrand(brand);
        this.setModel(model);
        this.setType(type);
        this.setPrice(price);
    }

    protected Instrument(Parcel in) {
        id = in.readInt();
        brand = in.readString();
        model = in.readString();
        type = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Instrument> CREATOR = new Creator<Instrument>() {
        @Override
        public Instrument createFromParcel(Parcel in) {
            return new Instrument(in);
        }

        @Override
        public Instrument[] newArray(int size) {
            return new Instrument[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(brand);
        parcel.writeString(model);
        parcel.writeString(type);
        parcel.writeDouble(price);
    }
}

package com.tstasks.sanchellios.navicostores;

import java.util.ArrayList;

/**
 * Created by alex on 28.07.16.
 */
public class Store {
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
}

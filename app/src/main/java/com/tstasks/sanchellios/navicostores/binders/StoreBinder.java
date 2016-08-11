package com.tstasks.sanchellios.navicostores.binders;

public class StoreBinder {
    public String name;
    public String address;
    public String phone;
    public String website;
    public String email;
    public int numberOfInstruments;

    public StoreBinder(String name, String address, String phone,
                       String website, String email, int numberOfInstruments){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.email = email;
        this.numberOfInstruments = numberOfInstruments;
    }
}

package com.tstasks.sanchellios.navicostores.data_binders;

public class StoreBinder {
    private String name;
    private String address;
    private String phone;
    private String website;
    private String email;
    private int numberOfInstruments;

    public StoreBinder(String name, String address, String phone,
                       String website, String email, int numberOfInstruments){
        this.setName(name);
        this.setAddress(address);
        this.setPhone(phone);
        this.setWebsite(website);
        this.setEmail(email);
        this.setNumberOfInstruments(numberOfInstruments);
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

    public int getNumberOfInstruments() {
        return numberOfInstruments;
    }

    public void setNumberOfInstruments(int numberOfInstruments) {
        this.numberOfInstruments = numberOfInstruments;
    }
}

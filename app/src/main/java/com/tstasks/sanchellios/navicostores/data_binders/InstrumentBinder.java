package com.tstasks.sanchellios.navicostores.data_binders;

import com.tstasks.sanchellios.navicostores.store_data.Instrument;

public class InstrumentBinder {
    private String brand;
    private String model;
    private String type;
    private String price;

    public InstrumentBinder(Instrument instrument){
        this.brand = instrument.getBrand();
        this.model = instrument.getModel();
        this.type = adoptType(instrument.getType());
        this.price = priceToString(instrument.getPrice());
    }

    private String priceToString(double price){
        return String.valueOf(price)+"$";
    }

    private String adoptType(String type){
        switch (type){
            case "GUITAR":
                return "Гитара";
            case "PIANO":
                return "Клавишные";
            default:
                return type;
        }
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

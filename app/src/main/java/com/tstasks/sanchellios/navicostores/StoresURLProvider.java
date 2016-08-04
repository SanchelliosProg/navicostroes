package com.tstasks.sanchellios.navicostores;

/**
 * Created by alex on 04.08.16.
 */
public class StoresURLProvider {
    private final String authority = "http://aschoolapi.appspot.com/";
    private final String stores = "stores";
    private final String instruments = "instruments";

    public StoresURLProvider(){}

    public String getStoresURL(){
        return authority + stores;
    }

    public String getStoreInstruments(int id){
        return authority + stores + "/" + id + "/" + instruments;
    }
}

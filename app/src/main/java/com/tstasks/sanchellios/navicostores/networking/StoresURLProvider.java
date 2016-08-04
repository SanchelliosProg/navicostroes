package com.tstasks.sanchellios.navicostores.networking;

/**
 * Created by alex on 04.08.16.
 */
public class StoresURLProvider {
    private static final String authority = "http://aschoolapi.appspot.com/";
    private static final String stores = "stores";
    private static final String instruments = "instruments";

    public StoresURLProvider(){}

    public static String getStoresURL(){
        return authority + stores;
    }

    public static String getStoreInstruments(int id){
        return authority + stores + "/" + id + "/" + instruments;
    }
}

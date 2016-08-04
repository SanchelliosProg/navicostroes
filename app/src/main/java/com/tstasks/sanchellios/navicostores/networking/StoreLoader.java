package com.tstasks.sanchellios.navicostores.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by alex on 03.08.16.
 */
public class StoreLoader extends AsyncTaskLoader<ArrayList<Store>> {

    public static final int LOAD_STORES = 0;

    private ArrayList<Store> stores = new ArrayList<>();
    private String url;
    private Type type;

    public StoreLoader(Context context, String url, Type type) {
        super(context);
        this.url = url;
        this.type = type;
    }

    @Override
    public ArrayList<Store> loadInBackground() {
        try {
            Gson gson = new Gson();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(this.url).build();
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                InputStream in = response.body().byteStream();
                JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                reader.beginArray();
                while (reader.hasNext()){
                    Store store = gson.fromJson(reader, this.type);
                    stores.add(store);
                }
                reader.endArray();
                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>(this.stores);
    }

}

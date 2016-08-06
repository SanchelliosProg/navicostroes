package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.networking.StoreLoader;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Store>> {

    private final String STORES_LOADED_STATE = "STORES_LOADED_STATE";
    private final String STORES_LIST = "STORES_LIST";

    private boolean isStoresLoaded = false;
    public final int LOAD_STORES = 0;

    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            isStoresLoaded = savedInstanceState.getBoolean(STORES_LOADED_STATE);
            stores = savedInstanceState.getParcelableArrayList(STORES_LIST);
        }else {
            stores = new ArrayList<>();
        }
        if(!isStoresLoaded){
            getLoaderManager().initLoader(LOAD_STORES, null, this).forceLoad();
        }
    }

    @Override
    public Loader<ArrayList<Store>> onCreateLoader(int i, Bundle bundle) {
        Loader<ArrayList<Store>> loader = null;
        loader = createStoreLoader();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> stores) {
        this.stores = stores;
        updateStoreRecyclerFragment(this.stores);
        isStoresLoaded = true;
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Store>> loader) {

    }

    private void updateStoreRecyclerFragment(ArrayList<Store> stores){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, StoreRecyclerFragment.newInstance(stores))
                .commit();
    }

    private StoreLoader createStoreLoader(){
        return new StoreLoader(getApplicationContext());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STORES_LOADED_STATE, isStoresLoaded);
        outState.putParcelableArrayList(STORES_LIST, stores);
    }
}

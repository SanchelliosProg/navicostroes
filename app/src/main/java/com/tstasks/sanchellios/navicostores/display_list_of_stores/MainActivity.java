package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.StoreLoader;
import com.tstasks.sanchellios.navicostores.StoresURLProvider;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Store>> {

    //Load States
    private int LOAD_STATE;
    public final int LOAD_STORES = 0;
    //TODO: Load loaction state if necessary
    //TODO: Load Instrument profile if necessary
    //TODO: Load Instrument if necessary

    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LOAD_STATE = LOAD_STORES;
        stores = new ArrayList<>();
        //TODO replace it with the loaded from internet
//        stores = new ArrayList<>();
//        stores.add(new Store(0, "Babooshka", "ulitsa Kutsygina, 35/1, Voronezh", "8 (473) 277-78-58", null));
//        stores.add(new Store(1, "Liubava", "ulitsa 20-letiya Oktabrya, 52, Voronezh", "8 (473) 277-35-29", null));
//        updateStoreRecyclerFragment(stores);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(LOAD_STATE, bundle, this).forceLoad();

    }

    @Override
    public Loader<ArrayList<Store>> onCreateLoader(int i, Bundle bundle) {
        Loader<ArrayList<Store>> loader = null;
        if(LOAD_STATE == LOAD_STORES){
            loader = createStoreLoader();
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> stores) {
        this.stores = stores;
        updateStoreRecyclerFragment(this.stores);
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
        return new StoreLoader(getApplicationContext(), StoresURLProvider.getStoresURL(), Store.class);
    }
}

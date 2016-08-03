package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Store>>{

    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO replace it with the loaded from internet
        stores = new ArrayList<>();
        stores.add(new Store(0, "Babooshka", "ulitsa Kutsygina, 35/1, Voronezh", "8 (473) 277-78-58", null));
        stores.add(new Store(1, "Liubava", "ulitsa 20-letiya Oktabrya, 52, Voronezh", "8 (473) 277-35-29", null));
        updateStoreRecyclerFragment(stores);

    }

    private void updateStoreRecyclerFragment(ArrayList<Store> stores){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, StoreRecyclerFragment.newInstance(stores))
                .commit();
    }

    @Override
    public Loader<ArrayList<Store>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Store>> loader) {

    }
}

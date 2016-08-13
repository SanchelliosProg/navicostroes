package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tstasks.sanchellios.navicostores.maps.MapUtils;
import com.tstasks.sanchellios.navicostores.email_sending.EmailFragment;
import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.networking.StoreLoader;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Store>>,
        StoreInfoAvailabilityListener,
        Refreshable, OnMapReadyCallback {

    private final String STORES_LOADED_STATE = "STORES_LOADED_STATE";
    private final String STORES_LIST = "STORES_LIST";
    private Fragment currentFragment;
    private MenuItem sendMailMenuButton;
    private final LatLng START_POSITION = new LatLng(51.675459, 39.208926);
    private final int ZOOM = 10;
    private GoogleMap map;

    private boolean isStoresLoaded = false;
    public final int LOAD_STORES = 0;

    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.list_of_stores_title);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            createMapFragment();
        }

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
        return createStoreLoader();
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> stores) {
        this.stores = stores;
        startStoreRecyclerFragment(this.stores);
        isStoresLoaded = true;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            populateMap();
            updateMapCenter();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Store>> loader) {

    }

    private void startStoreRecyclerFragment(ArrayList<Store> stores){
        sendMailMenuButton.setVisible(false);
        currentFragment = StoreRecyclerFragment.newInstance(stores);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, currentFragment)
                .addToBackStack(null)
                .commit();
    }

    public void startEmailFragment(String email){
        sendMailMenuButton.setVisible(true);
        currentFragment = EmailFragment.newInstance(email);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, currentFragment)
                .addToBackStack(null)
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

    @Override
    public void callEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.emailing_is_impossible)
                .setMessage(R.string.no_email);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startEmailFragment(null);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        sendMailMenuButton = menu.findItem(R.id.send_mail_button);
        if(currentFragment instanceof EmailFragment){
            sendMailMenuButton.setVisible(true);
        }else {
            sendMailMenuButton.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.send_mail_button:
                ((EmailFragment)currentFragment).trySendEmail();
                break;
            case R.id.refresh_button:
                startStoreRecyclerFragment(stores);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void refresh() {
        startStoreRecyclerFragment(stores);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        CameraPosition position = CameraPosition.builder().target(START_POSITION).zoom(ZOOM).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        //map.addMarker(new MarkerOptions().position(VORONEZH).title(currentStore.getName()));
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }

    private void createMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.main_activity_map);
        mapFragment.getMapAsync(this);
    }

    private void populateMap(){
        for(Store store : stores){
            map.addMarker(new MarkerOptions().position(store.getLatLng()).title(store.getName()));
        }
    }

    private void updateMapCenter(){
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Store store : stores){
            latLngs.add(store.getLatLng());
        }
        CameraPosition position = CameraPosition.builder().target(MapUtils.getCenterLatLng(latLngs)).zoom(ZOOM).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
}

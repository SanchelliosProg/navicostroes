package com.tstasks.sanchellios.navicostores.display_store_details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.data_binders.StoreBinder;
import com.tstasks.sanchellios.navicostores.data_binders.StoreDataBindAdapter;
import com.tstasks.sanchellios.navicostores.databinding.ActivityDetailsBinding;
import com.tstasks.sanchellios.navicostores.email_sending.SendEmailIntentProvider;
import com.tstasks.sanchellios.navicostores.phone_calls.PhoneCallIntentProvider;
import com.tstasks.sanchellios.navicostores.store_data.InstrumentProfile;
import com.tstasks.sanchellios.navicostores.store_data.Location;
import com.tstasks.sanchellios.navicostores.store_data.Store;
import com.tstasks.sanchellios.navicostores.website.WebSiteIntentProvider;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final int zoomLevel = 16;
    public static String STORE = "STORE";
    public static String LOCATION = "LOCATION";
    public static String INSTRUMENTS = "INSTRUMENTS";
    private final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    private int CURRENT_FRAGMENT_STATE = 0;
    private int STORE_CONTACTS_FRAGMENT = 0;
    private int INSTRUMENTS_LIST_FRAGMENT = 1;

    private Store currentStore;
    private LatLng storeLatLng;
    private GoogleApiClient client;
    private Fragment currentFragment;
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if(savedInstanceState == null){
            initStore();
        }else {
            currentStore = savedInstanceState.getParcelable(STORE);
        }

        setupActionBar();
        initStoreLatLng(currentStore.getLocation());
        createMapFragment();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        final StoreBinder storeBinder = new StoreDataBindAdapter().toStoreBinder(getApplicationContext(), currentStore);
        binding.setStore(storeBinder);



            if(savedInstanceState == null){
                startStoreContactsFragment();
            }else {
                CURRENT_FRAGMENT_STATE = savedInstanceState.getInt(CURRENT_FRAGMENT);
                if(CURRENT_FRAGMENT_STATE == STORE_CONTACTS_FRAGMENT){
                    startStoreContactsFragment();
                }else {
                    startInstrumentListFragment();
                    binding.numOfInstrumentsButton.setText(R.string.to_details);
                }
            }



        binding.numOfInstrumentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFragment instanceof StoreContactsFragment){
                    startInstrumentListFragment();
                    binding.numOfInstrumentsButton.setText(R.string.to_details);
                }else {
                    startStoreContactsFragment();
                    binding.setStore(storeBinder);
                }
            }
        });
    }

    private void setupActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setTitle(currentStore.getName());
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void createMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        CameraPosition position = CameraPosition.builder().target(storeLatLng).zoom(zoomLevel).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        map.addMarker(new MarkerOptions().position(storeLatLng).title(currentStore.getName()));
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
        checkIfButtonStringIsRight();
    }

    private void checkIfButtonStringIsRight(){
        if(CURRENT_FRAGMENT_STATE == INSTRUMENTS_LIST_FRAGMENT){
            binding.numOfInstrumentsButton.setText(R.string.to_details);
        }
    }

    private void initStore() {
        Store store = getIntent().getParcelableExtra(STORE);
        Location location = getIntent().getParcelableExtra(LOCATION);
        ArrayList<InstrumentProfile> instruments = getIntent().getParcelableArrayListExtra(INSTRUMENTS);
        currentStore = new Store(store, location, instruments);
    }

    private void initStoreLatLng(Location location) {
        storeLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void startStoreContactsFragment(){
        CURRENT_FRAGMENT_STATE = STORE_CONTACTS_FRAGMENT;
        currentFragment = StoreContactsFragment.newInstance(currentStore);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_details_container, currentFragment)
                .addToBackStack(null)
                .commit();
    }

    private void startInstrumentListFragment(){
        CURRENT_FRAGMENT_STATE = INSTRUMENTS_LIST_FRAGMENT;
        currentFragment = InstrumentsListFragment.newInstance(currentStore.getInstruments());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_details_container, currentFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Details Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.tstasks.sanchellios.navicostores/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Details Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.tstasks.sanchellios.navicostores/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.datailed_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.d("Back", "pressed");
                Intent homeIntent = new Intent(this, DetailsActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.phone_call_details_menu_button:
                phoneCall();
                break;
            case R.id.web_site_details_menu_button:
                visitWebSite();
                break;
            case R.id.send_email_details_menu_button:
                sendEmail();
                break;
            default:
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    //TODO: Create new class for following three methods
    private void phoneCall(){
        startActivity(PhoneCallIntentProvider.getPhoneCallIntent(currentStore.getPhone()));
    }

    private void visitWebSite(){
        startActivity(WebSiteIntentProvider.getWebSiteIntent(currentStore));
    }

    private void sendEmail(){
        startActivity(SendEmailIntentProvider.getIntent(new String[]{currentStore.getEmail()}));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STORE, currentStore);
        outState.putInt(CURRENT_FRAGMENT, CURRENT_FRAGMENT_STATE);
    }
}

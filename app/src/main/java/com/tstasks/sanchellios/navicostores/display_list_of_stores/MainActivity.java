package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.tstasks.sanchellios.navicostores.maps.MapStateRegister;
import com.tstasks.sanchellios.navicostores.maps.MapUtils;
import com.tstasks.sanchellios.navicostores.email_sending.EmailFragment;
import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.networking.StoreLoader;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Store>>,
        StoreInfoAvailabilityListener,
        Refreshable, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public final int STORES_LOADER_ID = 0;

    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private final String MAP_MODE_STATE = "MAP_MODE_STATE";
    private final String STORES_LOADED_STATE = "STORES_LOADED_STATE";
    private final String STORES_LIST = "STORES_LIST";
    private final String USER_LOCATION = "USER_LOCATION";

    private final int LIST_OF_STORES_MODE = 0;
    private final int USER_LOCATION_MODE = 1;

    private int mapModeState = 0;
    private boolean isLocationIconFailedToChange;
    private boolean isStoresLoaded;

    private Fragment currentFragment;
    private MenuItem sendMailMenuButton;
    private MenuItem getLocationButton;

    private final LatLng START_POSITION = new LatLng(51.675459, 39.208926);
    private final int ZOOM = 10;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location userPrevLocation;
    private Location usersLastLocation;
    private MapStateRegister mapStateRegister;

    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapStateRegister = new MapStateRegister();
        setTitle(R.string.list_of_stores_title);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            createMapFragment();
        }

        if (savedInstanceState != null) {
            mapModeState = savedInstanceState.getInt(MAP_MODE_STATE);
            isStoresLoaded = savedInstanceState.getBoolean(STORES_LOADED_STATE);
            stores = savedInstanceState.getParcelableArrayList(STORES_LIST);
            userPrevLocation = savedInstanceState.getParcelable(USER_LOCATION);
        } else {
            stores = new ArrayList<>();
        }

        if (!isStoresLoaded) {
            getLoaderManager().initLoader(STORES_LOADER_ID, null, this).forceLoad();
        }

        initGoogleApiClient();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void refresh() {
        startStoreRecyclerFragment(stores);
    }

    private void startStoreRecyclerFragment(ArrayList<Store> stores) {
        try {
            sendMailMenuButton.setVisible(false);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        currentFragment = StoreRecyclerFragment.newInstance(stores);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, currentFragment)
                .addToBackStack(null)
                .commit();
    }

    public void startEmailFragment(String email) {
        sendMailMenuButton.setVisible(true);
        currentFragment = EmailFragment.newInstance(email);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_container, currentFragment)
                .addToBackStack(null)
                .commit();
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
    public Loader<ArrayList<Store>> onCreateLoader(int i, Bundle bundle) {
        return createStoreLoader();
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> stores) {
        this.stores = stores;
        startStoreRecyclerFragment(this.stores);
        isStoresLoaded = true;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            populateMap();
            updateMapCenter();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Store>> loader) {

    }

    private StoreLoader createStoreLoader() {
        return new StoreLoader(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        sendMailMenuButton = menu.findItem(R.id.send_mail_button);
        getLocationButton = menu.findItem(R.id.get_location_button);

        if (isLocationIconFailedToChange) {
            if (mapModeState == LIST_OF_STORES_MODE) {
                getLocationButton.setIcon(R.drawable.ic_location_searching_white_24dp);
            } else {
                getLocationButton.setIcon(R.drawable.ic_location_disabled_white_24dp);
            }
        }

        if (currentFragment instanceof EmailFragment) {
            sendMailMenuButton.setVisible(true);
        } else {
            sendMailMenuButton.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_mail_button:
                ((EmailFragment) currentFragment).trySendEmail();
                break;
            case R.id.refresh_button:
                startStoreRecyclerFragment(stores);
                break;
            case R.id.get_location_button:
                if (mapModeState == LIST_OF_STORES_MODE) {
                    startUserLocationMapMode();
                } else {
                    startListOfStoresMapMode();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void createMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.main_activity_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        moveCamera(START_POSITION, ZOOM, map);
        setZoomControlOnMap(map);
        if (mapModeState == LIST_OF_STORES_MODE) {
            startListOfStoresMapMode();
        } else {
            startUserLocationMapMode();
        }
    }

    private void moveCamera(LatLng coordinates, int zoom, GoogleMap map) {
        CameraPosition position = CameraPosition.builder().target(coordinates).zoom(zoom).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private void startUserLocationMapMode() {
        mapStateRegister.setPopulated(false);
        mapStateRegister.setNewCenterSet(false);
        requestLocationPermission();
        setMyLocation(this.map);
        try {
            moveCamera(getUsersCoordinates(usersLastLocation), ZOOM, this.map);
        } catch (NullPointerException ex) {
            moveCamera(getUsersCoordinates(userPrevLocation), ZOOM, this.map);
        }

        try {
            getLocationButton.setIcon(R.drawable.ic_location_disabled_white_24dp);
            isLocationIconFailedToChange = false;
        } catch (NullPointerException ex) {
            isLocationIconFailedToChange = true;
        }
        mapModeState = USER_LOCATION_MODE;
    }

    private void startListOfStoresMapMode() {
        setListOfStoresOnMap();
        try {
            getLocationButton.setIcon(R.drawable.ic_location_searching_white_24dp);
            isLocationIconFailedToChange = false;
        } catch (NullPointerException ex) {
            isLocationIconFailedToChange = true;
        }
        mapModeState = LIST_OF_STORES_MODE;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 1);
            }
        }
    }

    private void setZoomControlOnMap(GoogleMap map) {
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }

    private void setListOfStoresOnMap() {
        if (!mapStateRegister.isPopulated())
            populateMap();

        if (!mapStateRegister.isNewCenterSet())
            updateMapCenter();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.clear();
            map.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (!map.isMyLocationEnabled()) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    private void populateMap() {
        for (Store store : stores) {
            map.addMarker(new MarkerOptions().position(store.getLatLng()).title(store.getName()));
        }
        mapStateRegister.setPopulated(true);
    }

    private void updateMapCenter() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Store store : stores) {
            latLngs.add(store.getLatLng());
        }
        CameraPosition position = CameraPosition.builder().target(MapUtils.getCenterLatLng(latLngs)).zoom(ZOOM).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        mapStateRegister.setNewCenterSet(true);
    }

    private void initGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
        usersLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    private LatLng getUsersCoordinates(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STORES_LOADED_STATE, isStoresLoaded);
        outState.putParcelableArrayList(STORES_LIST, stores);
        outState.putInt(MAP_MODE_STATE, mapModeState);
        if(usersLastLocation == null){
            Log.d(USER_LOCATION, "was null");
            outState.putParcelable(USER_LOCATION, userPrevLocation);
        }else {
            Log.d(USER_LOCATION, "was not null");
            outState.putParcelable(USER_LOCATION, usersLastLocation);
        }

    }
}

package com.tstasks.sanchellios.navicostores;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import com.tstasks.sanchellios.navicostores.store_data.InstrumentProfile;
import com.tstasks.sanchellios.navicostores.store_data.Location;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final int zoomLevel = 16;
    public static String STORE = "STORE";
    public static String LOCATION = "LOCATION";
    public static String INSTRUMENTS = "INSTRUMENTS";
    private Store currentStore;
    private LatLng storeLatLng;
    private GoogleMap map;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initStore();
        initStoreLatLng(currentStore.getLocation());
        createMapFragment();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        CameraPosition position = CameraPosition.builder().target(storeLatLng).zoom(zoomLevel).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        map.addMarker(new MarkerOptions().position(storeLatLng).title(currentStore.getName()));
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }

    private void initStore() {
        Store store = getIntent().getParcelableExtra(STORE);
        Location location = getIntent().getParcelableExtra(LOCATION);
        ArrayList<InstrumentProfile> instruments = getIntent().getParcelableExtra(INSTRUMENTS);
        currentStore = new Store(store, location, instruments);
    }

    private void initStoreLatLng(Location location) {
        storeLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void createMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
}

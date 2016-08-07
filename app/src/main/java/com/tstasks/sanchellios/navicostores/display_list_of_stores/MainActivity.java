package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tstasks.sanchellios.navicostores.email_sending.EmailFragment;
import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.networking.StoreLoader;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Store>>,
        StoreInfoAvailabilityListener,
        Refreshable {

    private final String STORES_LOADED_STATE = "STORES_LOADED_STATE";
    private final String STORES_LIST = "STORES_LIST";
    private Fragment currentFragment;
    private Menu menu;
    private MenuItem sendMailMenuButton;

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
        return createStoreLoader();
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Store>> loader, ArrayList<Store> stores) {
        this.stores = stores;
        startStoreRecyclerFragment(this.stores);
        isStoresLoaded = true;
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
    public void callWebsiteDialog() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;
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
}

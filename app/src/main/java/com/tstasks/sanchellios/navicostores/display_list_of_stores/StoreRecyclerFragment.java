package com.tstasks.sanchellios.navicostores.display_list_of_stores;


import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.store_data.Store;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreRecyclerFragment extends Fragment {
    public static final String STORES_KEY = "STORES";
    private ArrayList<Store> stores;
    private final String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_PHONE_STATE};

    public StoreRecyclerFragment() {
        // Required empty public constructor
    }

    public static StoreRecyclerFragment newInstance(ArrayList<Store> stores){
        Bundle bundle = new Bundle();
        StoreRecyclerFragment fragment = new StoreRecyclerFragment();
        bundle.putParcelableArrayList(STORES_KEY, stores);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, 0);
        }

        RecyclerView recyclerView =
                (RecyclerView) inflater.inflate(R.layout.fragment_store_recycler, container, false);
        stores = getArguments().getParcelableArrayList(STORES_KEY);


        StoreRecyclerAdapter adapter = new StoreRecyclerAdapter(stores, getContext());
        recyclerView.setAdapter(adapter);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);
        }else {
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(manager);
        }

        return recyclerView;
    }

}

package com.tstasks.sanchellios.navicostores;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreRecyclerFragment extends Fragment {


    public StoreRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView =
                (RecyclerView) inflater.inflate(R.layout.fragment_store_recycler, container, false);
        ArrayList<Store> mStores = new ArrayList<>();
        mStores.add(new Store(0, "Babooshka", "xxx", "02", null));
        mStores.add(new Store(1, "Liubava", "yyy", "03", null));

        StoreAdapter adapter = new StoreAdapter(mStores);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        return recyclerView;
    }

}

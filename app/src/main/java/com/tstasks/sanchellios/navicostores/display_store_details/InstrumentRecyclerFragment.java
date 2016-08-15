package com.tstasks.sanchellios.navicostores.display_store_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.store_data.Instrument;
import com.tstasks.sanchellios.navicostores.store_data.InstrumentProfile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstrumentRecyclerFragment extends Fragment {

    private static final String PROFILES = "PROFILES";

    public InstrumentRecyclerFragment() {
        // Required empty public constructor
    }

    public static InstrumentRecyclerFragment newInstance(ArrayList<InstrumentProfile> profiles){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PROFILES, profiles);
        InstrumentRecyclerFragment fragment = new InstrumentRecyclerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView instrumentRecycler = (RecyclerView) inflater
                .inflate(R.layout.fragment_instrument_recycler, container, false);
        ArrayList<InstrumentProfile> profiles = getArguments().getParcelableArrayList(PROFILES);

        InstrumentsRecyclerAdapter adapter = new InstrumentsRecyclerAdapter(profiles);
        instrumentRecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        instrumentRecycler.setLayoutManager(layoutManager);

        return instrumentRecycler;
    }

}

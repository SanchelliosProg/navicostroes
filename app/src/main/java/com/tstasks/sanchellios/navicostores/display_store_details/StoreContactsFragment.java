package com.tstasks.sanchellios.navicostores.display_store_details;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.binders.StoreBinder;
import com.tstasks.sanchellios.navicostores.binders.StoreDataBindAdapter;
import com.tstasks.sanchellios.navicostores.databinding.FragmentStoreContactsBinding;
import com.tstasks.sanchellios.navicostores.store_data.Store;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreContactsFragment extends Fragment {

    private static final String STORE = "STORE";
    private FragmentStoreContactsBinding binding;

    public StoreContactsFragment() {
        // Required empty public constructor
    }

    public static StoreContactsFragment newInstance(Store store){
        Bundle bundle = new Bundle();
        bundle.putParcelable(STORE, store);
        StoreContactsFragment fragment = new StoreContactsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_contacts, container, false);
        View view = binding.getRoot();
        Store currentStore = getArguments().getParcelable(STORE);
        StoreBinder storeBinder = new StoreDataBindAdapter().toStoreBinder(getContext(), currentStore);
        binding.setStore(storeBinder);
        return view;
    }

}

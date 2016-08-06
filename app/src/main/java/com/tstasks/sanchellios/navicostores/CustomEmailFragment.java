package com.tstasks.sanchellios.navicostores;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.tstasks.sanchellios.navicostores.databinding.FragmentCustomEmailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomEmailFragment extends Fragment {

    FragmentCustomEmailBinding binding;
    public CustomEmailFragment() {
        // Required empty public constructor
    }

    public static CustomEmailFragment newInstance(){
        return new CustomEmailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_email, container, false);
        binding.customEmailEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        return inflater.inflate(R.layout.fragment_custom_email, container, false);
    }

}

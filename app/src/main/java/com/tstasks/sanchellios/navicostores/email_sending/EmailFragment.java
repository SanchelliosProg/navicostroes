package com.tstasks.sanchellios.navicostores.email_sending;



import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.databinding.FragmentEmailBinding;
import com.tstasks.sanchellios.navicostores.display_list_of_stores.Refreshable;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment {
    private static final String EMAIL_TAG = "EMAIL_TAG";
    private EditText emailEditText;
    private FragmentEmailBinding binding;
    private String emailAddress;
    private Refreshable refreshable;
    private FloatingActionButton fab;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        refreshable = (Refreshable)context;
    }

    public EmailFragment() {
        // Required empty public constructor
    }

    public static EmailFragment newInstance(String emailAddress){
        EmailFragment fragment = new EmailFragment();
        if(emailAddress != null){
            Bundle bundle = new Bundle();
            bundle.putString(EMAIL_TAG, emailAddress);
            fragment.setArguments(bundle);
        }

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false);
        View view = binding.getRoot();
        emailEditText = binding.emailEditText;
        if(getArguments()!=null){
            emailAddress = getArguments().getString(EMAIL_TAG);
            emailEditText.setText(emailAddress);
        }else {
            emailEditText.requestFocus();
            emailEditText.setHint(R.string.enter_email);
        }
        return view;
    }

    public void trySendEmail(){
        sendEmail();
    }

    public void sendEmail(){
        startActivity(SendEmailIntentProvider
                .getIntent(new String[]{emailEditText.getText().toString()}));
        refreshable.refresh();
    }

}

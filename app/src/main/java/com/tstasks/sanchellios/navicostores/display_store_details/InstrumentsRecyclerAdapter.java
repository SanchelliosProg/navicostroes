package com.tstasks.sanchellios.navicostores.display_store_details;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstasks.sanchellios.navicostores.data_binders.InstrumentBinder;
import com.tstasks.sanchellios.navicostores.databinding.ListOfInstrumentsCardViewBinding;
import com.tstasks.sanchellios.navicostores.store_data.Instrument;
import com.tstasks.sanchellios.navicostores.store_data.InstrumentProfile;

import java.util.ArrayList;

public class InstrumentsRecyclerAdapter extends RecyclerView.Adapter<InstrumentsRecyclerAdapter.ViewHolder> {

    private ArrayList<InstrumentProfile> profiles;

    public InstrumentsRecyclerAdapter(ArrayList<InstrumentProfile> profiles){
        this.profiles = profiles;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ListOfInstrumentsCardViewBinding binding;

        public ViewHolder(View view){
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    @Override
    public InstrumentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListOfInstrumentsCardViewBinding binding = ListOfInstrumentsCardViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InstrumentProfile profile = profiles.get(position);
        Instrument instrument = profile.getInstrument();
        holder.binding.setInstrument(new InstrumentBinder(instrument));
        holder.binding.setProfile(profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}

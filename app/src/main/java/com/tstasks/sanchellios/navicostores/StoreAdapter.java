package com.tstasks.sanchellios.navicostores;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tstasks.sanchellios.navicostores.binders.StoreDataBindAdapter;
import com.tstasks.sanchellios.navicostores.databinding.StoreCardViewBinding;

import java.util.ArrayList;

/**
 * Created by alex on 01.08.16.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private ArrayList<Store> stores = new ArrayList<>();

    public StoreAdapter(ArrayList<Store> stores){
        this.stores = stores;
    }

    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StoreCardViewBinding binding = StoreCardViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store currentStore = this.stores.get(position);
        holder.binding.setStore(StoreDataBindAdapter.toStoreBinder(currentStore));
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public StoreCardViewBinding binding;
        public ViewHolder(View view){
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}

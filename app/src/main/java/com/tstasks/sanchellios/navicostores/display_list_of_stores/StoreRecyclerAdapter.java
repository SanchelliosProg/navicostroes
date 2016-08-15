package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstasks.sanchellios.navicostores.display_store_details.DetailsActivity;
import com.tstasks.sanchellios.navicostores.website.WebSiteIntentProvider;
import com.tstasks.sanchellios.navicostores.phone_calls.PhoneCallIntentProvider;
import com.tstasks.sanchellios.navicostores.store_data.Store;
import com.tstasks.sanchellios.navicostores.data_binders.StoreDataBindAdapter;
import com.tstasks.sanchellios.navicostores.databinding.StoreCardViewBinding;

import java.util.ArrayList;

/**
 * Created by alex on 01.08.16.
 */
public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.ViewHolder> {
    private ArrayList<Store> stores = new ArrayList<>();
    private Context context;
    private StoreInfoAvailabilityListener availabilityListener;

    public StoreRecyclerAdapter(ArrayList<Store> stores, Context context) {
        this.context = context;
        this.stores = stores;
        this.availabilityListener = (StoreInfoAvailabilityListener)context;
    }

    @Override
    public StoreRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StoreCardViewBinding binding = StoreCardViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Store currentStore = this.stores.get(position);
        CardView cardView = holder.binding.storeCard;
        holder.binding.setStore(new StoreDataBindAdapter().toStoreBinder(context, currentStore));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CardView", "is clicked");
                startDetailedActivity(currentStore);
            }
        });
        setListenersToButtons(holder, currentStore);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }


    private void startDetailedActivity(Store currentStore){
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(DetailsActivity.STORE, currentStore);
        intent.putExtra(DetailsActivity.LOCATION, currentStore.getLocation());
        intent.putExtra(DetailsActivity.INSTRUMENTS, currentStore.getInstruments());
        context.startActivity(intent);
    }


    private void setListenersToButtons(ViewHolder holder, final Store currentStore){
        holder.binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(PhoneCallIntentProvider.getPhoneCallIntent(currentStore.getPhone()));
            }
        });

        holder.binding.webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(WebSiteIntentProvider.getWebSiteIntent(currentStore));
            }
        });

        holder.binding.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentStore.getEmail() != null){
                    availabilityListener.startEmailFragment(currentStore.getEmail());
                }else {
                    Log.d("Store e-mail", "null");
                    availabilityListener.callEmailDialog();
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public StoreCardViewBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}

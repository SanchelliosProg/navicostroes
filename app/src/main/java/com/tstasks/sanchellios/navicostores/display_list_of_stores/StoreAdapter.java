package com.tstasks.sanchellios.navicostores.display_list_of_stores;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tstasks.sanchellios.navicostores.website.WebSiteIntentProvider;
import com.tstasks.sanchellios.navicostores.phone_calls.PhoneCallIntentProvider;
import com.tstasks.sanchellios.navicostores.store_data.Store;
import com.tstasks.sanchellios.navicostores.binders.StoreDataBindAdapter;
import com.tstasks.sanchellios.navicostores.databinding.StoreCardViewBinding;

import java.util.ArrayList;

/**
 * Created by alex on 01.08.16.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private ArrayList<Store> stores = new ArrayList<>();
    private Context context;
    private StoreInfoAvailabilityListener availabilityListener;
    private PhoneCallIntentProvider phoneCallIntentProvider;
    private WebSiteIntentProvider webSiteIntentProvider;

    public interface StoreInfoAvailabilityListener {
        void callEmailDialog();
        void callWebsiteDialog();
    }

    public StoreAdapter(ArrayList<Store> stores, Context context) {
        this.context = context;
        this.stores = stores;
        phoneCallIntentProvider = new PhoneCallIntentProvider();
        webSiteIntentProvider = new WebSiteIntentProvider();
        this.availabilityListener = (StoreInfoAvailabilityListener)context;
    }

    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StoreCardViewBinding binding = StoreCardViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Store currentStore = this.stores.get(position);
        holder.binding.setStore(StoreDataBindAdapter.toStoreBinder(currentStore));
        setListenersToButtons(holder, currentStore);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public StoreCardViewBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    private void setListenersToButtons(ViewHolder holder, final Store currentStore){
        holder.binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(phoneCallIntentProvider.getPhoneCallIntent(currentStore.getPhone()));
            }
        });

        holder.binding.webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(webSiteIntentProvider.getWebSiteIntent(currentStore));
            }
        });

        holder.binding.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentStore.getEmail() != null){
                    //TODO: get to the screen of email
                }else {
                    Log.d("Store e-mail", "null");
                    availabilityListener.callEmailDialog();
                }
            }
        });
    }
}

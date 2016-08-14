package com.tstasks.sanchellios.navicostores.data_binders;

import android.content.Context;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.store_data.Store;

/**
 * Created by alex on 02.08.16.
 */
public class StoreDataBindAdapter {
    private Context context;
    public StoreBinder toStoreBinder(Context context, Store store){
        this.context = context;
        return new StoreBinder(
                investigateStringNull(store.getName()),
                investigateStringNull(store.getAddress()),
                investigateStringNull(store.getPhone()),
                investigateStringNull(store.getWebsite()),
                investigateStringNull(store.getEmail()),
                store.getNumberOfInstruments());
    }

    private String investigateStringNull(String string){
        if(string == null){
            return context.getResources().getString(R.string.no_data);
        }else {
            return string;
        }
    }


}

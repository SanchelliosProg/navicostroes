package com.tstasks.sanchellios.navicostores.data_binders;

import android.content.Context;

import com.tstasks.sanchellios.navicostores.R;
import com.tstasks.sanchellios.navicostores.Transliterator;
import com.tstasks.sanchellios.navicostores.store_data.Store;

/**
 * Created by alex on 02.08.16.
 */
public class StoreDataBindAdapter {
    private Context context;
    public StoreBinder toStoreBinder(Context context, Store store){
        this.context = context;
        return new StoreBinder(
                adoptString(store.getName()),
                adoptString(store.getAddress()),
                adoptString(store.getPhone()),
                adoptString(store.getWebsite()),
                adoptString(store.getEmail()),
                store.getNumberOfInstruments());
    }

    private String adoptString(String string){
        if(string == null){
            return context.getResources().getString(R.string.no_data);
        }else {
            return Transliterator.getRussianString(string);
        }
    }


}

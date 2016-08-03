package com.tstasks.sanchellios.navicostores.binders;

import com.tstasks.sanchellios.navicostores.store_data.Store;

/**
 * Created by alex on 02.08.16.
 */
public class StoreDataBindAdapter {
    public static StoreBinder toStoreBinder(Store store){
        return new StoreBinder(store.getName(), store.getAddress(), store.getPhone());
    }
}

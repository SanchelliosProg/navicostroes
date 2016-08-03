package com.tstasks.sanchellios.navicostores;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by alex on 03.08.16.
 */
public class StoreLoader extends AsyncTaskLoader<String> {

    public StoreLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        return null;
    }

}

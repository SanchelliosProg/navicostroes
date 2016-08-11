package com.tstasks.sanchellios.navicostores.website;

import android.content.Intent;
import android.net.Uri;

import com.tstasks.sanchellios.navicostores.store_data.Store;

/**
 * Created by alex on 06.08.16.
 */
public class WebSiteIntentProvider {
    private static final String GOOGLE_SEARCH = "https://www.google.ru/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#newwindow=1&q=";

    public WebSiteIntentProvider(){}

    public static Intent getWebSiteIntent(Store store){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(store.getWebsite() != null){
            intent.setData(Uri.parse(store.getWebsite().toString()));
        }else {
            intent.setData(Uri.parse(getGoogleSearchUrl(store.getName())));
        }
        return intent;
    }

    private static String getGoogleSearchUrl(String storeName){
        return GOOGLE_SEARCH + storeName;
    }
}

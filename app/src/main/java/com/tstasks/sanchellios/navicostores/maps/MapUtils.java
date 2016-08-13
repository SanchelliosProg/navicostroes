package com.tstasks.sanchellios.navicostores.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by alex on 13.08.16.
 */
public class MapUtils {

    public static LatLng getCenterLatLng(ArrayList<LatLng> latLngs){
        int size = latLngs.size();
        double latitudesSum = 0;
        double longitudesSum = 0;
        for (LatLng latLng : latLngs){
            latitudesSum += latLng.latitude;
            longitudesSum += latLng.longitude;
        }
        return new LatLng((latitudesSum/size), (longitudesSum/size));

    }

}

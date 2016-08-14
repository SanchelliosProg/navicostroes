package com.tstasks.sanchellios.navicostores.maps;

/**
 * Created by alex on 14.08.16.
 */
public class MapStateRegister {
    private boolean isPopulated;
    private boolean isNewCenterSet;

    public MapStateRegister(){}


    public boolean isPopulated() {
        return isPopulated;
    }

    public void setPopulated(boolean populated) {
        isPopulated = populated;
    }

    public boolean isNewCenterSet() {
        return isNewCenterSet;
    }

    public void setNewCenterSet(boolean newCenterSet) {
        isNewCenterSet = newCenterSet;
    }
}

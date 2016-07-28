package com.tstasks.sanchellios.navicostores;

/**
 * Created by alex on 28.07.16.
 */
public class InstrumentProfile {
    private Instrument instrument;
    private int quantity;

    public InstrumentProfile(Instrument instrument, int quantity){
        this.setInstrument(instrument);
        this.setQuantity(quantity);
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

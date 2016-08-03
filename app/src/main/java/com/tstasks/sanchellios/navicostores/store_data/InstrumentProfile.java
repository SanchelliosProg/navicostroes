package com.tstasks.sanchellios.navicostores.store_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 28.07.16.
 */
public class InstrumentProfile implements Parcelable{
    private Instrument instrument;
    private int quantity;

    public InstrumentProfile(Instrument instrument, int quantity){
        this.setInstrument(instrument);
        this.setQuantity(quantity);
    }

    protected InstrumentProfile(Parcel in) {
        instrument = in.readParcelable(Instrument.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<InstrumentProfile> CREATOR = new Creator<InstrumentProfile>() {
        @Override
        public InstrumentProfile createFromParcel(Parcel in) {
            return new InstrumentProfile(in);
        }

        @Override
        public InstrumentProfile[] newArray(int size) {
            return new InstrumentProfile[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(instrument, i);
        parcel.writeInt(quantity);
    }
}

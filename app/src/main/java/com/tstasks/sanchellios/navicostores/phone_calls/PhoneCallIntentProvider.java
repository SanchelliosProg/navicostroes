package com.tstasks.sanchellios.navicostores.phone_calls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by alex on 06.08.16.
 */
public class PhoneCallIntentProvider {
    private static PhoneFormatter formatter = new PhoneFormatter();

    public static Intent getPhoneCallIntent(String phoneNumber) {
        String formattedPhoneNumber = formatter.getReformattedPhone(phoneNumber);
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + formattedPhoneNumber));
        Log.i("Phone number", formattedPhoneNumber);
        return phoneIntent;
    }
}

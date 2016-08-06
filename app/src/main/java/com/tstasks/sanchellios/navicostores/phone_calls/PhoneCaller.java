package com.tstasks.sanchellios.navicostores.phone_calls;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by alex on 06.08.16.
 */
public class PhoneCaller {
    private PhoneFormatter formatter;
    private Context context;
    public PhoneCaller(Context context){
        formatter = new PhoneFormatter();
        this.context = context;
    }
    public void phoneCall(String phoneNumber) {
        String formattedPhoneNumber = formatter.getReformattedPhone(phoneNumber);
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + formattedPhoneNumber));
        Log.i("Phone number", formattedPhoneNumber);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(phoneIntent);
    }

}

package com.tstasks.sanchellios.navicostores.email_sending;

import android.content.Intent;

/**
 * Created by alex on 07.08.16.
 */
public class SendEmailIntentProvider {
    public static Intent getIntent(String[] emails){
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);
        emailIntent.setType("text/plain");
        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
        return emailIntent;
    }
}

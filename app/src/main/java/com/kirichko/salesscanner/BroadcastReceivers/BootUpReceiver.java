package com.kirichko.salesscanner.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.kirichko.salesscanner.Services.ScannerAndUpdateService;

/**
 * Created by Киричко on 10.08.2015.
 */
public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ScannerAndUpdateService.class);
        context.startService(serviceIntent);
    }
}

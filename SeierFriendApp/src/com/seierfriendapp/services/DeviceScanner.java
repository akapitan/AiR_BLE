package com.seierfriendapp.services;

import android.content.Context;
import android.content.Intent;

/**
 * Created by matha on 22.01.15..
 * Abstract class that implements OuterDeviceScan interface and
 * overrides notifyOnScan method that notifies ScaningService using
 * broadcast intent.
 */
public abstract class DeviceScanner implements OuterDevicesScan {
    /**
     * Method that notifies ScanningService that device has been found (scanned)
     *
     * @param applicationContext current aplication context
     */
    @Override
    public void notifyOnScan(Context applicationContext) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.example.action.DEVICE_SCANNED");
        applicationContext.sendBroadcast(broadcastIntent);
    }
}

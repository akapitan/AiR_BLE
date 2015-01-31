package com.seierfriendapp.services;


import android.content.Context;

import java.util.List;

public interface OuterDevicesScan {
    /**
     * Method that starts device scan
     *
     * @param ctx         Current application context
     * @param macAddresses List of mac addresses that need to mach
     */
    public void scanForDevices(Context ctx, List<String> macAddresses);

    /**
     * Method that notifies Application on device match.
     *
     * @param applicationContext current application context
     */
    public void notifyOnScan(Context applicationContext);

    /**
     * Method that is used for stopping the scan
     */
    public void stopScan();
}

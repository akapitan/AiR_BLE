package com.example.services;

/**
 * Created by goran on 3.12.2014..
 */

import android.content.Context;

import java.util.List;

/**
 * Created by goran on 2.12.2014..
 */
public interface OuterDevicesScan {
    /**
     * Method that starts device scan
     *
     * @param ctx         Current application context
     * @param macAdresses List of mac addresses that need to mach
     */
    public void scanForDevices(Context ctx, List<String> macAdresses);

    /**
     * Method that notifies Application on device match.
     */
    public void NotifyOnScan();
}

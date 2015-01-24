package com.example.services;

/**
 * Created by goran on 3.12.2014..
 */
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by goran on 2.12.2014..
 */
public interface OuterDevicesScan {
    /**
     * Method that starts device scan
     * @param ctx Current application context
     * @param macAdresses List of mac addresses that need to mach
     */
    public void scanForDevices(Context ctx, List<String> macAdresses);
    public SparseArray<BluetoothDevice> getDeviceList();

}

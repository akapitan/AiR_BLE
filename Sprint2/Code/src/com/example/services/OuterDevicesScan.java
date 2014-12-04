package com.example.services;

/**
 * Created by goran on 3.12.2014..
 */
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.SparseArray;

/**
 * Created by goran on 2.12.2014..
 */
public interface OuterDevicesScan {
    public void scanForDevices(Context ctx);
    public SparseArray<BluetoothDevice> getDeviceList();

}

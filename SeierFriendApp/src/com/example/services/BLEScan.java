package com.example.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by goran on 1.12.2014..
 */
public class BLEScan implements BluetoothAdapter.LeScanCallback, OuterDevicesScan {

    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothManager manager;
    private SparseArray<BluetoothDevice> mDevices;
    Handler mHandler;
    Context applicationContext;
    //Mac Addresses to filter scanned devices
    private List<String> macAddreses;

    public BLEScan() {
    }

    /**
     * Stop scanning for BLE devices (stop thread)
     */
    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
            startScan(applicationContext);
        }
    };

    /**
     * Method that initiates device scanning, and stops it after 9 seconds
     * HARDCODED TIME VALUE -> CHANGE LATER!!!.
     */
    private void startScan(Context ctx) {
        applicationContext = ctx;
        //get system service for Bluetooth manager
        manager = (BluetoothManager) ctx.getSystemService(ctx.BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        //array that will scanned devices will be stored
        mDevices = new SparseArray<BluetoothDevice>();
        mHandler = new Handler();
        //start scanning
        mBluetoothAdapter.startLeScan(this);
        mHandler.postDelayed(mStopRunnable, 10000);
    }


    /**
     * Method which stops scanning if wasn't stopped by mHandler in startScan method.
     */
    private void stopScan() {
        mBluetoothAdapter.stopLeScan(this);
    }

    // mechanism for making less broadcast events. Every fifth time that scanning service recognise device it will trigger broadcast event
    int j = 4;

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        j++;
        //check if any of MAC addresses match, Break after first match
        for (String i : macAddreses) {
            if (i.equals(device.getAddress().toString())) {
                Log.e("BLEScan", "Device MATCH! " + device.getName() + " @ " + rssi + " MAC: " + device.getAddress().toString());
                //put devices in sparsearray
                mDevices.put(device.hashCode(), device);
                //send broadcast
                //just for slowing down broadcast events
                if (j > 5) {
                    j = 0;
                    NotifyOnScan();
                }
                break;
            } else {
                Log.e("BLEScan", "Device NOT MATCH! " + device.getName() + " @ " + rssi + " MAC: " + device.getAddress().toString());

            }
        }
    }

    /**
     * Starting device scan
     *
     * @param ctx          Current application context
     * @param macAddresses Mac Addresses from local storage
     */
    @Override
    public void scanForDevices(Context ctx, List<String> macAddresses) {
        this.macAddreses = macAddresses;
        startScan(ctx);
    }

    @Override
    public void NotifyOnScan() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.example.action.DEVICE_SCANNED");
        applicationContext.sendBroadcast(broadcastIntent);
    }
}

package com.example.services;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseArray;
import android.os.Handler;


import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created by goran on 1.12.2014..
 */
public class BLEScan implements BluetoothAdapter.LeScanCallback, OuterDevicesScan {

    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothManager manager;
    private SparseArray<BluetoothDevice> mDevices;
    Handler mHandler;
    protected Set<Observer> observers;
    Context applicationContext;
    //Mac Addresses to filter scanned devices
    private List<String> macAddreses;

    public BLEScan() {
    }

    /**
     * Start scanning for BLE devices (in new thread)
     */
    private Runnable mStartRunnable=new Runnable() {
        @Override
        public void run() {
            startScan(applicationContext);
        }
    };

    /**
     * Stop scanning for BLE devices (stop thread)
     */
    private Runnable mStopRunnable=new Runnable() {
        @Override
        public void run() {
            stopScan();
            startScan(applicationContext);
        }
    };

    /**
     * Method that initiates device scanning, and stops it after 2,5 seconds
     * HARDCODED TIME VALUE -> CHANGE LATER!!!.
     */
    private void startScan(Context ctx){
        applicationContext = ctx;
        manager=(BluetoothManager) ctx.getSystemService(ctx.BLUETOOTH_SERVICE);
        mBluetoothAdapter=manager.getAdapter();
        mDevices=new SparseArray<BluetoothDevice>();
        mHandler=new Handler();

        mBluetoothAdapter.startLeScan(this);
        mHandler.postDelayed(mStopRunnable,3000);
    }

    /**
     * Method which stops scanning if wasn't stopped by mHandler in startScan method.
     */
    private void stopScan(){
        mBluetoothAdapter.stopLeScan(this);
    }

    int j = 0;
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        j++;
        for(String i : macAddreses ){
            if(i.equals(device.getAddress().toString())){
                Log.e("BLEScan","Device MATCH! "+device.getName()+" @ " + rssi + " MAC: " + device.getAddress().toString());
                //put devices in sparsearray
                mDevices.put(device.hashCode(),device);
                //send broadcast
                if( j >5) {
                    Intent broadcastIntent = new Intent();
                    //broadcastIntent.setAction("com.example.backgroundScaning.ScaningService.OUTER_DEVICE_SCANNED");
                    broadcastIntent.setAction("com.example.action.DEVICE_SCANNED");
                    applicationContext.sendBroadcast(broadcastIntent);
                }

            }else{
                Log.e("BLEScan","Device NOT MATCH! "+device.getName()+" @ " + rssi + " MAC: " + device.getAddress().toString());
            }
        }

    }

    /**
     * Starting device scan
     * @param ctx Current application context
     * @param macAddresses Mac Addresses from local storage
     */
    @Override
    public void scanForDevices(Context ctx, List<String> macAddresses) {
        this.macAddreses = macAddresses;
        startScan(ctx);
    }

    /**
     * Interface override.
     * @return
     */
    @Override
    public SparseArray<BluetoothDevice> getDeviceList() {
        return mDevices;
    }
}

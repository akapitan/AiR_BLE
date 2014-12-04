package com.example.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.os.Handler;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created by goran on 1.12.2014..
 */
public class BLEScan extends Observable implements BluetoothAdapter.LeScanCallback, OuterDevicesScan {

    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothManager manager;
    private SparseArray<BluetoothDevice> mDevices;
    Handler mHandler;
    protected Set<Observer> observers;
    Context applicationContext;

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


    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.e("BLE","Uredjaj: "+device.getName()+" @ " + rssi + " UUID: " + device.getAddress().toString());
        mDevices.put(device.hashCode(),device);
        //observable ->notify that change occured
        setChanged();
        notifyObservers();
    }

    /**
     * Interface override.
     */
    @Override
    public void scanForDevices(Context ctx) {
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

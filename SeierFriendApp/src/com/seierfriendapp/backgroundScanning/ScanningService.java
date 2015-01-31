package com.seierfriendapp.backgroundScanning;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import com.seierfriendapp.BaseApplication;
import com.seierfriendapp.localdata.Beacon;
import com.seierfriendapp.services.BLEScan;
import com.seierfriendapp.services.DeviceScanner;

import java.util.LinkedList;
import java.util.List;

/**
 * Service that works in backgorund, it launches device scanning
 * and recieves broadcast when device is scanned
 */
public class ScanningService extends Service {
    private Thread mThread;
    private static ScanningService instance = null;
    public List<String> deviceList;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("Service", "OnCreate");
        super.onCreate();
        getInstance();

        //register broadcast reciever
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.action.DEVICE_SCANNED");
        this.registerReceiver(new Receiver(), filter);

        //get mac addresses
        //deviceList = getMacAddresses();

        ///start scanning over the interface
        final DeviceScanner deviceScanner = new BLEScan();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                deviceScanner.scanForDevices(getBaseContext(), deviceList);
            }
        });
        mThread.run();
    }

    /**
     * Singleton. No duplicate services.
     */
    public static ScanningService getInstance() {
        if (instance == null) {
            instance = new ScanningService();
        }
        return instance;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "Servis startCommand");
        return START_STICKY;
    }

    /**
     * Get Mac addresses from local database.
     * MAC addresses are from devices that need to match.
     *
     * @return list of mac addresses of devices from local database
     */
    private List<String> getMacAdresses() {
        Beacon mac = new Beacon();
        List<String> macs = new LinkedList<String>();
        for (Beacon i : mac.getMacAddresses()) {
            macs.add(i.getMac());
            Log.e("MAC ADDRESS", i.getMac().toString());
        }
        return macs;
    }

    /**
     * Receives broadcast that scanning class (currently BLEScan) sent.
     * Then it launches notification launcher
     */
    public static class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("ScanningService", "RECEIVED BROADCAST!");
            //start activity/notification/...
            startAct(BaseApplication.getStaticCurrentContext());
        }
    }

    /**
     * Defines what action is performed on received broadcast signal.
     *
     * @param c application context
     */
    public static void startAct(Context c) {
        //start notification launcher
        NotificationLauncher nl = new NotificationLauncher();
        nl.launchNotification(c);
    }
}

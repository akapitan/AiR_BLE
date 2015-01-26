package com.example.backgroundScanning;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.example.core.BaseApplication;
import com.example.localdata.Beacon;
import com.example.services.BLEScan;
import com.example.services.OuterDevicesScan;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by goran on 21.12.2014..
 * Service that works in backgorund, it launches device scanning
 * and recieves broadcast when device is scanned
 */
public class ScaningService extends Service {
    private Thread mThread;
    private static ScaningService instance=null;
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
        this.registerReceiver(new Reciever(), filter);

        //get mac adresses
        deviceList = getMacAdresses();

        ///start scanning over the interface
        final OuterDevicesScan outerDevicesScan1 = new BLEScan();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                outerDevicesScan1.scanForDevices(getBaseContext(), deviceList);
            }
        });
        mThread.run();
    }

    /**
     * Singleton. No duplicate services.
     */
    public static ScaningService getInstance(){
        if(instance==null){
            instance=new ScaningService();
        }
        return instance;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service","Servis startCommand");
        Toast.makeText(this, "Servis pokrenut", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    /**
     * Get Mac addresses from local database.
     * MAC addresses are from devices that need to match.
     * @return list of mac addresses of devices from local database
     */
    private List<String> getMacAdresses(){
        Beacon mac=new Beacon();
        List<String> macs = new LinkedList<String>();
        for(Beacon i : mac.getMacAdresses()){
            macs.add(i.getMac());
            Log.e("MAC ADDRESS", i.getMac().toString());
        }
        return macs;
    }

    /**
     * Recieves broadcast that scanning class (currently BLEScan) sent.
     * Then it launches notification launcher
     */
    public static class Reciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("ScanningService", "RECIEVED BROADCAST!");
            //start activity/notification/...
            startAct(BaseApplication.getStaticCurrentContext());
        }
    }

    /**
     * Defines what action is performed on received broadcast signal.
     * @param c application context
     */
    public static void startAct(Context c) {
        //start notification launcher
        NotificationLauncher nl = new NotificationLauncher();
        nl.launchNotification(c);
    }
}

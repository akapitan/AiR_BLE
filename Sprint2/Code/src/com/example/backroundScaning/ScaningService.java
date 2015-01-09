package com.example.backroundScaning;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.example.core.BaseApplication;
import com.example.fragments.PointStatusFragment;
import com.example.seierfriendapp.LoginActivity;
import com.example.seierfriendapp.MainActivity;
import com.example.services.BLEScan;
import com.example.services.OuterDevicesScan;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by goran on 21.12.2014..
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
        ///preko interfacea pokreni skeniranje
        final OuterDevicesScan outerDevicesScan = new BLEScan();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                outerDevicesScan.scanForDevices(getBaseContext(), deviceList);
            }
        });
        mThread.run();
        ///analiziranje dohvaćenih podataka
        //kreiranje notificationa
        //pokretanje glavne aplikacije
    }

    /*
    Singletone pattern example
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
     * Get Mac addresses from local database
     * @return list of mac addresses of devices from local database
     */
    private List<String> getMacAdresses(){
        //IMPORTAINT! -> currently hardcoded, get from shared preferences
        List<String> macs = new LinkedList<String>();
        String mac = "FE:0F:91:39:CF:90";
        macs.add(mac);

        return macs;
    }
    boolean started = false;
    public static class Reciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("ScanningService", "RECIEVED BROADCAST!!!");

            startAct(BaseApplication.getStaticCurrentContext());
        }
    }

    public static void startAct(Context c) {
            /*Intent dialogIntent = new Intent(c, LoginActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(dialogIntent);*/

        NotificationLauncher nl = new NotificationLauncher();
        nl.launchNotification(c);
    }
}

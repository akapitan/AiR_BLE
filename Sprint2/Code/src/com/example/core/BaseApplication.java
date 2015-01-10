package com.example.core;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.activeandroid.ActiveAndroid;
import com.example.backroundScaning.ScaningService;
import com.example.localdata.Beacon;

import java.util.List;

/**
 * Created by goran on 2.12.2014..
 */
public class BaseApplication extends Application {
    private Context currentContext;
    private static BaseApplication instance;

    public BaseApplication(){
        instance = this;
    }

    @Override
    public void onCreate() {
        if(BluetoothAdapter.getDefaultAdapter().isEnabled()==false)
            BluetoothAdapter.getDefaultAdapter().enable();
        //creating scaning service
        startService(new Intent(getBaseContext(),ScaningService.class));
        Log.e("BaseApplication","Service started in baseApplication");

        ActiveAndroid.initialize(this);
        /**
         * Save beacon mac to local db
         */
       // saveMac();
    }

    /**
     * Method for getting current context
     * @return current context
     */
    public Context getCurrentContext(){
        return currentContext;
    }
    public static Context getStaticCurrentContext(){
        return instance;
    }

    /**
     * Set current Application context
     * @param ctx
     */
    public void setCurrentContext(Context ctx){
        this.currentContext = ctx;
        Log.d("SET CURRENT CONTEXT", "Current context setted");
    }

    public void saveMac(){
        Beacon beacon = new Beacon();
        beacon.setMac("FE:0F:91:39:CF:90");
        beacon.save();

        Beacon beacon1 = new Beacon();
        beacon1.setMac("FE:0F:91:39:CF:88");
        beacon1.save();
    }
}

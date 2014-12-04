package com.example.core;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;
import com.example.services.BLEScan;

/**
 * Created by goran on 2.12.2014..
 */
public class BaseApplication extends Application {
    public BLEScan bleScan;
    private Context currentContext;

    @Override
    public void onCreate() {
        super.onCreate();
        bleScan=new BLEScan();

    }

    public BLEScan getObserver(){
        return bleScan;
    }

    /**
     * Method for getting current context
     * @return current context
     */
    public Context getCurrentContext(){
        return currentContext;
    }

    /**
     * Set current Application context
     * @param ctx
     */
    public void setCurrentContext(Context ctx){
        this.currentContext = ctx;
        Log.d("SET CURRENT CONTEXT", "Current context setted");
    }
}

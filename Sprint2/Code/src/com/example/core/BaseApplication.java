package com.example.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.backroundScaning.ScaningService;

/**
 * Created by goran on 2.12.2014..
 */
public class BaseApplication extends Application {
    private Context currentContext;
    private ScaningService scaningService;
    private static BaseApplication instance;

    public BaseApplication(){
        instance = this;
    }

    @Override
    public void onCreate() {
       //creating scaning service
        startService(new Intent(getBaseContext(),ScaningService.class));
        Log.e("BaseApplication","Service started in baseApplication");
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
}

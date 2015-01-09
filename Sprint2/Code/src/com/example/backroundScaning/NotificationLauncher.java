package com.example.backroundScaning;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.seierfriendapp.LoginActivity;
import com.example.seierfriendapp.R;

/**
 * Created by matha on 08.01.15..
 */
public class NotificationLauncher {
    public Context context;


    public void launchNotification(Context c){

        context = c;
        //check if there was notification launched

        //check if there is user that is logged in

        //create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.ic_launcher).setContentTitle("SeierFriend").
                setContentText("Welcome! You've checked in. Claim your points now.");

        //login activity intent
        Intent dialogIntent = new Intent(context, LoginActivity.class);
        //dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(dialogIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,builder.build());
    }
}

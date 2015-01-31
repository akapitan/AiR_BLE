package com.seierfriendapp.backgroundScanning;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import hr.foi.seierfriendapp.R;
import com.seierfriendapp.localdata.Notification;
import com.seierfriendapp.seierfriendapp.LoginActivity;

import java.util.Calendar;
import java.util.Date;

public class NotificationLauncher {
    public Context context;

    /**
     * Launch notification with default title and text. Put other notification
     * logic if needed
     *
     * @param c Current application context
     */
    public void launchNotification(Context c) {

        context = c;

        //create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.ic_launcher).setContentTitle(context.getResources().getString(R.string.notificationTitle)).
                setContentText(context.getResources().getString(R.string.notificationMessage));
        //login activity intent
        Intent dialogIntent = new Intent(context, LoginActivity.class);
        //extras for check in recognition, used in loginActivity
        dialogIntent.putExtra("checkIn", "checkIn");

        //task builder for creating notification with intent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(dialogIntent);

        //intent for notification, update current notification if it's there
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        saveNotification();
        //get system service and notify
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Save notification for checking when was last notification sent
     */
    public void saveNotification() {

        Notification notification = new Notification();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        notification.setDateNotification(date);
        notification.setCheckedIn(false);
        notification.save();
    }
}

package com.example.forum.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.forum.R;

public class NotificationUtil {
    public static final String channelId = "com.example.forum";
    public static final int id = R.string.app_name;
    public static NotificationChannel defaultChannel = new NotificationChannel(channelId, "默认渠道", NotificationManager.IMPORTANCE_DEFAULT);

    public static void sendNotification(Context context, String title, String text) {
        Intent clickIntent = new Intent(context, context.getClass());
        sendNotification(context, clickIntent, title, text);
    }

    public static void sendNotification(Context context, Intent clickIntent, String title, String text) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, R.string.app_name, clickIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new Notification.Builder(context, context.getString(R.string.app_name))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(text)
                .build();

        sendNotification(context, notification);
    }

    public static void sendNotification(Context context, Notification notification) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(defaultChannel);
        manager.notify(id, notification);
    }
}

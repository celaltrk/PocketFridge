package com.example.pocketfridge.fridgeItems;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;

import java.util.ArrayList;

public class Notification {
    private Context context;
    public Notification (Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify","notify", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    public void createNotification(ArrayList<Product> products) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notify");
    builder.setContentTitle("Expiration Alert");
    String text = "";
    for (Product p : products)
        text += p.getName() + ", ";
    if (text.length() >= 2)
        text = text.substring(0,text.length() - 2);
    if (products.size() == 1)
        text += " is about to expire! Please consume it for a sustainable future.";
    else
        text += " are about to expire! Please consume them for a sustainable future.";
    builder.setContentText(text);
    builder.setSmallIcon(R.drawable.logo);
    builder.setAutoCancel(true);
    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
    managerCompat.notify(0,builder.build());
}

}

package com.example.notificationtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_NOTIFICATION = "channel_notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_notice:
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        NotificationChannel channel = new NotificationChannel(
                                CHANNEL_NOTIFICATION, "NotificationTestChannel", NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription("Descprtion of CHANNEL_NOTIFICATION");
                        channel.enableLights(true);
                        channel.setLightColor(Color.WHITE);

                        manager.createNotificationChannel(channel);

                        Notification notification = new NotificationCompat.Builder(v.getContext(), CHANNEL_NOTIFICATION)
                                .setContentTitle("This is content title 1")
                                .setContentText("This is content text 1")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .build();

                        manager.notify(1, notification);

                        break;
                    default:
                        break;
                }
            }
        });
    }
}
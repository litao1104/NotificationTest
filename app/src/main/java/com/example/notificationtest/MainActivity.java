package com.example.notificationtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_NOTIFICATION = "channel_notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.VIBRATE}, 1);
        }

        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_notice:
                        Intent intent = new Intent(v.getContext(), NotificationActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(), 0, intent, 0);

                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        manager.deleteNotificationChannel(CHANNEL_NOTIFICATION);
                        NotificationChannel channel = new NotificationChannel(
                                CHANNEL_NOTIFICATION, "NotificationTestChannel", NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription("Descprtion of CHANNEL_NOTIFICATION");
                        channel.enableLights(true);
                        channel.setLightColor(Color.WHITE);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[] {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000});
                        channel.enableLights(true);
                        channel.setLightColor(Color.rgb(0,0,0));

                        manager.createNotificationChannel(channel);

                        Notification notification = new NotificationCompat.Builder(v.getContext(), CHANNEL_NOTIFICATION)
                                .setContentTitle("This is content title 1")
                                .setContentText("This is content text 1")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .setContentIntent(pendingIntent)
                                //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/ToyRobot.ogg")))
                                //.setVibrate(new long[] {0, 3000, 0, 3000, 0, 3000})
                                //.setAutoCancel(true)
                                .setLights(Color.rgb(0,0,0), 1000, 1000)
                                .build();

                        manager.notify(1, notification);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (1) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You accessed the permissions", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You denied the permissions", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
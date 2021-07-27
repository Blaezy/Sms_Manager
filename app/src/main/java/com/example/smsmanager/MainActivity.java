package com.example.smsmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private static final int SMS_REQUEST_CODE= 101;
public static final String EXTRA_SMS_MESSAGE="extra_sms_message";
public static final String EXTRA_SMS_SENDER="extra_sms_sender";
private static final String KEY_TEXT_REPLY = "key_text_reply";
private TextView tvSmsFrom,tvSmsContent;
String smsSender ;
String smsContent;
private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},SMS_REQUEST_CODE);
        }
        tvSmsFrom=findViewById(R.id.Msz);
        tvSmsContent=findViewById(R.id.number);
        btn1=findViewById(R.id.btn2);
        btn2=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSmsFrom.setText(smsSender);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSmsContent.setText(smsContent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
       super.onNewIntent(intent);
        if(!intent.hasExtra( EXTRA_SMS_MESSAGE) && !intent.hasExtra(EXTRA_SMS_MESSAGE)){
            return;
        }
        smsSender =intent.getExtras().getString(EXTRA_SMS_SENDER);
        smsContent=intent.getExtras().getString(EXTRA_SMS_MESSAGE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(MainActivity.this,"My Notification");
        builder.setContentTitle(smsSender);
        builder.setContentText("Message Received");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.FLAG_ONGOING_EVENT);
        PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== SMS_REQUEST_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Sms Receiver Permission Granted",Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this,"Sms Receiver Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

}





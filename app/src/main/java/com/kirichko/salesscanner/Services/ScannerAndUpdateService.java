package com.kirichko.salesscanner.Services;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.kirichko.salesscanner.Activities.BaseActivity;
import com.kirichko.salesscanner.R;
import com.kirichko.salesscanner.Util.ExternalFileCreator;
import com.kirichko.salesscanner.datamodels.SettingsFileHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by Киричко on 10.08.2015.
 */
 public class ScannerAndUpdateService extends Service {

    private Context context;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;

        if(SettingsFileHolder.isEnableSalesScanner(this.getApplicationContext()))
        {
            startScannAndUpdateCycle();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //возможно сдесь надо будет отключить базу данных
        stopForeground(true);
    }

    void startScannAndUpdateCycle() {


        //del

        Notification note=new Notification(R.drawable.sales,
                "Запуск поиска",
                System.currentTimeMillis());
        Intent i = new Intent(this, BaseActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi=PendingIntent.getActivity(this, 0,
                i, 0);

        note.setLatestEventInfo(this, "Sales Scanning",
                "Идет поиск",
                pi);
        note.flags|=Notification.FLAG_NO_CLEAR;

        startForeground(1337, note);
        ///del


        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            stopSelf();
            }
        }).start();
    }


    public static boolean isServiceRunning(Context context) {
                ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (ScannerAndUpdateService.class.getName().equals(service.service.getClassName())) {
                        return true;
                    }
                }
                return false;
    }

}
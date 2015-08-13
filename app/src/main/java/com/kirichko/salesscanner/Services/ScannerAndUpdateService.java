package com.kirichko.salesscanner.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by Киричко on 10.08.2015.
 */
 public class ScannerAndUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        someTask();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //возможно сдесь надо будет отключить базу данных
    }

    void someTask() {
        new Thread(new Runnable() {
            public void run() {

                /*
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
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
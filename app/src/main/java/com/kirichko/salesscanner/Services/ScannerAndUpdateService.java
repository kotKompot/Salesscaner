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
    final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    (String) msg.obj, Toast.LENGTH_SHORT);
            toast.show();

        };
    };

        new Thread(new Runnable() {
            public void run() {

                String filename = "myfile";
                String string = "53";
                FileOutputStream outputStream;

                try
                {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                final String TESTSTRING = new String("Hello Android");
                FileInputStream fIn = null;
                try {
                    fIn = openFileInput(filename);

                    InputStreamReader isr = new InputStreamReader(fIn);
                    char[] inputBuffer = new char[TESTSTRING.length()];
                    isr.read(inputBuffer);
                    String readString = new String(inputBuffer);
                    handler.sendMessage(handler.obtainMessage(0, readString));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



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
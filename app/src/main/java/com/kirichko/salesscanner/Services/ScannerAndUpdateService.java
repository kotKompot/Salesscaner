package com.kirichko.salesscanner.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                btnCheckUsbDevClick();
            }
        }).start();

    }

    public void btnCheckUsbDevClick() {
        String fullpath, foldername, filename;
        foldername = "temp/myFolder";
        filename = "myFile.txt";

        //Сохранение файла на карту SD:
        fullpath = getSDcardPath()
                + "/" + foldername
                + "/" + filename;
        SaveFile(fullpath, "Этот текст сохранен на карту SD");
    }

    private String getSDcardPath() {
        String exts = Environment.getExternalStorageDirectory().getPath();
        String sdCardPath = null;
        try {
            FileReader fr = new FileReader(new File("/proc/mounts"));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("secure") || line.contains("asec"))
                    continue;
                if (line.contains("fat")) {
                    String[] pars = line.split("\\s");
                    if (pars.length < 2)
                        continue;
                    if (pars[1].equals(exts))
                        continue;
                    sdCardPath = pars[1];
                    break;
                }
            }
            fr.close();
            br.close();
            return sdCardPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdCardPath;
    }

    public void SaveFile(String filePath, String FileContent) {
        //Создание объекта файла.
        File fhandle = new File(filePath);
        try {
            //Если нет директорий в пути, то они будут созданы:
            if (!fhandle.getParentFile().exists())
                fhandle.getParentFile().mkdirs();
            //Если файл существует, то он будет перезаписан:
            fhandle.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fhandle);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(FileContent);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
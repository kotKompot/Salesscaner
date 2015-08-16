package com.kirichko.salesscanner.Util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Киричко on 15.08.2015.
 */
public class ExternalFileCreator {

    public static void createFile(String name, String dir, String text) {
        String fullpath, foldername, filename;
        foldername = "temp/";

        //Сохранение файла на карту SD:
        fullpath = getSDcardPath()
                + "/" + foldername+dir
                + "/" + name;
        SaveFile(fullpath, text);
    }

    private static String getSDcardPath() {
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

    public static void SaveFile(String filePath, String FileContent) {
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

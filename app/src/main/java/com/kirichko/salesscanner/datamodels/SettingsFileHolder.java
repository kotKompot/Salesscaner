package com.kirichko.salesscanner.datamodels;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kirichko.salesscanner.Services.ScannerAndUpdateService;
import com.kirichko.salesscanner.Util.ExternalFileCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Киричко on 13.08.2015.
 */
public class SettingsFileHolder {

    public static Context mContext;

    public static final String FILE_NAME = "SettingsFile";
    public static final int ROUGH_SYMBOLS_NUMBER = 3000;

    private static String mFileString;
    private static JSONObject mFileJSONObject;

    public static boolean mEnableSalesScanner;
    public static boolean mSaveBatteryMod;
    public static boolean mSaveInternetTrafficMod;
    public static ArrayList<String> mSales;
    public static ArrayList<String> mShops;

    public static boolean isAlreayReaded = false;

    public static void createNewSettingFile(Context context) {
        createNewSettingFile(context, true, false, false,null, null);
    }

    public static void createNewSettingFile(Context context, boolean enableSalesScanner, boolean saveBatteryMod, boolean saveInternetTrafficMod,
                                            ArrayList<String> sales, ArrayList<String> shops) {
        mEnableSalesScanner = enableSalesScanner;
        mSaveBatteryMod = saveBatteryMod;
        mSaveInternetTrafficMod = saveInternetTrafficMod;
        mSales = sales;
        mShops = shops;


        mFileJSONObject = new JSONObject();

        try {
            mFileJSONObject.put("EnableSalesScanner", mEnableSalesScanner);
            mFileJSONObject.put("SaveBatteryMod", mSaveBatteryMod);
            mFileJSONObject.put("SaveInternetTrafficMod", mSaveInternetTrafficMod);

            if(mSales!=null) {
                JSONArray salesJSONArray = new JSONArray();
                for (String sale : mSales) {
                    salesJSONArray.put(sale);
                }
                mFileJSONObject.put("Sales",salesJSONArray);
            }

            if (mShops!= null) {
                JSONArray shopsJSONArray = new JSONArray();
                for (String shop : mShops) {
                    shopsJSONArray.put(shop);
                }
                mFileJSONObject.put("Shops", shopsJSONArray);
            }

            mFileString = mFileJSONObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileOutputStream outputStream;
        try
        {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(mFileString.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        isAlreayReaded = true;
    }

    public static boolean isSettingFileExists(Context context){
        boolean r = (context.getFileStreamPath(FILE_NAME)).exists();
        return r;
    }

    public static void readSettingsFile(Context context)
    {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[ROUGH_SYMBOLS_NUMBER];
            inputStreamReader.read(inputBuffer);
            mFileString = new String(inputBuffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mFileJSONObject = new JSONObject(mFileString);

            mEnableSalesScanner = mFileJSONObject.getBoolean("EnableSalesScanner");
            mSaveBatteryMod = mFileJSONObject.getBoolean("SaveBatteryMod");
            mSaveInternetTrafficMod = mFileJSONObject.getBoolean("SaveInternetTrafficMod");

            JSONArray salesJSONArray = mFileJSONObject.getJSONArray("Sales");

            mSales = new ArrayList<>(salesJSONArray.length());
            for(int i=0; i<salesJSONArray.length(); ++i) {
                mSales.add(salesJSONArray.getString(i));
            }

            JSONArray shopsJSONArray = mFileJSONObject.getJSONArray("Shops");

            ArrayList<String> mShops = new ArrayList<>(shopsJSONArray.length());
            for(int i=0; i<shopsJSONArray.length(); ++i) {
                mShops.add(shopsJSONArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ExternalFileCreator.createFile("Setting.txt","settings",mFileString);
        isAlreayReaded = true;
    }

    public static void setScannerActive(boolean isActive, Context context)
    {
       createNewSettingFile(mContext, isActive, mSaveBatteryMod, mSaveInternetTrafficMod, mSales, mShops);

        if(isActive == true)
        {
            if(!ScannerAndUpdateService.isServiceRunning(context)) {
                Intent serviceIntent = new Intent(context, ScannerAndUpdateService.class);
                context.startService(serviceIntent);
            }
        }
        else
        {
            if(ScannerAndUpdateService.isServiceRunning(context)) {
                Intent serviceIntent = new Intent(context, ScannerAndUpdateService.class);
                context.stopService(serviceIntent);
            }
        }
    }

    public static boolean isEnableSalesScanner(Context context)
    {
        if(isAlreayReaded)
        {
            return mEnableSalesScanner;
        }
        else
        {
           if(isSettingFileExists(context))
           {
               readSettingsFile(context);
               return mEnableSalesScanner;
           }
            else
           {
            createNewSettingFile(context);
            return mEnableSalesScanner;
           }
        }

    }
}

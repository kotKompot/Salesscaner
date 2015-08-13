package com.kirichko.salesscanner.datamodels;

import android.content.Context;
import android.content.pm.FeatureInfo;

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

    public static final String FILE_NAME = "SettingsFile";
    public static final int ROUGH_SYMBOLS_NUMBER = 50;

    private File mSettingsFile;
    private String mFileString;

    public boolean mEnableSalesScanner;
    public boolean mSaveBatteryMod;
    public boolean mSaveInternetTrafficMod;
    public ArrayList<Sale> mSales;
    public ArrayList<Shop> mShops;

    public SettingsFileHolder(boolean mEnableSalesScanner, boolean mSaveBatteryMod, boolean mSaveInternetTrafficMod, ArrayList<Sale> mSales, ArrayList<Shop> mShops) {
        this.mEnableSalesScanner = mEnableSalesScanner;
        this.mSaveBatteryMod = mSaveBatteryMod;
        this.mSales = mSales;
        this.mShops = mShops;
        this.mSaveInternetTrafficMod = mSaveInternetTrafficMod;

       /* mFileString ="{ \"EnableSalesScanner\": \"true\"," +
                "\"SaveBatteryMod\": \"False\", " +
                "\"Sales\": [ \"Shoes\", \"Computers\", \"Food\" ], " +
                "\"Shops\": [ \"Mvideo\", \"Ashan\", \"MTS\" ] }";
                */
        //сдесь создать jsonObject и взять его строку
    }

    public boolean isSettingFileExists(){
        mSettingsFile = new File(FILE_NAME);
        return (mSettingsFile.exists());
    }

    public void readSettingsFile(Context context)
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
    }

    public void createNewSettingsFile(Context context) {

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
    }
}

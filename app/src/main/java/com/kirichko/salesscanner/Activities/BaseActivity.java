package com.kirichko.salesscanner.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kirichko.salesscanner.Adapters.AppSectionsPagerAdapter;
import com.kirichko.salesscanner.ExternalCode.SlidingTabLayout;
import com.kirichko.salesscanner.R;
import com.kirichko.salesscanner.Services.ScannerAndUpdateService;
import com.kirichko.salesscanner.datamodels.SettingsFileHolder;


/**
 * Created by Киричко on 02.08.2015.
 */
public class BaseActivity extends AppCompatActivity implements ActionBar.TabListener {

    Context context;
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;

        if(!ScannerAndUpdateService.isServiceRunning(this.getApplicationContext()))
        {
            Intent serviceIntent = new Intent(this.getApplicationContext(), ScannerAndUpdateService.class);
            this.startService(serviceIntent);
        }

        setContentView(R.layout.activity_base);


        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mSlidingTabLayout = ((SlidingTabLayout)  findViewById(R.id.pager_header));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_button_as_menu, menu);
        Switch enableScannerSwitch  = (Switch)  menu.findItem(R.id.switchId).getActionView().findViewById(R.id.switchForActionBar);
        enableScannerSwitch.setChecked(SettingsFileHolder.isEnableSalesScanner(this));
        enableScannerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SettingsFileHolder.setScannerActive(isChecked, context);
            }
        });

        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(!ScannerAndUpdateService.isServiceRunning(this.getApplicationContext()))
        {
            Intent serviceIntent = new Intent(this.getApplicationContext(), ScannerAndUpdateService.class);
            this.startService(serviceIntent);
        }
    }
}

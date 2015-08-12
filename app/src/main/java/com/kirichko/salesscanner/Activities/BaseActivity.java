package com.kirichko.salesscanner.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.kirichko.salesscanner.Adapters.AppSectionsPagerAdapter;
import com.kirichko.salesscanner.R;
import com.kirichko.salesscanner.Services.ScannerAndUpdateService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Киричко on 02.08.2015.
 */
public class BaseActivity extends AppCompatActivity implements ActionBar.TabListener {


    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(!ScannerAndUpdateService.isServiceRunning(this))
        {
            Intent serviceIntent = new Intent(this, ScannerAndUpdateService.class);
            this.startService(serviceIntent);
        }


        setContentView(R.layout.activity_base);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        //Планируется сделаать map shops sales иконками, а не текстом
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(R.drawable.map)
                            .setTabListener(this));

            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(R.drawable.shops)
                            .setTabListener(this));

            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(R.drawable.sales)
                            .setTabListener(this));


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



}

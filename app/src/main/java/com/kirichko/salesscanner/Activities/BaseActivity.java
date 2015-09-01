package com.kirichko.salesscanner.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private Context context;
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private Switch enableScannerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;

        offerStartService(context);

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
        enableScannerSwitch  = (Switch)  menu.findItem(R.id.switchId).getActionView().findViewById(R.id.switchForActionBar);
        enableScannerSwitch.setChecked(SettingsFileHolder.isEnableSalesScanner(this.getApplicationContext()));
        enableScannerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsFileHolder.setScannerActive(isChecked, context.getApplicationContext());
            }
        });

        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void offerStartService(Context context)
    {
        if(SettingsFileHolder.isEnableSalesScanner(context))
        {
            if (!ScannerAndUpdateService.isServiceRunning(context)) {
                Intent serviceIntent = new Intent(context, ScannerAndUpdateService.class);
                this.startService(serviceIntent);
            }
        }
        else
        {
           offerStartSearchForDiscount(context);
        }
    }

    private void offerStartSearchForDiscount(final Context contextForAlert)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(contextForAlert);
        alertBuilder.setTitle(R.string.searchForDiscountDisabled);
        alertBuilder.setMessage(R.string.offerActivateSearchDiscount);
        alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enableScannerSwitch.setChecked(true);
                if (!ScannerAndUpdateService.isServiceRunning(contextForAlert)) {
                    Intent serviceIntent = new Intent(contextForAlert, ScannerAndUpdateService.class);
                    contextForAlert.startService(serviceIntent);
                }
            }
        });
        alertBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.setCancelable(true);
        alertBuilder.show();
    }
}

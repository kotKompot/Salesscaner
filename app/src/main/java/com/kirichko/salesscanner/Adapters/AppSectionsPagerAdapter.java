package com.kirichko.salesscanner.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.kirichko.salesscanner.Controllers.MapPositioningController;
import com.kirichko.salesscanner.Fragments.DummySectionFragment;
import com.kirichko.salesscanner.R;


/**
 * Created by Киричко on 02.08.2015.
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private Context mContext;

    public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fm = fm;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0) {

            SupportMapFragment supportMapFragment = new SupportMapFragment();
            supportMapFragment.getMapAsync(new MapPositioningController());
            return supportMapFragment;

        }
        else {
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getString(R.string.Map);
            case 1:
                return mContext.getString(R.string.Sales);
            case 2:
                return mContext.getString(R.string.Shops);
        }
        return "";
    }

}

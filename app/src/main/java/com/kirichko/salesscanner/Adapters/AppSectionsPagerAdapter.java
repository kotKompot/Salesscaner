package com.kirichko.salesscanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.kirichko.salesscanner.Controllers.MapPositioningController;
import com.kirichko.salesscanner.Fragments.DummySectionFragment;
import com.kirichko.salesscanner.Fragments.SalesListFragment;
import com.kirichko.salesscanner.Fragments.WaitingForDataFragment;
import com.kirichko.salesscanner.R;
import com.kirichko.salesscanner.Util.GetSalesShopsAsync;
import com.kirichko.salesscanner.datamodels.Sale;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Киричко on 02.08.2015.
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private Context mContext;
    private MapPositioningController mMapPositioningController;

    public AppSectionsPagerAdapter(FragmentManager fm, Context context, MapPositioningController mapPositioningController) {
        super(fm);
        this.fm = fm;
        this.mContext = context;
        this.mMapPositioningController = mapPositioningController;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                SupportMapFragment supportMapFragment = new SupportMapFragment();
                supportMapFragment.getMapAsync(mMapPositioningController);
                return supportMapFragment;

            case 1:

                    SalesListFragment salesListFragment = new SalesListFragment();
                    ArrayList<Sale> sales = new ArrayList<>();
                    try {
                        sales.add(new Sale(2,2,"саб ночи в SUBWAY","скидка 20%","23.11.2015", new URL("http://bigbuzzy.ru/f/p/722/527/main.jpg"),
                                new LatLng( 55.931848, 37.520094) ));

                        sales.add(new Sale(1,5,"Торт суфле в шоколаднице", "скидка 60%","13.09.2015", new URL("http://bigbuzzy.ru/f/p/722/527/main.jpg"),
                                new LatLng(55.937650, 37.518182 )));
                        sales.add(new Sale(0,5,"Французские булочки в шоколаднице", "скидка 10%","23.05.2015", new URL("http://bigbuzzy.ru/f/p/722/527/main.jpg"),
                                new LatLng(55.937650, 37.518182 )));
                        sales.add(new Sale(0,2,"Ликвидация товара в пятерочке", "скидки от 19%","23.02.2015", new URL("http://bigbuzzy.ru/f/p/722/527/main.jpg"),
                                new LatLng(55.937678, 37.508068 )));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    salesListFragment.setListAdapter(new SalesListAdapter((Activity) mContext, sales, mMapPositioningController));

                    return salesListFragment;
            
            case 2:
                Fragment fragment = new DummySectionFragment();
                return fragment;
        }

     return null;
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

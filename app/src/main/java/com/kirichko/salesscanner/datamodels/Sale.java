package com.kirichko.salesscanner.datamodels;

import com.google.android.gms.maps.model.LatLng;

import java.net.URL;

/**
 * Created by Киричко on 14.08.2015.
 */
public class Sale {
    public int mSaleId;
    public int mShopId;
    public String mName;
    public String mDescription;
    public URL mImage;
    public String mDate;
    public LatLng mLatLng;

    public Sale(int saleId, int shopId, String name, String description, String date, URL image, LatLng latLng ) {
        this.mSaleId = saleId;
        this.mShopId = shopId;
        this.mName = name;
        this.mImage = image;
        this.mDescription = description;
        this.mDate = date;
        this.mLatLng = latLng;
    }
}

package com.kirichko.salesscanner.datamodels;

import java.net.URL;

/**
 * Created by Киричко on 14.08.2015.
 */
public class Shop {

    public int id;
    public String mName;
    public String mDescription;
    public URL mImage;

    public Shop(int id, String mName, String mDescription, URL mImage) {
        this.id = id;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mImage = mImage;
    }
}

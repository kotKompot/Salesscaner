package com.kirichko.salesscanner.datamodels;

import java.net.URL;

/**
 * Created by Киричко on 14.08.2015.
 */
public class Sale {
    public int id;
    public String mName;
    public String mDescription;
    public URL mImage;

    public Sale(int id, String mName, URL mImage, String mDescription) {
        this.id = id;
        this.mName = mName;
        this.mImage = mImage;
        this.mDescription = mDescription;
    }
}

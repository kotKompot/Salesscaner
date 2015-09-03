package com.kirichko.salesscanner.Adapters;

/**
 * Created by Киричко on 02.09.2015.
 */

import android.app.Activity;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirichko.salesscanner.Controllers.MapPositioningController;
import com.kirichko.salesscanner.R;
import com.kirichko.salesscanner.datamodels.Sale;

import java.util.ArrayList;


public class SalesListAdapter extends ArrayAdapter<Sale> {
    private final Activity context;
    private final ArrayList<Sale> mSales;
    MapPositioningController mMapPositioningController;

    public SalesListAdapter(Activity context, ArrayList<Sale> sales, MapPositioningController mapPositioningController) {
        super(context, R.layout.sale_row, sales);
        this.context = context;
        this.mSales = sales;
        mMapPositioningController = mapPositioningController;
    }

    static class ViewHolder {
        public ImageView SaleImage;
        public TextView Title;
        public TextView Description;
        public TextView Date;
        public ImageButton onMapButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.sale_row, null, true);
            holder = new ViewHolder();
            holder.Title = (TextView) rowView.findViewById(R.id.title);
            //holder.Description = (TextView) rowView.findViewById(R.id.description);
            holder.Date = (TextView) rowView.findViewById(R.id.date);
            holder.SaleImage = (ImageView) rowView.findViewById(R.id.saleImage);
            holder.onMapButton = (ImageButton) rowView.findViewById(R.id.onMapButton);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.Title.setText(mSales.get(position).mName);
        //holder.Description.setText(mSales.get(position).mDescription);
        holder.Date.setText(context.getString(R.string.DeadLine) + mSales.get(position).mDate);
        holder.onMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager mViewPager = (ViewPager) context.findViewById(R.id.pager);
                mViewPager.setCurrentItem(0);
                mMapPositioningController.setPosition(mSales.get(position).mLatLng, mSales.get(position) );
            }
        });
        //holder.SaleImage.setI


        return rowView;
    }
}
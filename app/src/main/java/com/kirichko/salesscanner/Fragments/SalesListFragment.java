package com.kirichko.salesscanner.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirichko.salesscanner.R;

/**
 * Created by Киричко on 02.09.2015.
 */
public class SalesListFragment extends ListFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_sales_list, container, false);

            return rootView;
        }

}

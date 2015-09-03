package com.kirichko.salesscanner.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.kirichko.salesscanner.Controllers.MapPositioningController;
import com.kirichko.salesscanner.datamodels.Sale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Киричко on 02.09.2015.
 */
public class GetSalesShopsAsync extends AsyncTask<Void, Void,String> {

    private static final String mainAdress = "http://vs-premiera.ru/SalesScannerServerData";
    private static final String TAG = "Http Connection";
    private Context mContext;
    private  MapPositioningController mMapPositioningController;

    public GetSalesShopsAsync(Context context,  MapPositioningController mapPositioningController)
    {
        mContext = context;
        mMapPositioningController= mapPositioningController;
    }


    @Override
    protected String doInBackground(Void... params) {

        String response="";
        {
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;

            Integer result = 0;
            try {
                URL url = new URL(mainAdress);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    response = convertInputStreamToString(inputStream);
                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            if(result == 1)  { return response; }
            else return "";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        Toast toast = Toast.makeText(mContext,
                result, Toast.LENGTH_SHORT);
        toast.show();

        if(!result.equals(""))
        {

        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }
    private ArrayList<Sale> parseJSON(String string) throws JSONException, MalformedURLException {
        ArrayList<Sale> saleArrayList = new ArrayList<>();

        JSONObject jSONObject = new JSONObject(string);
        JSONArray salesJSONArray = jSONObject.getJSONArray("Sales");
        for(int i=0; i<salesJSONArray.length(); ++i) {
            JSONObject s = salesJSONArray.getJSONObject(i);
            saleArrayList.add(new Sale(s.getInt("SaleId"), s.getInt("ShopId"), s.getString("Title"),
                                       s.getString("Description"),  s.getString("EndOfOffer"),
                                       new URL(s.getString("ImageURL")),
                                       new LatLng(s.getDouble("Latitude"), s.getDouble("Longitude"))));
        }

        return  saleArrayList;
    }
}

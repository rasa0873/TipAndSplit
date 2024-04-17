// JsonQuery.java
// Does the Json query in order to know the currency rates
// and return those rates

package com.siraapps.raul.tipandsplitrevb;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonQuery {

    private Double selectedRate;
    private JSONObject jsonObject;
    //private String latestDate;

    private Ready mReady; // Interface

    public Double getSelectedRate(String currency) throws JSONException {  // getter for selectedRate
        if (jsonObject != null){
            selectedRate = jsonObject.getJSONObject(currency).getDouble("rate");
        }
        return selectedRate;
    }


    public JsonQuery() {  // Constructor

        String currencyRatesAddress = "https://www.floatrates.com/daily/usd.json"; // usd based query
        URL url = createUrl(currencyRatesAddress);  // url created

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //JSONObject result = null;
            jsonObject = null;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection(); // attempt to connect to the url
                //connection.setSSLSocketFactory(sc.getSocketFactory());

                int response = connection.getResponseCode();

                if (response == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        String line;

                        while ((line = reader.readLine()) != null){
                            builder.append(line);
                        }
                    }
                    catch (IOException e){
                        Log.e("ETIQUETA ERROR", "Error while creating Buffered reader", e);

                    }

                    jsonObject = new JSONObject(builder.toString());
                }
            } catch (Exception e){
                Log.e("ETIQUETA ERROR", "Error retrieving data via HTTP" , e);
            }
            finally {
                assert connection != null;
                connection.disconnect();
            }

            handler.post(() -> {

                if (jsonObject != null && mReady != null)
                {
                    mReady.goRefresh();
                }
            });

        });

    }

    // Used by MyService
    public void requestUpdatedData() {

        /*
        String currencyRatesAddress = "https://www.floatrates.com/daily/usd.json"; // usd based query
        URL url = createUrl(currencyRatesAddress);  // url created


        String dolarTodayAddress = "https://s3.amazonaws.com/dolartoday/data.json";
        URL urlDT = createUrl(dolarTodayAddress);


        // initiate the process to get rates via json coded message
        if (url != null ) {
            GetCurrencyRates getRates = new GetCurrencyRates();
            getRates.execute(url);
        }


         if (urlDT != null ) {
            GetCurrencyRates getDolarTodayRate = new GetCurrencyRates();
            getDolarTodayRate.execute(urlDT);
         }
        */

    }


    // Assemble the Url based on the web address
    public URL createUrl (String address) {
        try {
            return new URL(address); // return address converted into URL
        } catch (MalformedURLException e) {
            Log.e("ETIQUETA ERROR", "Error while creating URL", e);
        }
        return null; // Url couldn't be created
    }


    /*
    private class GetCurrencyRates extends
            AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) urls[0].openConnection(); // attempt to connect to first url
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {

                        String line;

                        while ((line = reader.readLine()) != null){
                            builder.append(line);
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    return new JSONObject(builder.toString());
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                assert connection != null;
                connection.disconnect();
            }
            return null;
        }

        // process JSON response and print results
        @Override
        protected void onPostExecute(JSONObject currencies) {
            jsonObject = currencies;
            //Double dolarTodayDouble = null;
            if (jsonObject!=null) {

                if (mReady != null) {
                        mReady.goRefresh();

                }
            }

        }
    }

     */

   public interface Ready {
        void goRefresh();
        void goGetDolarToday(Double dtDouble);
   }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(Ready listener) {
        mReady = listener;
    }


}



// Removed code lines

// String dolarTodayAddress = "https://s3.amazonaws.com/dolartoday/data.json";
// URL urlDT = createUrl(dolarTodayAddress);

        /*
         if (urlDT != null ) {
            GetCurrencyRates getDolarTodayRate = new GetCurrencyRates();
            getDolarTodayRate.execute(urlDT);
         }
         */
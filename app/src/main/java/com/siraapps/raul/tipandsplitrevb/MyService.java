package com.siraapps.raul.tipandsplitrevb;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    JsonQuery jsonQuery = new JsonQuery(); // new instance of JsonQuery

    // Preferences keywords
    public static final String PREF_SAVED_CURRENCY = "MySavedPrefCurrency";
    public static final String PREF_CURRENCY_NAME = "SavedPrefCurrencyName"; // Read
    public static final String PREF_CURRENCY_RATE = "SavedPrefCurrencyRate"; // Update
    public static final String PREF_CURRENCY_DATE = "CurrencyRefreshDate"; // Update

    String savedPrefCurrencyName;
    float savedPrefCurrencyRate;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                jsonQuery.requestUpdatedData();
            }
        }, 0, 3600000); // Repeat each hour

        jsonQuery.setCustomObjectListener(new JsonQuery.Ready() {
            @Override
            public void goRefresh() {
                // Update shared pref with date and time
                // Retrieve shared prefs
                boolean usdUsed = false;
                SharedPreferences prefs = getSharedPreferences(PREF_SAVED_CURRENCY, MODE_PRIVATE);
                savedPrefCurrencyName = prefs.getString(PREF_CURRENCY_NAME, "usd");
                assert savedPrefCurrencyName != null;
                // if (savedPrefCurrencyName.equals("ves")){return;}  // used in case of DollarToday
                if (savedPrefCurrencyName.equals("usd")){
                    // switch to eur
                    savedPrefCurrencyName = "eur";
                    usdUsed = true;
                }

                try {
                    if (jsonQuery.getSelectedRate(savedPrefCurrencyName)!= null) {
                            savedPrefCurrencyRate = jsonQuery.getSelectedRate(savedPrefCurrencyName).floatValue();
                            if (usdUsed){
                                savedPrefCurrencyRate = 1 / savedPrefCurrencyRate;
                            }
                            updateSharedPrefs(savedPrefCurrencyRate);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void goGetDolarToday(Double dtDouble) {
                // Only used if DollarToday
                SharedPreferences prefs = getSharedPreferences(PREF_SAVED_CURRENCY, MODE_PRIVATE);
                savedPrefCurrencyName = prefs.getString(PREF_CURRENCY_NAME, "usd");
                assert savedPrefCurrencyName != null;
                if (!savedPrefCurrencyName.equals("ves")){return;}
                savedPrefCurrencyRate = dtDouble.floatValue();
                updateSharedPrefs(savedPrefCurrencyRate);
            }

        });

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void updateSharedPrefs(float savedRate) {
        // Update Rate and Date to SharedPrefs
        SharedPreferences settings = getSharedPreferences(PREF_SAVED_CURRENCY, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(PREF_CURRENCY_RATE, savedRate); // Save the updated rate

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = sdf.format(new Date());
        editor.putString(PREF_CURRENCY_DATE, currentDate.toUpperCase()); // Save the current date
        editor.apply();
    }

}
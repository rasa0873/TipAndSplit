package com.siraapps.raul.tipandsplitrevb;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    // Preferences keywords
    public static final String PREF_SAVED_CURRENCY = "MySavedPrefCurrency";
    public static final String PREF_CURRENCY_NAME = "SavedPrefCurrencyName";
    public static final String PREF_CURRENCY_RATE = "SavedPrefCurrencyRate";
    public static final String PREF_CURRENCY_DATE = "CurrencyRefreshDate";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        DecimalFormat formato = new DecimalFormat("#,###,###.##");

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_SAVED_CURRENCY, Context.MODE_PRIVATE);
        String currencySavedName = sharedPreferences.getString(PREF_CURRENCY_NAME, "usd");
        float currencySavedRate = sharedPreferences.getFloat(PREF_CURRENCY_RATE, 1);
        String currencyDate = sharedPreferences.getString(PREF_CURRENCY_DATE, " - - - ");
        String secondaryCurrency = "/USD";
        assert currencySavedName != null;
        if (currencySavedName.equals("usd")){
            secondaryCurrency = "/EUR";
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        String formattedRateString = formato.format(currencySavedRate);
        views.setTextViewText(R.id.appwidget_currency_rate, formattedRateString);
        if (formattedRateString.length() > 6) {
            views.setFloat(R.id.appwidget_currency_rate, "setTextSize", 25);
        } else {
            views.setFloat(R.id.appwidget_currency_rate, "setTextSize", 35);
        }
        views.setTextViewText(R.id.tv_main_currency_name, currencySavedName.toUpperCase());
        views.setTextViewText(R.id.tv_sec_currency_name, secondaryCurrency);
        views.setTextViewText(R.id.tv_current_date, currencyDate);

        // Setup the Intent to MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_currency_rate, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
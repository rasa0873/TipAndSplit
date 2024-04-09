package com.siraapps.raul.tipandsplitreva;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RemoteViews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CurrencyList extends AppCompatActivity {

    // Array of class Currencies
    ArrayList<Currencies> mCurrenciesArrayList = new ArrayList<>();

    // Currency Old sent from MainActivity
    public String currencyOld = "usd"; // default usd

    ListView mListView;

    String negAnswer, posAnswer;

    // Preferences keywords
    public static final String PREF_SAVED_CURRENCY = "MySavedPrefCurrency";
    public static final String PREF_CURRENCY_NAME = "SavedPrefCurrencyName";
    public static final String PREF_CURRENCY_RATE = "SavedPrefCurrencyRate";
    public static final String PREF_CURRENCY_DATE = "CurrencyRefreshDate";

    final String[] CURRENCY_NAME = {"usd", "eur", "gbp", "jpy", "aud", "cad", "cop", "brl", "chf", "myr",
                             "huf", "ngn", "kzt", "jmd", "ssp", "ern", "ugx", "nok", "hrk", "byn",
                             "tmt", "nio", "srd", "htg", "mop", "vuv", "bwp", "rub", "hkd", "qar",
                             "uah", "pab", "sbd", "mwk", "gtq", "clp", "sek", "mxn", "mdl", "ars",
                             "bzd", "kmf", "mzn", "kes", "dkk", "aed", "uzs", "gip", "syp", "top",
                             "cny", "kwd", "bgn", "pgk", "egp", "lak", "cup", "nzd", "isk", "pkr",
                             "lyd", "ghs", "gmd", "bif", "aoa", "sar", "azn", "jod", "mga", "ang",
                             "bsd", "gnf", "hnl", "lkr", "zar", "ils", "kgs", "uyu", "gyd", "rwf",
                             "lsl", "scr", "czk", "thb", "xaf", "tjs", "crc", "nad", "sdg", "mru",
                             "tzs", "krw", "bhd", "vnd", "irr", "dop", "mnt", "ron", "pen", "lbp",
                             "tnd", "svc", "awg", "zmw", "yer", "dzd", "pln", "try", "bdt", "gel",
                             "stn", "djf", "all", "khr", "bnd", "sgd", "idr", "pyg", "fjd", "szl",
                             "npr", "php", "bob", "lrd", "sos", "inr", "bbd", "twd", "afn", "ttd",
                             "wst", "iqd", "mad", "etb", "cve", "bam", "mmk", "omr", "amd", "sll",
                             "rsd", "xpf", "mvr", "mur", "cdf", "ves"};

    Double[] currencyRates = {1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00 ,1.00 ,1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00 ,1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,
                              1.00, 1.00, 1.00, 1.00, 1.00, 1.00};

    Integer[] imgId = {R.drawable.usa, R.drawable.eur3, R.drawable.eng, R.drawable.jpy2, R.drawable.aud3,
            R.drawable.cad3, R.drawable.cop3, R.drawable.brl3, R.drawable.switzerland, R.drawable.malaysia,
            R.drawable.hungary, R.drawable.nigeria,R.drawable.kazakhstan,R.drawable.jamaica,R.drawable.south_sudan,
            R.drawable.eritrea, R.drawable.uganda,R.drawable.norway,R.drawable.croatia,R.drawable.belarus,
            R.drawable.turkmenistan, R.drawable.nicaragua,R.drawable.suriname,R.drawable.haiti,R.drawable.macao,
            R.drawable.vanuatu, R.drawable.botswana,R.drawable.russia,R.drawable.hong_kong, R.drawable.qatar,
            R.drawable.ukraine, R.drawable.panama,R.drawable.solomon_islands, R.drawable.malawi,R.drawable.guatemala,
            R.drawable.chile, R.drawable.sweden,R.drawable.mexico, R.drawable.moldova,R.drawable.argentina ,
            R.drawable.belize, R.drawable.comoros, R.drawable.mozambique, R.drawable.kenya, R.drawable.denmark,
            R.drawable.united_arab_emirates, R.drawable.uzbekistan,R.drawable.gibraltar , R.drawable.syria, R.drawable.tonga,
            R.drawable.china, R.drawable.kuwait, R.drawable.bulgaria, R.drawable.papua_new_guinea, R.drawable.egypt,
            R.drawable.laos, R.drawable.cuba, R.drawable.new_zealand, R.drawable.iceland, R.drawable.pakistan,
            R.drawable.libya, R.drawable.ghana, R.drawable.gambia, R.drawable.burundi, R.drawable.angola,
            R.drawable.saudi_arabia, R.drawable.azerbaijan, R.drawable.jordan, R.drawable.madagascar, R.drawable.curacao,
            R.drawable.bahamas, R.drawable.guinea, R.drawable.honduras, R.drawable.sri_lanka, R.drawable.south_africa,
            R.drawable.israel, R.drawable.kyrgyzstan, R.drawable.uruguay, R.drawable.guyana, R.drawable.rwanda,
            R.drawable.lesotho, R.drawable.seychelles, R.drawable.czech_republic, R.drawable.thailand, R.drawable.central_african_republic,
            R.drawable.tajikistan, R.drawable.costa_rica, R.drawable.namibia, R.drawable.sudan, R.drawable.mauritania,
            R.drawable.tanzania, R.drawable.south_korea, R.drawable.bahrain, R.drawable.vietnam, R.drawable.iran,
            R.drawable.dominican_republic, R.drawable.mongolia, R.drawable.romania, R.drawable.peru, R.drawable.lebanon,
            R.drawable.tunisia, R.drawable.el_salvador, R.drawable.aruba, R.drawable.zambia, R.drawable.yemen,
            R.drawable.algeria, R.drawable.poland, R.drawable.turkey, R.drawable.bangladesh, R.drawable.georgia,
            R.drawable.sao_tome_and_principe, R.drawable.djibouti, R.drawable.albania, R.drawable.cambodia, R.drawable.brunei,
            R.drawable.singapore, R.drawable.indonesia, R.drawable.paraguay, R.drawable.fiji, R.drawable.swaziland,
            R.drawable.nepal, R.drawable.philippines, R.drawable.bolivia, R.drawable.liberia, R.drawable.somalia,
            R.drawable.india, R.drawable.barbados, R.drawable.taiwan, R.drawable.afghanistan, R.drawable.trinidad_and_tobago,
            R.drawable.samoa, R.drawable.iraq, R.drawable.morocco, R.drawable.ethiopia, R.drawable.cape_verde,
            R.drawable.bosnia_and_herzegovina, R.drawable.myanmar, R.drawable.oman, R.drawable.armenia, R.drawable.sierra_leone,
            R.drawable.serbia, R.drawable.french_polynesia, R.drawable.maldives, R.drawable.mauritius, R.drawable.democratic_republic_of_congo,
            R.drawable.ves3};

    JsonQuery jsonQuery = new JsonQuery(); // defines the constructor access

    CustomListview customListview;

    // Internal file for saving currency rates
    FileOutputStream fileWriteStream;
    FileInputStream fileReadStream;
    private final String CURRENCY_FILENAME = "currency_rates.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_currency_list);
        setContentView(R.layout.activity_currency_list);

        negAnswer = getResources().getString(R.string.neg_answer);
        posAnswer = getResources().getString(R.string.pos_answer);

        mListView = findViewById(R.id.listview);

        // restore saved currencies
        try {
            InputStream inputStream = openFileInput(CURRENCY_FILENAME);
            if (inputStream != null){ // file exists
                readInternalFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // JsonQuery onPostExecute Listener
        jsonQuery.setCustomObjectListener(new JsonQuery.Ready() {
            @Override
            public void goRefresh() {

                // Update date to sharedPref
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String currentDate = sdf.format(new Date());
                // Insert the update DATE to Prefs
                SharedPreferences settings = getSharedPreferences(PREF_SAVED_CURRENCY, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREF_CURRENCY_DATE, currentDate.toUpperCase());
                editor.apply();

                updateList();
            }

            @Override
            public void goGetDolarToday(Double dtDouble) {
                int numberOfRates = currencyRates.length;
                currencyRates[numberOfRates-1] = dtDouble;
                updateList();
            }
        });


        // populate ArrayList of currencies with initial values
        for (int i = 0; i < CURRENCY_NAME.length; i++) {
            mCurrenciesArrayList.add(new Currencies(CURRENCY_NAME[i], currencyRates[i], imgId[i]));
        }


        customListview = new CustomListview(this, mCurrenciesArrayList);
        mListView.setAdapter(customListview);

        mListView.setOnItemClickListener((parent, view, position, id) -> {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(CurrencyList.this, R.style.AlertDialog);

            builder1.setMessage(getResources().getString(R.string.selected_currency) + " " + mCurrenciesArrayList.get(position).getCurrencyName().toUpperCase());
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    posAnswer,
                    (dialog, id1) -> {
                        dialog.cancel();

                        //New - update pref with selected currency
                            updatePrefsAndWidget(mCurrenciesArrayList.get(position).getCurrencyName(),
                                    mCurrenciesArrayList.get(position).getRate());

                        sendResponse(mCurrenciesArrayList.get(position).getFlagImage(),
                                mCurrenciesArrayList.get(position).getCurrencyName(),
                                calculateNewCurrencyFactor(mCurrenciesArrayList.get(position).getRate()));
                    });

            builder1.setNegativeButton(
                    negAnswer,
                    (dialog, id12) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();

        });

        Intent i2 = getIntent();
        currencyOld = i2.getStringExtra("currencyOld");

    }

    private void updatePrefsAndWidget(String currencyName, Double currencyRate) {

        String secondCurrency = "/USD";
        if (currencyName.equals("usd")) { // switch to eur rate
            currencyRate = 1/currencyRates[1]; // 1/eur rate
            secondCurrency = "/EUR";
        }

        DecimalFormat formato = new DecimalFormat("#,###,###.##");
        String formattedRateString = formato.format(currencyRate);

        // Insert to Prefs
        SharedPreferences settings = getSharedPreferences(PREF_SAVED_CURRENCY, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_CURRENCY_NAME, currencyName);
        editor.putFloat(PREF_CURRENCY_RATE, currencyRate.floatValue());
        editor.apply();

        // Update Widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.my_app_widget);
        views.setCharSequence(R.id.tv_main_currency_name, "setText", currencyName.toUpperCase());
        views.setCharSequence(R.id.tv_sec_currency_name, "setText", secondCurrency);
        views.setCharSequence(R.id.appwidget_currency_rate, "setText", formattedRateString);
        if (formattedRateString.length()> 6) {
            views.setFloat(R.id.appwidget_currency_rate, "setTextSize", 25); // Reduced size
        } else {
            views.setFloat(R.id.appwidget_currency_rate, "setTextSize", 35); // Default size
        }

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SAVED_CURRENCY, Context.MODE_PRIVATE);
        String retrievedDate = sharedPreferences.getString(PREF_CURRENCY_DATE, " - - - ");
        views.setCharSequence(R.id.tv_current_date, "setText", retrievedDate);

        ComponentName widgetComponent = new ComponentName(this, MyAppWidget.class);
        int[] widgetIds =  appWidgetManager.getAppWidgetIds(widgetComponent);
        for (int appWidgetId : widgetIds){
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }


    public Double calculateNewCurrencyFactor(Double selectedCurrency){

        Double doubleCurrencyOld = 1.0;
        int i = 0;
        while (i < CURRENCY_NAME.length){
            if (CURRENCY_NAME[i].equals(currencyOld)){
                doubleCurrencyOld = currencyRates[i];
                i = CURRENCY_NAME.length;
            } else {
                i++;
            }
        }

        return selectedCurrency / doubleCurrencyOld;

    }

    public void sendResponse(Integer newLogo, String newCurrency, Double factor){
        Intent responseIntent = new Intent();
        responseIntent.putExtra("responseLogo", newLogo);
        responseIntent.putExtra("responseCurrency", newCurrency);
        responseIntent.putExtra("responseFactor", factor);
        setResult(RESULT_OK, responseIntent);
        finish();
    }

    public void updateList () {

        for (int i = 1; i < CURRENCY_NAME.length  ; i++){  //  mCurrenciesArrayList.size()
            try {
                if (jsonQuery.getSelectedRate(CURRENCY_NAME[i])!= null) {
                     //if (!CURRENCY_NAME[i].equals("ves"))  // Enable only in case of DolarToday
                    currencyRates[i] = jsonQuery.getSelectedRate(CURRENCY_NAME[i]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        updateInternalFile(currencyRates); // update currencies from Json to file

        if (mCurrenciesArrayList.size() == CURRENCY_NAME.length) { // update the list ONLY if th list is not being filtered
            for (int i = 1; i < mCurrenciesArrayList.size(); i++) {
                mCurrenciesArrayList.get(i).setRate(currencyRates[i]);
            }

            customListview.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_search_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_menu_search);

        final SearchView searchViewAndroidActionBar =  (SearchView) searchViewItem.getActionView();
        assert searchViewAndroidActionBar != null;
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCurrenciesArrayList.clear();
                for (int i = 0; i < CURRENCY_NAME.length ; i++) {  // mCurrenciesArrayListCopy.size()
                    if (CURRENCY_NAME[i].contains(newText)){     // mCurrenciesArrayListCopy.get(i).getCurrencyName().contains(newText)
                        mCurrenciesArrayList.add(new Currencies(CURRENCY_NAME[i], currencyRates[i], imgId[i]));
                    }
                }
                customListview.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void updateInternalFile (Double[] currencyRates){

        String[] currenciesText = {"1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0" ,"1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0",
                                   "1.0", "1.0", "1.0", "1.0", "1.0", "1.0"} ;

        for (int i = 0; i < currencyRates.length; i++){
            currenciesText[i] = currencyRates[i].toString() + "\n";
        }

        try {

            fileWriteStream = openFileOutput(CURRENCY_FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < currencyRates.length; i++){
                fileWriteStream.write(currenciesText[i].getBytes());
            }
            fileWriteStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }


    private void readInternalFile() {

        try {
            fileReadStream = openFileInput(CURRENCY_FILENAME);
            StringBuilder sBuffer = new StringBuilder();
            int i;
            while ((i = fileReadStream.read())!= -1){
                sBuffer.append((char)i);
            }
            fileReadStream.close();
            String[] details = sBuffer.toString().split("\n");

            for (int i2 = 0; i2 < details.length; i2++ ) {
                currencyRates[i2] = Double.parseDouble(details[i2]);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }


}

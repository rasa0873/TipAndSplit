package com.siraapps.raul.tipandsplitreva;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

// <String>
public class CustomListview extends ArrayAdapter<Currencies> {

    private double rateNumber = 0.0;
    private String rateString = "0.0";

    private final Activity context; // define an instance of Activity

    // Array of class Currencies
    ArrayList<Currencies> mCurrenciesArrayList = new ArrayList<>();


    // Constructor receives the Activity instance and the content of the ListView
    public CustomListview(Activity context, ArrayList<Currencies> mCurrenciesArrayList) {
    //public CustomListview(Activity context, String[] currencyName, Double[] currencyRate, Integer[] imgid, boolean[] checked ) {

       super(context, R.layout.listview_layout, mCurrenciesArrayList);
    //  super(context, R.layout.listview_layout, currencyName);

       this.context = context;
       this.mCurrenciesArrayList = mCurrenciesArrayList;

    }


    @Nullable
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){

       View r = convertView;
       ViewHolder viewHolder = null;
       if (r == null){
           LayoutInflater layoutInflater = context.getLayoutInflater();
           r=layoutInflater.inflate(R.layout.listview_layout, null, true);
           viewHolder = new ViewHolder(r);
           r.setTag(viewHolder);
        } else {
           viewHolder = (ViewHolder) r.getTag();
        }

       viewHolder.ivw.setImageResource(mCurrenciesArrayList.get(position).flagImage);
       viewHolder.tvw1.setText(mCurrenciesArrayList.get(position).currencyName.toUpperCase());

       if (mCurrenciesArrayList.get(position).getRate() != null) {

           rateString = mCurrenciesArrayList.get(position).getRate().toString();
           rateNumber = Double.parseDouble(rateString);

           DecimalFormat formato = (DecimalFormat) NumberFormat.getInstance(Locale.US);
           formato.applyPattern("#,###,###.##");
           viewHolder.tvw2.setText(context.getResources().getString(R.string.listview_rate_tag, formato.format(rateNumber)) );

      } else {
           viewHolder.tvw2.setText(R.string.rate_not_available);
       }

      return r;

    }

   class ViewHolder {

     TextView tvw1;
     TextView tvw2;
     ImageView ivw;

      // Constructor
      ViewHolder(View v){
      tvw1 = v.findViewById(R.id.tvname);
      tvw2 = v.findViewById(R.id.tvdescription);
      ivw = v.findViewById(R.id.imageView);

      }

   }


}

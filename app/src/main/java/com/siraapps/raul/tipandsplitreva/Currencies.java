package com.siraapps.raul.tipandsplitreva;

public class Currencies {

    String currencyName;
    Double rate;
    Integer flagImage;

    // Constructor
    public Currencies(String name, Double rate, Integer flagImage){
        this.currencyName = name;
        this.rate = rate;
        this.flagImage = flagImage;

    }

    public String getCurrencyName(){
        return currencyName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getFlagImage() {
        return flagImage;
    }
}

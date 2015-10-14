package com.andela.www.currencycalculator.model;

import com.andela.www.currencycalculator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 10/9/15.
 */
public class CurrencyModel {

    private String currencyName;
    private int imageId;

    public CurrencyModel(String currencyName, int imageId) {
        this.currencyName = currencyName;
        this.imageId = imageId;
    }

    // Setter methods
    public void setCurrencyName(String CompanyName)
    {
        this.currencyName = CompanyName;
    }

    public void setImage(int image)
    {
        this.imageId = image;
    }

    // Getter methods
    public String getCurrencyName()
    {
        return currencyName;
    }

    public int getImage()
    {
        return imageId;
    }

    public static List<CurrencyModel> getCurrencies(String[] currencies) {
        List<CurrencyModel> currencyObjects = new ArrayList<>();

        for (String currency: currencies) {
            int drawableId = getDrawable(currency);
            CurrencyModel currencyModel = new CurrencyModel(currency, drawableId);
            currencyObjects.add(currencyModel);
        }

        return currencyObjects;
    }

    private static int getDrawable(String currency) {
        switch (currency) {
            case "USD":
                return R.drawable.usd;
            case "CAD":
                return R.drawable.cad;
            case "CHF":
                return R.drawable.chf;
            case "AUD":
                return R.drawable.aud;
            case "CHY":
                return R.drawable.chy;
            case "EUR":
                return R.drawable.eur;
            case "HKD":
                return R.drawable.hkd;
            case "JPY":
                return R.drawable.jpy;
            case "NGN":
                return R.drawable.ngn;
            case "NZD":
                return R.drawable.nzd;
            case "SEK":
                return R.drawable.sek;
            case "GBP":
                return R.drawable.gbp;
        }
        return 0;
    }

}

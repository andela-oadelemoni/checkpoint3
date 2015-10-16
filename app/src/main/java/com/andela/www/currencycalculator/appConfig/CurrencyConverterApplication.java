package com.andela.www.currencycalculator.appConfig;

import android.app.Application;

/**
 * Created by kamiye on 10/14/15.
 */
public class CurrencyConverterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ContextProvider(this);
    }
}

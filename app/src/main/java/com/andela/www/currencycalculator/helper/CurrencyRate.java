package com.andela.www.currencycalculator.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by kamiye on 10/26/15.
 */
public class CurrencyRate extends JSONObject {

    private JSONObject object;

    public CurrencyRate(JSONObject object) {
        this.object = object;
    }

    public CurrencyRate getCurrencyRate() throws JSONException {

        Iterator<?> keys = object.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();

            put(key, object.get(key));
        }
        return this;
    }

}

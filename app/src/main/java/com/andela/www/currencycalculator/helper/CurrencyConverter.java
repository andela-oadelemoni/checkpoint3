package com.andela.www.currencycalculator.helper;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kamiye on 10/16/15.
 */
public class CurrencyConverter {

    public static final String CONVERSION_URL = "https://openexchangerates.org/api/latest.json?app_id=6b8d176d6e1741af8a8028ed6c17d51d";
    private JSONObject rates;
    private RequestQueue requestQueue;

    public CurrencyConverter(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getRates(final RatesCallback callback) {
        JsonObjectRequest conversionRequest = new JsonObjectRequest(Request.Method.GET,
                CONVERSION_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    rates = response.getJSONObject("rates");
                    callback.onSuccess(rates);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailure();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error request", error.toString());
                // set error message to the user
                callback.onFailure();
                requestQueue.stop();
            }
        });

        requestQueue.add(conversionRequest);
    }

    public interface RatesCallback {
        void onSuccess(JSONObject rates);
        void onFailure();
    }
}

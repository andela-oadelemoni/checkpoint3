package com.andela.www.currencycalculator;

import android.test.InstrumentationTestCase;
import android.view.View;

import com.andela.www.currencycalculator.activities.TopTenActivity;
import com.andela.www.currencycalculator.adapter.TopTenAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 10/23/15.
 */
public class TopTenAdapterTest extends InstrumentationTestCase {

    private TopTenAdapter topTenAdapter;


    public void setUp() {
       topTenAdapter = new TopTenAdapter(getInstrumentation().getContext(), setUpCurrencyList());
    }


    private List<JSONObject> setUpCurrencyList() {
        List<JSONObject> currencyList = new ArrayList<>();
        JSONObject currency1 = new JSONObject();
        JSONObject currency2 = new JSONObject();

        try {
            currency1.put("USD", 1.0);
            currency2.put("GBP", 2.0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        currencyList.add(currency1);
        currencyList.add(currency2);

        return currencyList;
    }

    public void testAdapterCount() {
        assertEquals(2, topTenAdapter.getCount());
    }

}

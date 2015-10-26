package com.andela.www.currencycalculator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.andela.www.currencycalculator.R;
import com.andela.www.currencycalculator.adapter.TopTenAdapter;
import com.andela.www.currencycalculator.helper.CurrencyConverter;
import com.andela.www.currencycalculator.helper.CurrencyRate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TopTenActivity extends AppCompatActivity {

    private double usdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usdValue = getIntent().getExtras().getDouble("USD_VALUE");
        if (usdValue == 0) usdValue = 1;

        CurrencyConverter converter = new CurrencyConverter(this);
        converter.getRates(new CurrencyConverter.RatesCallback() {
            @Override
            public void onSuccess(CurrencyRate rates) {
                try {
                    fillListView(rates);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void fillListView(CurrencyRate rates) throws JSONException {
        List<CurrencyRate> orderingList = new ArrayList<>();

        Iterator<?> keys = rates.keys();

        while (keys.hasNext()) {
            String key = (String)keys.next();
            double value = rates.getDouble(key) * usdValue;
            CurrencyRate object = new CurrencyRate(rates);
            object.put(key, value);

            orderingList.add(object);
        }
        orderingList = getTopTen(orderingList);
        ListView listView = (ListView) findViewById(R.id.listview);
        TopTenAdapter adapter = new TopTenAdapter(this, orderingList);
        listView.setAdapter(adapter);
    }

    private List<CurrencyRate> getTopTen(List<CurrencyRate> list) {

        List<CurrencyRate> topTen = new ArrayList<>();

        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lhsKey = lhs.keys().next();
                String rhsKey = rhs.keys().next();
                try {
                    if (lhs.getDouble(lhsKey) > rhs.getDouble(rhsKey)) {
                        return 1;
                    } else if (lhs.getDouble(lhsKey) < rhs.getDouble(rhsKey))
                        return -1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        for (int i = 0; i < 10; i++)
            topTen.add(list.get(i));

        return topTen;
    }

}

package com.andela.www.currencycalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.andela.www.currencycalculator.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by kamiye on 10/20/15.
 */

public class TopTenAdapter extends ArrayAdapter<JSONObject> {
    public TopTenAdapter(Context context, List<JSONObject> currency) {
        super(context, 0, currency);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JSONObject currency = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView currencyName = (TextView) convertView.findViewById(R.id.currency_name);
        TextView currencyValue = (TextView) convertView.findViewById(R.id.currency_value);
        // Populate the data into the template view using the data object
        currencyName.setText(currency.keys().next());
        try {
            currencyValue.setText(String.valueOf(currency.getDouble(currency.keys().next())));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }
}

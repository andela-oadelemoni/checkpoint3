package com.andela.www.currencycalculator.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.www.currencycalculator.model.Currency;
import com.andela.www.currencycalculator.R;

import java.util.List;

/**
 * Created by kamiye on 10/9/15.
 */
public class CurrencyAdapter extends ArrayAdapter<String> {

    private Context context;
    private List currencies;
    private Currency tempValues;
    private LayoutInflater inflater;

    // Constructor
    public CurrencyAdapter(Context context, int textViewResourceId, List objects) {

        super(context, textViewResourceId, objects);
        this.context = context;
        currencies = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This function called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        tempValues = null;
        tempValues = (Currency) currencies.get(position);

        TextView label = (TextView)row.findViewById(R.id.currency);
        ImageView currencyLogo = (ImageView)row.findViewById(R.id.image);

        // Set values for spinner each row
        label.setText(tempValues.getCurrencyName());
        currencyLogo.setImageResource(tempValues.getImage());

        return row;
    }
}

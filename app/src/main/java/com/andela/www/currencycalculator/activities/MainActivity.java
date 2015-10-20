package com.andela.www.currencycalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.andela.www.currencycalculator.helper.InputHandler;
import com.andela.www.currencycalculator.adapter.CurrencyAdapter;
import com.andela.www.currencycalculator.model.Currency;
import com.andela.www.currencycalculator.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public List<Currency> currencyValues = new ArrayList<>();
    // Input helper
    private InputHandler inputHandler;
    private String activePosition = "USD";
    private String baseCurrency = "USD";

    private TextView convertingCurrency;
    private Spinner convertingCurrencySelector;
    private Spinner startingCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize method to set spinner values
        init();
    }

    private void init() {
        // set calculator screen
        TextView calculator_screen = (TextView) findViewById(R.id.calculator_screen);
        TextView mini_screen = (TextView) findViewById(R.id.mini_screen);

        // currency display screens
        convertingCurrency = (TextView) findViewById(R.id.destination_currency);

        // set input helper
        inputHandler = new InputHandler(this, calculator_screen, mini_screen);

        String[] currencyList = getResources().getStringArray(R.array.currency_list);
        currencyValues = Currency.getCurrencies(currencyList);

        startingCurrency = (Spinner) findViewById(R.id.starting_currency_picker);
        convertingCurrencySelector = (Spinner) findViewById(R.id.destination_currency_picker);

        // define adapters for spinners
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, R.layout.spinner_rows, currencyValues, getResources());

        startingCurrency.setAdapter(currencyAdapter);
        convertingCurrencySelector.setAdapter(currencyAdapter);


        convertingCurrencySelector.setOnItemSelectedListener(this);
        startingCurrency.setOnItemSelectedListener(this);

        // conversion button
        Button conversionButton = (Button) findViewById(R.id.conversionButton);
        conversionButton.setOnClickListener(this);

        // top ten button
        Button topTenButton = (Button) findViewById(R.id.topTen);
        topTenButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number_0:
            case R.id.number_1:
            case R.id.number_2:
            case R.id.number_3:
            case R.id.number_4:
            case R.id.number_5:
            case R.id.number_6:
            case R.id.number_7:
            case R.id.number_8:
            case R.id.number_9:
                // get the integer value of the number
                int value = Integer.valueOf(((Button) v).getText().toString());
                inputHandler.numberPressed(value);
                break;
            case R.id.divide:
            case R.id.multiply:
            case R.id.add:
            case R.id.subtract:
                inputHandler.operandPressed(v.getId());
                break;
            case R.id.clear:
                inputHandler.clearPressed();
                convertingCurrency.setText("");
                break;
            case R.id.back:
                inputHandler.backPressed();
                break;
            case R.id.equal:
                inputHandler.equalPressed();
                break;
            case R.id.decimal:
                inputHandler.decimalPressed();
                break;
            case R.id.conversionButton:
                Currency currency = (Currency) convertingCurrencySelector.getSelectedItem();
                activePosition = currency.getCurrencyName();
                updateCurrencyResult();
                break;
            case R.id.topTen:
                Intent intent = new Intent(this, TopTenActivity.class);
                intent.putExtra("USD_VALUE", inputHandler.getUSDValue());
                startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Currency currency = (Currency) parent.getItemAtPosition(position);
        if (parent.getId() == R.id.destination_currency_picker) {
            activePosition = currency.getCurrencyName();
            inputHandler.setTargetCurrency(activePosition);
        }
        else {
            baseCurrency = currency.getCurrencyName();
            inputHandler.setBaseCurrency(baseCurrency);
        }
    }

    private void updateCurrencyResult() {
        double currency = (double) inputHandler.getEquivalentValue();
        convertingCurrency.setText(String.valueOf(currency));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}

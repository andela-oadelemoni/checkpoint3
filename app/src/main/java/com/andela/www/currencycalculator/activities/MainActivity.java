package com.andela.www.currencycalculator.activities;

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
import android.widget.Toast;
import com.andela.www.currencycalculator.helper.CurrencyConverter;
import com.andela.www.currencycalculator.helper.InputHandler;
import com.andela.www.currencycalculator.adapter.CurrencyAdapter;
import com.andela.www.currencycalculator.model.Currency;
import com.andela.www.currencycalculator.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public List<Currency> currencyValues = new ArrayList<>();
    // Input helper
    private InputHandler inputHandler;
    private CurrencyConverter converter;
    private String activePosition = "USD";
    private String baseCurrency = "USD";

    private TextView destinationCurrency;

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
        destinationCurrency = (TextView) findViewById(R.id.destination_currency);

        // set input helper
        inputHandler = new InputHandler(this, calculator_screen, mini_screen);

        String[] currencyList = getResources().getStringArray(R.array.currency_list);
        currencyValues = Currency.getCurrencies(currencyList);

        final Spinner startingCurrency = (Spinner) findViewById(R.id.starting_currency_picker);
        final Spinner destinationCurrency = (Spinner) findViewById(R.id.destination_currency_picker);
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, R.layout.spinner_rows, currencyValues, getResources());

        startingCurrency.setAdapter(currencyAdapter);
        destinationCurrency.setAdapter(currencyAdapter);

        converter = new CurrencyConverter(MainActivity.this);

        destinationCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) destinationCurrency.getItemAtPosition(position);
                activePosition = currency.getCurrencyName();
                doConversion();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startingCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) startingCurrency.getItemAtPosition(position);
                baseCurrency = currency.getCurrencyName();
                inputHandler.setBaseCurrency(baseCurrency);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // conversion button
        Button conversionButton = (Button) findViewById(R.id.conversionButton);
        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Currency currency = (Currency) destinationCurrency.getSelectedItem();
                activePosition = currency.getCurrencyName();
                doConversion();
            }
        });
    }

    private void doConversion() {
        float baseValue = updateBaseValue();
        converter.convertCurrency(baseCurrency, baseValue, activePosition, new CurrencyConverter.ConfirmationCallback() {
            @Override
            public void onSuccess(String currency) {
                Toast.makeText(MainActivity.this, "Conversion is " + currency, Toast.LENGTH_LONG).show();
                destinationCurrency.setText(currency);
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "Conversion failed!!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private float updateBaseValue() {
        float base = (float) inputHandler.getBaseValue();

        return (base == 0) ? 1 : base;
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
                inputHandler.divisionPressed();
                break;
            case R.id.multiply:
                inputHandler.multiplicationPressed();
                break;
            case R.id.add:
                inputHandler.additionPressed();
                break;
            case R.id.subtract:
                inputHandler.subtractionPressed();
                break;
            case R.id.clear:
                inputHandler.clearPressed();
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
        }
    }
}

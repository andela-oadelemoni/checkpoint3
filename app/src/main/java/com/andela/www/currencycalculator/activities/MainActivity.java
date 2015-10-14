package com.andela.www.currencycalculator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.www.currencycalculator.InputHandler;
import com.andela.www.currencycalculator.adapter.CurrencyAdapter;
import com.andela.www.currencycalculator.model.CurrencyModel;
import com.andela.www.currencycalculator.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<CurrencyModel> currencyValues = new ArrayList<>();
    // Input helper
    private InputHandler inputHandler;

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

        // set input helper
        inputHandler = new InputHandler(this, calculator_screen, mini_screen);

        String[] currencyList = getResources().getStringArray(R.array.currency_list);
        currencyValues = CurrencyModel.getCurrencies(currencyList);

        Spinner startingCurrency = (Spinner) findViewById(R.id.starting_currency_picker);
        Spinner destinationCurrency = (Spinner) findViewById(R.id.destination_currency_picker);
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, R.layout.spinner_rows, currencyValues, getResources());

        startingCurrency.setAdapter(currencyAdapter);
        destinationCurrency.setAdapter(currencyAdapter);
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

    public void buttonClickAction(View view) {
        switch (view.getId()) {
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
                int value = Integer.valueOf(((Button) view).getText().toString());
                inputHandler.numberPressed(value);
                break;
            case R.id.divide:
            case R.id.multiply:
            case R.id.add:
            case R.id.subtract:
                break;
            case R.id.clear:
                inputHandler.clearPressed();
                break;
            case R.id.back:
                break;
            case R.id.equal:
                break;
            case R.id.decimal:
                inputHandler.decimalPressed();
                break;
        }
    }
}

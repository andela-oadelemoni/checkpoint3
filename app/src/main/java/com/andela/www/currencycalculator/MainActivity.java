package com.andela.www.currencycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView calculator_screen;
    private float lastResult = 0;
    private String previousInput = "";
    private String currentInput = "";
    private Spinner startingCurrency;
    private Spinner destinationCurrency;
    private CurrencyAdapter currencyAdapter;
    public List<CurrencyModel> currencyValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set calculator screen
        calculator_screen = (TextView) findViewById(R.id.calculator_screen);

        String[] currencyList = getResources().getStringArray(R.array.currency_list);
        currencyValues = CurrencyModel.getCurrencies(currencyList);

        startingCurrency = (Spinner) findViewById(R.id.starting_currency_picker);
        destinationCurrency = (Spinner) findViewById(R.id.destination_currency_picker);
        currencyAdapter = new CurrencyAdapter(this, R.layout.spinner_rows, currencyValues, getResources());

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
                processNumberClick(value);
                break;
            case R.id.divide:
            case R.id.multiply:
            case R.id.add:
            case R.id.subtract:
                arithmeticOperation(view.getId());
                break;
            case R.id.cancel_action:
                resetCalculator();
                break;
            case R.id.back:
                backOneSpace();
                break;
            case R.id.equal:
                calculate();
                break;
        }
    }

    private void processNumberClick(int number) {
        if (!zeroInput(number))
            currentInput += number;
            previousInput = currentInput;
        displayResult(currentInput);
    }

    private boolean zeroInput(int number) {
        return (previousInput.equals("") && number == 0);
    }

    private void arithmeticOperation(int operand) {

    }

    private void calculate() {

    }

    private void resetCalculator() {

    }

    private void backOneSpace() {

    }

    private void displayResult(String result) {
        calculator_screen.setText(result);
    }
}

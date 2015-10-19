package com.andela.www.currencycalculator.utility;

import android.content.Context;
import android.util.Log;
import com.andela.www.currencycalculator.helper.CalculationHistory;
import com.andela.www.currencycalculator.helper.CurrencyConverter;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Stack;

/**
 * Created by kamiye on 10/14/15.
 */
public class Calculator {

    private float calculationResult = 0;
    private boolean isFirstOperation = true;
    private float firstNumber = 0;
    private float secondNumber = 0;

    private double firstNumberInUSD = 0;
    private double secondNumberInUSD = 0;

    private String baseCurrency = "USD";
    private String firstBaseCurrency = "USD";
    private ArithmeticOperand operand = ArithmeticOperand.EQUAL;
    private Stack<Float> calculationStack = new Stack<>();
    private CalculationHistory history = new CalculationHistory();

    private CurrencyConverter converter;
    private double resultInUSD;
    private JSONObject currencyRates;

    public Calculator(Context context) {
        converter = new CurrencyConverter(context);
        converter.getRates(new CurrencyConverter.RatesCallback() {
            @Override
            public void onSuccess(JSONObject rates) {
                currencyRates = rates;
            }

            @Override
            public void onFailure() {
                Log.i("Rates Failure", "Failure getting rates");
            }
        });
    }

    private void setFirstNumber(float firstNumber, String baseCurrency) {
        firstNumberInUSD = convertToUSD(firstNumber, baseCurrency);
        Log.i("FirstBaseCurrency", baseCurrency);
        this.firstNumber = firstNumber;
    }

    private void setSecondNumber(float secondNumber, String baseCurrency) {
        secondNumberInUSD = convertToUSD(secondNumber, baseCurrency);
        Log.i("SecondBaseCurrency", baseCurrency);
        this.secondNumber = secondNumber;
    }

    public void setOperand(ArithmeticOperand operand) {
        this.operand = operand;
    }

    public void setBaseCurrency(String currency) {
        firstBaseCurrency = baseCurrency;
        this.baseCurrency = currency;
    }

    /* CALCULATION METHODS */

    public void calculate() {
        setNumbers();
        switch (operand) {
            case ADD:
                add();
                break;
            case SUBTRACT:
                subtract();
                break;
            case MULTIPLY:
                multiply();
                break;
            case DIVIDE:
                divide();
                break;
        }
    }

    public void resetCalculator() {
        history.resetHistory();
        operand = ArithmeticOperand.EQUAL;
        calculationResult = 0;
        resultInUSD = 0;
        isFirstOperation = true;
        calculationStack.clear();
    }

    // Addition method
    private void add() throws ArithmeticException {
        // do currency conversion of first and second number differently
        resultInUSD = firstNumberInUSD + secondNumberInUSD;
        calculationResult = firstNumber + secondNumber;
    }

    // Subtraction method
    private void subtract() throws ArithmeticException {
        resultInUSD = firstNumberInUSD - secondNumberInUSD;
        calculationResult = firstNumber - secondNumber;
    }

    // Multiplication method
    private void multiply() throws ArithmeticException {
        resultInUSD = firstNumberInUSD * secondNumberInUSD;
        calculationResult = firstNumber * secondNumber;
    }

    // Divide method
    private void divide() throws ArithmeticException {
        resultInUSD = firstNumberInUSD / secondNumberInUSD;
        calculationResult = firstNumber / secondNumber;
    }

    private void setNumbers() {
        if (calculationStack.size() > 1) {
            secondNumber = calculationStack.pop();
            firstNumber = calculationStack.pop();
            firstNumberInUSD = convertToUSD(firstNumber, baseCurrency);
            secondNumberInUSD = convertToUSD(secondNumber, baseCurrency);
        }
    }

    public void resetOperation() {
        isFirstOperation = false;
    }

    public float getCalculatorResult() {
        return calculationResult;
    }

    public double getResultInUSD() {
        return resultInUSD;
    }

    public void setNewOperation() {
        isFirstOperation = true;
        history.resetHistory();
    }

    public void operandOperation(float initialInput, ArithmeticOperand operand) {
        if ((this.operand != ArithmeticOperand.EQUAL && this.operand != operand)
                || (initialInput != 0 && !isFirstOperation)) {
            performCalculation(initialInput);
            Log.i("BaseCurrency1", baseCurrency);
        }
        else if (initialInput == 0 && isFirstOperation) {
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            this.history.pushHistory(history);
            Log.i("BaseCurrency2", baseCurrency);
        }
        else if (isFirstOperation) {
            calculationResult = initialInput;
            resultInUSD = convertToUSD(initialInput, baseCurrency);
            Log.i("BaseCurrency3", baseCurrency);
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            this.history.pushHistory(history);
        }

        setOperand(operand);
    }

    public void performCalculation(float initialInput) {
        if (initialInput != 0 && operand != ArithmeticOperand.EQUAL) {
            setFirstNumber(calculationResult, firstBaseCurrency);
            setSecondNumber(initialInput, baseCurrency);
            calculate();

            String history = baseCurrency + " " + String.valueOf(initialInput);
            this.history.pushHistory(history, operand);
        }
    }

    public String getCalculationHistory() {
        return history.getHistory();
    }

    public String getOperandString() {
        String operandString = "";
        switch (operand) {
            case ADD:
                operandString = " + ";
                break;
            case SUBTRACT:
                operandString = " - ";
                break;
            case MULTIPLY:
                operandString = " x ";
                break;
            case DIVIDE:
                operandString = " / ";
                break;
        }
        return operandString;
    }

    private double convertToUSD (float targetValue, String targetCurrency) {
        double USDValue = 0;
        try {
            USDValue = currencyRates.getDouble(targetCurrency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return targetValue / USDValue;
    }

    public double equivalentValue(String targetCurrency) {
        double targetCurrencyValue = 0;
        try {
            targetCurrencyValue = currencyRates.getDouble(targetCurrency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultInUSD * targetCurrencyValue;
    }
}

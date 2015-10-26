package com.andela.www.currencycalculator.helper;


import android.content.Context;
import android.widget.TextView;
import com.andela.www.currencycalculator.R;
import com.andela.www.currencycalculator.utility.ArithmeticOperand;
import com.andela.www.currencycalculator.utility.Calculator;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/14/15.
 */
public class InputHandler {

    // context
    private String targetCurrency = "USD";
    // screen
    private TextView screen;
    private TextView mini_screen;
    // input values
    private float initialInput = 0;
    private String currentInput = "";
    // history stack
    private Stack<String> backHistory = new Stack<>();
    // decimal notifier
    private boolean dotPressed = false;
    // calculator screen
    private Calculator calculator;

    public InputHandler(Context context, TextView screen, TextView mini_screen) {
        this.screen = screen;
        this.mini_screen = mini_screen;
        calculator = new Calculator(context);

        // set default screen
        setDisplay();
    }

    public void numberPressed(int number) {
        if (!isInvalidInput(number)) {
            if (!currentInput.equals("0")) currentInput += number;
            else currentInput = String.valueOf(number);
            initialInput = Float.valueOf(currentInput);
            backHistory.push(currentInput);
        }
        setDisplay();
    }

    public void setBaseCurrency(String baseCurrency) {
        calculator.setBaseCurrency(baseCurrency);
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void decimalPressed() {
        if (!dotPressed) {
            if (currentInput.equals("0")) currentInput = "0.";
            else currentInput += (initialInput == 0) ? "0." : "." ;
            initialInput = Float.valueOf(currentInput);
            backHistory.push(currentInput);
        }
        setDotPressed();
        setDisplay();
    }

    public void clearPressed() {
        calculator.resetCalculator();
        initialInput = 0;
        currentInput = "";
        backHistory.clear();
        setDotUnpressed();
        clearMiniDisplay();
        setDisplay();
    }

    public void backPressed() {
        if (backHistory.size() > 1) {
            // check removed string for decimal
            String inspectPop = backHistory.pop();
            if (inspectPop.substring(inspectPop.length() - 1).equals("."))
                setDotUnpressed();

            currentInput = backHistory.pop();
            initialInput = Float.valueOf(currentInput);
            backHistory.push(currentInput);
            setDisplay();
        }
        else
            clearPressed();
    }

    public void operandPressed(int operandId) {
        ArithmeticOperand operand = getOperand(operandId);
        calculator.operandOperation(initialInput, operand);
        clearInput();
    }

    public void equalPressed() {
        calculator.performCalculation(initialInput);
        calculator.setOperand(ArithmeticOperand.EQUAL);
        clearInput();
        calculator.setNewOperation();
    }

    private void clearInput() {
        setMiniDisplay();
        currentInput = "";
        initialInput = 0;
        setDotUnpressed();
        backHistory.clear();
        calculator.resetOperation();
        setMainDisplay();
    }

    private boolean isInvalidInput(int number) {
        if (!dotPressed && (initialInput == 0 && number == 0)) {
            currentInput = "0";
            return true;
        }
        return false;
    }

    private void setDotPressed() {
        dotPressed = true;
    }

    private void setDotUnpressed() {
        dotPressed = false;
    }

    private void setDisplay() {
        if (currentInput.equals("") || currentInput.equals("0")) {
            screen.setText("0");
        }
        else {
            screen.setText(currentInput);
        }
    }

    private void setMainDisplay() {
        String display = processScreenDisplay();
        screen.setText(display);
    }

    private String processScreenDisplay() {
        float calculationResult = calculator.getCalculatorResult();
        // convert calculationResult to string
        String display = String.valueOf(calculationResult);
        // check if initialInput is integer
        if (calculationResult % 1 == 0) {
            return display.split(Pattern.quote("."))[0];
        }
        return display;
    }

    private void setMiniDisplay() {
        String operandString = calculator.getOperandString();
        String display = calculator.getCalculationHistory() + operandString;
        mini_screen.setText(display);
    }

    private void clearMiniDisplay() {
        mini_screen.setText("");
    }

    public Number getEquivalentValue() {
        return calculator.equivalentValue(targetCurrency);
    }

    public double getUSDValue() {
        return calculator.getResultInUSD();
    }

    private ArithmeticOperand getOperand(int id) {
        switch (id) {
            case R.id.divide:
                return ArithmeticOperand.DIVIDE;
            case R.id.multiply:
                return ArithmeticOperand.MULTIPLY;
            case R.id.add:
                return ArithmeticOperand.ADD;
            case R.id.subtract:
                return ArithmeticOperand.SUBTRACT;
        }
        return ArithmeticOperand.EQUAL;
    }
}

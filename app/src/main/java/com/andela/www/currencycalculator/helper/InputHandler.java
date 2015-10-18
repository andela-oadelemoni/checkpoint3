package com.andela.www.currencycalculator.helper;


import android.content.Context;
import android.widget.TextView;
import com.andela.www.currencycalculator.utility.ArithmeticOperand;
import com.andela.www.currencycalculator.utility.Calculator;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/14/15.
 */
public class InputHandler {

    // context
    private Context context;
    private String baseCurrency = "USD";
    // screen
    private TextView screen;
    private TextView mini_screen;
    // input values
    private float calculationResult = 0;
    private float initialInput = 0;
    private String currentInput = "";
    // history stack
    private Stack<String> backHistory = new Stack<>();
    // decimal notifier
    private boolean dotPressed = false;
    private boolean isFirstOperation = true;
    // calculator screen
    private Calculator calculator = new Calculator();
    private CalculationHistory calculationHistory = new CalculationHistory();
    // active operand
    private ArithmeticOperand operand = ArithmeticOperand.EQUAL;

    public InputHandler(Context context, TextView screen, TextView mini_screen) {
        this.context = context;
        this.screen = screen;
        this.mini_screen = mini_screen;

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
        this.baseCurrency = baseCurrency;
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
        calculationResult = 0;
        initialInput = 0;
        currentInput = "";
        backHistory.clear();
        calculationHistory.resetHistory();
        setDotUnpressed();
        clearMiniDisplay();
        operand = ArithmeticOperand.EQUAL;
        isFirstOperation = true;
        setDisplay();
    }

    public void backPressed() {
        if (backHistory.size() > 1) {
            // check removed string for decimal
            String inspectPop = backHistory.pop();
            if (inspectPop.substring(inspectPop.length() - 1).equals(".")) setDotUnpressed();

            currentInput = backHistory.pop();
            initialInput = Float.valueOf(currentInput);
            backHistory.push(currentInput);
            setDisplay();
        }
        else
            clearPressed();
    }

    public void operandPressed(ArithmeticOperand operand) {
        operandOperation(operand);
        cleanUpOperation();
    }

    public void equalPressed() {
        performCalculation();
        setOperand(ArithmeticOperand.EQUAL);
        cleanUpOperation();
        isFirstOperation = true;
        calculationHistory.resetHistory();
    }

    private void operandOperation(ArithmeticOperand operand) {
        if ((this.operand != ArithmeticOperand.EQUAL && this.operand != operand)
                || (!currentInput.equals("") && !isFirstOperation)) {
            performCalculation();
        }
        else if (currentInput.equals("") && isFirstOperation) {
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            calculationHistory.pushHistory(history);
        }
        else if (isFirstOperation) {
            calculationResult = initialInput;
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            calculationHistory.pushHistory(history);
        }

        setOperand(operand);
    }

    private void cleanUpOperation() {
        setMiniDisplay();
        currentInput = "";
        initialInput = 0;
        setDotUnpressed();
        backHistory.clear();
        isFirstOperation = false;
        setMainDisplay();
    }

    private void performCalculation() {
        if (!currentInput.equals("") && operand != ArithmeticOperand.EQUAL) {
            calculator.setOperand(operand);
            calculator.setFirstNumber(calculationResult);
            calculator.setSecondNumber(initialInput);
            calculationResult = calculator.calculate();

            String history = baseCurrency + " " + String.valueOf(initialInput);
            calculationHistory.pushHistory(history, operand);
        }
    }

    private void setOperand(ArithmeticOperand operand) {
        this.operand = operand;
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
        if (currentInput.equals("") || currentInput.equals("0")) screen.setText("0");
        else screen.setText(currentInput);
    }

    private void setMainDisplay() {
        String display = processScreenDisplay();
        screen.setText(display);
    }

    private String processScreenDisplay() {
        // convert calculationResult to string
        String display = calculationResult +"";
        // check if initialInput is integer
        if (this.calculationResult % 1 == 0) return display.split(Pattern.quote("."))[0];
        else return display;
    }

    private void setMiniDisplay() {
        String operandString = getOperandString();
        String display = calculationHistory.getHistory() + operandString;
        mini_screen.setText(display);
    }

    private String getOperandString() {
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

    private void clearMiniDisplay() {
        mini_screen.setText("");
    }

    public Number getBaseValue() {
        return calculationResult;
    }
}

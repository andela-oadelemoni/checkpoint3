package com.andela.www.currencycalculator.helper;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.andela.www.currencycalculator.utility.ArithmeticOperand;
import com.andela.www.currencycalculator.utility.Calculator;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/14/15.
 */
public class InputHandler {

    // context
    private Context context;
    // screen
    private TextView screen;
    private TextView mini_screen;
    // input values
    private float firstNumber = 0;
    private float initialInput = 0;
    private String inputHistory = "";
    private String currentInput = "";
    // history stack
    private Stack<String> backHistory = new Stack<>();
    private ArrayDeque<String> computationHistory = new ArrayDeque<>();
    // decimal notifier
    private boolean dotPressed = false;
    private boolean isFirstOperation = true;
    // calculator screen
    private Calculator calculator = new Calculator();
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
        firstNumber = 0;
        initialInput = 0;
        currentInput = "";
        inputHistory = "";
        backHistory.clear();
        computationHistory.clear();
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

    public void additionPressed() {

        if (operand != ArithmeticOperand.EQUAL && operand != ArithmeticOperand.ADD) {
            performCalculation();
        }
        else if (firstNumber == 0 && isFirstOperation) firstNumber = initialInput;
        else if (!currentInput.equals("")) firstNumber += initialInput;
        setOperand(ArithmeticOperand.ADD);
        cleanUpOperation();
        //calculator.setFirstNumber(initialInput);
    }

    public void subtractionPressed() {

        if (operand != ArithmeticOperand.EQUAL && operand != ArithmeticOperand.SUBTRACT) {
            performCalculation();
        }
        else if (firstNumber == 0 && isFirstOperation) firstNumber = initialInput;
        else if (!currentInput.equals("")) firstNumber -= initialInput;
        setOperand(ArithmeticOperand.SUBTRACT);
        cleanUpOperation();
    }

    public void multiplicationPressed() {

        if (operand != ArithmeticOperand.EQUAL && operand != ArithmeticOperand.MULTIPLY) {
            performCalculation();
        }
        else if (firstNumber == 0 && isFirstOperation) firstNumber = initialInput;
        else if (!currentInput.equals("")) firstNumber *= initialInput;
        setOperand(ArithmeticOperand.MULTIPLY);
        cleanUpOperation();
    }

    public void divisionPressed() {

        if (operand != ArithmeticOperand.EQUAL && operand != ArithmeticOperand.DIVIDE) {
            performCalculation();
        }
        else if (firstNumber == 0 && isFirstOperation) firstNumber = initialInput;
        else if (!currentInput.equals("")) firstNumber /= initialInput;
        setOperand(ArithmeticOperand.DIVIDE);
        cleanUpOperation();
    }

    public void equalPressed() {
        performCalculation();
        cleanUpOperation();
        clearMiniDisplay();
        setOperand(ArithmeticOperand.EQUAL);
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
            calculator.setFirstNumber(firstNumber);
            calculator.setSecondNumber(initialInput);
            firstNumber = calculator.calculate();
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
        // convert firstNumber to string
        String display = firstNumber+"";
        // check if initialInput is integer
        if (this.firstNumber % 1 == 0) return display.split(Pattern.quote("."))[0];
        else return display;
    }

    private void setMiniDisplay() {
        String operandString = getOperandString();
        String display_number = processMiniScreenDisplay();
        String finalDisplay = display_number + operandString;
        mini_screen.setText(finalDisplay);
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

    private String processMiniScreenDisplay() {
        // convert firstNumber to string
        String display = String.valueOf(firstNumber);
        // check if initialInput is integer
        if (this.firstNumber % 1 == 0) return display.split(Pattern.quote("."))[0];
        else return display;
    }

    public Number getBaseValue() {
        return firstNumber;
    }
}

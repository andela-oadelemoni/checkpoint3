package com.andela.www.currencycalculator;


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
    // screen
    private TextView screen;
    private TextView mini_screen;
    // input values
    private float firstNumber = 0;
    private float initialInput = 0;
    private String currentInput = "";
    // history stack
    private Stack<String> backHistory = new Stack<>();
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
            currentInput += number;
            initialInput = Float.valueOf(currentInput);
            backHistory.push(currentInput);
        }
        setDisplay();
    }

    public void decimalPressed() {
        if (!dotPressed) {
            currentInput += (initialInput == 0) ? "0." : "." ;
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
        backHistory.clear();
        setDotUnpressed();
        clearMiniDisplay();
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
        setOperand(ArithmeticOperand.ADD);
        firstNumber += initialInput;
        setMiniDisplay();
        currentInput = "";
        initialInput = 0;
        setDotUnpressed();
        backHistory.clear();
        setDisplay();
        //calculator.setFirstNumber(initialInput);
    }

    public void subtractionPressed() {
        setOperand(ArithmeticOperand.SUBTRACT);
        if (firstNumber == 0 && isFirstOperation) firstNumber = initialInput;
        else firstNumber -= initialInput;
        setMiniDisplay();
        currentInput = "";
        initialInput = 0;
        setDotUnpressed();
        backHistory.clear();
        isFirstOperation = false;
        setDisplay();
    }

    private void setOperand(ArithmeticOperand operand) {
        this.operand = operand;
    }

    private boolean isInvalidInput(int number) {
        return !dotPressed && (initialInput == 0 && number == 0);
    }

    private void setDotPressed() {
        dotPressed = true;
    }

    private void setDotUnpressed() {
        dotPressed = false;
    }

    private void setDisplay() {
        if (currentInput.equals("")) screen.setText("0");
        else screen.setText(currentInput);
    }

    private void setMiniDisplay() {
        String operandString = "";
        String display_number = processMiniScreenDisplay();
        switch (operand) {
            case ADD:
                operandString = " +";
                break;
            case SUBTRACT:
                operandString = " -";
                break;
        }
        String mini_display = display_number+operandString;
        mini_screen.setText(mini_display);
    }

    private void clearMiniDisplay() {
        mini_screen.setText("");
    }

    private String processMiniScreenDisplay() {
        // convert firstNumber to string
        String display = firstNumber+"";
        // check if initialInput is integer
        if (this.firstNumber % 1 == 0) return display.split(Pattern.quote("."))[0];
        else return display;
    }
}

package com.andela.www.currencycalculator;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.Stack;

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
    private float initialInput = 0;
    private String currentInput = "";
    // history stack
    private Stack<String> backHistory = new Stack();
    // decimal notifier
    private boolean dotPressed = false;

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
        initialInput = 0;
        currentInput = "";
        backHistory.clear();
        setDotUnpressed();
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
        String mini_display = ((Number)initialInput).toString();
        mini_screen.setText(mini_display);
    }
}

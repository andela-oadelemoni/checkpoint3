package com.andela.www.currencycalculator;


import android.content.Context;
import android.widget.TextView;

/**
 * Created by kamiye on 10/14/15.
 */
public class InputHandler {

    private TextView screen;
    private TextView mini_screen;
    private Context context;
    private float initialInput = 0;
    private String currentInput = "";
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
        }
        setDisplay();
    }

    public void decimalPressed() {
        if (!dotPressed) {
            currentInput += (initialInput == 0) ? "0." : "." ;
            initialInput = Float.valueOf(currentInput);
        }
        dotPressed = true;
        setDisplay();
    }

    private boolean isInvalidInput(int number) {
        return !dotPressed && (initialInput == 0 && number == 0);
    }

    private void setDisplay() {
        if (currentInput.equals("")) screen.setText("0");
        else screen.setText(currentInput);
    }

    /*private void setMiniDisplay() {
        String mini_display = ((Number)initialInput).toString();
        mini_screen.setText(mini_display);
    }*/
}

package com.andela.www.currencycalculator.helper;

import android.util.Log;

import com.andela.www.currencycalculator.utility.ArithmeticOperand;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/18/15.
 */
public class CalculationHistory {

    private ArrayDeque<String> calculationHistory = new ArrayDeque<>();

    public void pushHistory(String string, ArithmeticOperand... operand) {
        String operandString = "";
        if (operand.length > 0) {
            operandString = getOperandString(operand[0]);
        }

        // check for float
        String[] floatCheck = string.split(Pattern.quote("."));
        // check if initialInput is integer
        if (Integer.valueOf(floatCheck[1]) == 0) string = floatCheck[0];

        String historyString = operandString + string;

        calculationHistory.push(historyString);
    }

    private String getOperandString(ArithmeticOperand operand) {
        switch (operand) {
            case ADD:
                return " + ";
            case SUBTRACT:
                return " - ";
            case MULTIPLY:
                return " x ";
            case DIVIDE:
                return " / ";
        }
        return "";
    }

    public String getHistory() {
        String history = "";
        Iterator<String> iterator = calculationHistory.descendingIterator();
        while (iterator.hasNext()) {
            history += iterator.next();
        }

        return history;
    }

    public void resetHistory() {
        calculationHistory.clear();
    }
}

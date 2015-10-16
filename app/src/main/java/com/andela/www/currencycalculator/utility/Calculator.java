package com.andela.www.currencycalculator.utility;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/14/15.
 */
public class Calculator {

    private float previousResult = 0;
    private float result;
    private float firstNumber;
    private float secondNumber;
    private ArithmeticOperand operand;
    private Stack<Float> historyStack = new Stack<>();
    private Stack<Float> calculationStack = new Stack<>();
    private boolean resultIsInt = false;

    /* SET FIRST NUMBER */
    public void setFirstNumber(float firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setSecondNumber(float secondNumber) {
        this.secondNumber = secondNumber;
    }

    public void setOperand(ArithmeticOperand operand) {
        this.operand = operand;
    }

    /* CALCULATION METHODS */

    // Addition method
    private void add() throws ArithmeticException {
        previousResult = firstNumber + secondNumber;
    }

    // Subtraction method
    private void subtract() throws ArithmeticException {
        previousResult = firstNumber - secondNumber;
    }

    // Multiplication method
    private void multiply() throws ArithmeticException {
        previousResult = firstNumber * secondNumber;
    }

    // Divide method
    private void divide() throws ArithmeticException {
        previousResult = firstNumber / secondNumber;
    }

    /* ACTION METHODS */

    public float calculate() {
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
        processResult(previousResult);
        historyStack.push(previousResult);
        if (resultIsInt) {
            resultIsInt = false;
            return (int) previousResult;
        }
        else return previousResult;
    }

    private void setNumbers() {
        if (calculationStack.size() > 1) {
            secondNumber = calculationStack.pop();
            firstNumber = calculationStack.pop();
        }
    }

    public void resetCalculator() {
        previousResult = 0;
        calculationStack.clear();
    }

    private void processResult(float result) {
        String floatToString = result+"";
        String[] array = floatToString.split(Pattern.quote("."));
        if (Integer.valueOf(array[1]) == 0) resultIsInt = true;
    }

    public float getHistory() {
        return historyStack.pop();
    }
}

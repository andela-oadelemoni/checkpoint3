package com.andela.www.currencycalculator.utility;

import com.andela.www.currencycalculator.helper.CalculationHistory;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/14/15.
 */
public class Calculator {

    private float previousResult = 0;
    private float result;
    private float firstNumber;
    private boolean isFirstOperation = true;
    private float secondNumber;
    private ArithmeticOperand operand = ArithmeticOperand.EQUAL;
    private Stack<Float> historyStack = new Stack<>();
    private Stack<Float> calculationStack = new Stack<>();
    private CalculationHistory history = new CalculationHistory();
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

    public void resetCalculator() {
        history.resetHistory();
        operand = ArithmeticOperand.EQUAL;
        previousResult = 0;
        calculationStack.clear();
    }

    public float getHistory() {
        return historyStack.pop();
    }

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

    private void setNumbers() {
        if (calculationStack.size() > 1) {
            secondNumber = calculationStack.pop();
            firstNumber = calculationStack.pop();
        }
    }

    private void processResult(float result) {
        String floatToString = result+"";
        String[] array = floatToString.split(Pattern.quote("."));
        if (Integer.valueOf(array[1]) == 0) resultIsInt = true;
    }

    /*private void operandOperation(ArithmeticOperand operand) {
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
    }*/
}

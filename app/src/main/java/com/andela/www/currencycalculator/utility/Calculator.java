package com.andela.www.currencycalculator.utility;

import com.andela.www.currencycalculator.helper.CalculationHistory;
import java.util.Stack;

/**
 * Created by kamiye on 10/14/15.
 */
public class Calculator {

    private float calculationResult = 0;
    private float firstNumber = 0;
    private boolean isFirstOperation = true;
    private float secondNumber = 0;

    private String baseCurrency = "USD";
    private ArithmeticOperand operand = ArithmeticOperand.EQUAL;
    private Stack<Float> calculationStack = new Stack<>();
    private CalculationHistory history = new CalculationHistory();

    private void setFirstNumber(float firstNumber) {
        this.firstNumber = firstNumber;
    }

    private void setSecondNumber(float secondNumber) {
        this.secondNumber = secondNumber;
    }

    public void setOperand(ArithmeticOperand operand) {
        this.operand = operand;
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
        isFirstOperation = true;
        calculationStack.clear();
    }

    // Addition method
    private void add() throws ArithmeticException {
        calculationResult = firstNumber + secondNumber;
    }

    // Subtraction method
    private void subtract() throws ArithmeticException {
        calculationResult = firstNumber - secondNumber;
    }

    // Multiplication method
    private void multiply() throws ArithmeticException {
        calculationResult = firstNumber * secondNumber;
    }

    // Divide method
    private void divide() throws ArithmeticException {
        calculationResult = firstNumber / secondNumber;
    }

    private void setNumbers() {
        if (calculationStack.size() > 1) {
            secondNumber = calculationStack.pop();
            firstNumber = calculationStack.pop();
        }
    }

    public void resetOperation() {
        isFirstOperation = false;
    }

    public float getCalculatorResult() {
        return calculationResult;
    }

    public void setNewOperation() {
        isFirstOperation = true;
        history.resetHistory();
    }

    public void operandOperation(float initialInput, ArithmeticOperand operand) {
        if ((this.operand != ArithmeticOperand.EQUAL && this.operand != operand)
                || (initialInput != 0 && !isFirstOperation)) {
            performCalculation(initialInput);
        }
        else if (initialInput == 0 && isFirstOperation) {
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            this.history.pushHistory(history);
        }
        else if (isFirstOperation) {
            calculationResult = initialInput;
            String history = baseCurrency + " " + String.valueOf(calculationResult);
            this.history.pushHistory(history);
        }

        setOperand(operand);
    }

    public void performCalculation(float initialInput) {
        if (initialInput != 0 && operand != ArithmeticOperand.EQUAL) {
            setFirstNumber(calculationResult);
            setSecondNumber(initialInput);
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
}

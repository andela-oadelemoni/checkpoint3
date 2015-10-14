package com.andela.www.currencycalculator.utility;

/**
 * Created by kamiye on 10/14/15.
 */
public class Calculator {

    private float initial = 0;
    private float firstNumber;
    private float secondNumber;

    // Setter methods
    public void setFirstNumber(float firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setSecondNumber(float secondNumber) {
        this.secondNumber = secondNumber;
    }

    // Calculation methods

    // Addition method
    public float add() {
        initial = firstNumber + secondNumber;
        return initial;
    }

    // Subtraction method
    public float subtract() {
        initial = firstNumber - secondNumber;
        return initial;
    }

    // Multiplication method
    public float multiply() {
        initial = firstNumber * secondNumber;
        return initial;
    }

    // Divide method
    public float divide() {
        initial = firstNumber / secondNumber;
        return initial;
    }
}

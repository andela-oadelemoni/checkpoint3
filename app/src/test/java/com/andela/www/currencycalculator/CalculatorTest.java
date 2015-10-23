package com.andela.www.currencycalculator;


import com.andela.www.currencycalculator.utility.ArithmeticOperand;
import com.andela.www.currencycalculator.utility.Calculator;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kamiye on 10/23/15.
 */
public class CalculatorTest {


    private Calculator calculator = new Calculator();
    private static final float FIRST_NUMBER = 5.0f;
    private static final float SECOND_NUMBER = 3.0f;


    @Test
    public void testOperation_additionFirstOperation() {
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.ADD);
        assertTrue("Addition assertion error", FIRST_NUMBER == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_additionNormalOperation() {
        float expected = FIRST_NUMBER + SECOND_NUMBER;
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.ADD);
        calculator.performCalculation(SECOND_NUMBER);

        assertTrue("Addition assertion error", expected == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_subtractionFirstOperation() {
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.SUBTRACT);
        assertTrue("Addition assertion error", FIRST_NUMBER == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_subtractionNormalOperation() {
        float expected = FIRST_NUMBER - SECOND_NUMBER;
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.SUBTRACT);
        calculator.performCalculation(SECOND_NUMBER);

        assertTrue("Addition assertion error", expected == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_multiplicationFirstOperation() {
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.MULTIPLY);
        assertTrue("Addition assertion error", FIRST_NUMBER == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_multiplicationNormalOperation() {
        float expected = FIRST_NUMBER * SECOND_NUMBER;
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.MULTIPLY);
        calculator.performCalculation(SECOND_NUMBER);

        assertTrue("Addition assertion error", expected == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_divisionFirstOperation() {
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.DIVIDE);
        assertTrue("Addition assertion error", FIRST_NUMBER == calculator.getCalculatorResult());

    }

    @Test
    public void testOperation_divisionNormalOperation() {
        float expected = FIRST_NUMBER / SECOND_NUMBER;
        calculator.operandOperation(FIRST_NUMBER, ArithmeticOperand.DIVIDE);
        calculator.performCalculation(SECOND_NUMBER);

        assertTrue("Addition assertion error", expected == calculator.getCalculatorResult());

    }


    @After
    public void resetCalculator() {
        calculator.resetCalculator();
    }


}

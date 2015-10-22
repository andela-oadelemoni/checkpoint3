package com.andela.www.currencycalculator;

import org.junit.Test;
import static org.junit.Assert.*;
import com.andela.www.currencycalculator.helper.CalculationHistory;

/**
 * Created by kamiye on 10/22/15.
 */
public class CalculationHistoryTest {

    private CalculationHistory calculationHistory = new CalculationHistory();

    public void setUp() {

    }

    @Test
    public void testHistory() {
        calculationHistory.pushHistory("2.1");
        assertEquals("2.1", calculationHistory.getHistory());
    }
    @Test
    public void testHistoryReset() {
        calculationHistory.resetHistory();
        assertEquals("", calculationHistory.getHistory());
    }

}

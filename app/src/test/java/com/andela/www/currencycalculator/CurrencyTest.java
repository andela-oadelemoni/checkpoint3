package com.andela.www.currencycalculator;

import com.andela.www.currencycalculator.model.Currency;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kamiye on 10/23/15.
 */
public class CurrencyTest {

    private Currency currency = new Currency("TST", 10);

    /* Test Setters */
    @Test
    public void testCurrencyName() {
        currency.setCurrencyName("NAME");
        assertEquals("NAME", currency.getCurrencyName());
    }

    @Test
    public void testCurrencyImage() {
        currency.setImage(20);
        assertEquals(20, currency.getImage());
    }
}

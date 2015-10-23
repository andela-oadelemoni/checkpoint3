package com.andela.www.currencycalculator;

import android.test.InstrumentationTestCase;
import com.andela.www.currencycalculator.adapter.CurrencyAdapter;
import com.andela.www.currencycalculator.model.Currency;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 10/23/15.
 */
public class CurrencyAdapterTest extends InstrumentationTestCase {

    private CurrencyAdapter currencyAdapter;

    public void setUp() {
        currencyAdapter = new CurrencyAdapter(getInstrumentation().getContext(), R.layout.spinner_rows, setUpCurrencyValues());
    }

    private List<Currency> setUpCurrencyValues() {
        List<Currency> currencyList = new ArrayList<>();

        Currency currency1 = new Currency("USD", 1);
        Currency currency2 = new Currency("GBP", 1);
        Currency currency3 = new Currency("EUR", 1);

        currencyList.add(currency1);
        currencyList.add(currency2);
        currencyList.add(currency3);

        return currencyList;
    }

    public void testAdapterCount() {
        assertEquals(3, currencyAdapter.getCount());
    }
}

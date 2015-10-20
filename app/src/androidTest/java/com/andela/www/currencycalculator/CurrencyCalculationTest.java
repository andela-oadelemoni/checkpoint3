package com.andela.www.currencycalculator;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.www.currencycalculator.activities.MainActivity;
import com.andela.www.currencycalculator.activities.TopTenActivity;

/**
 * Created by kamiye on 10/20/15.
 */
public class CurrencyCalculationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity mActivity;
    private Button testButton1;
    private Button testButton2;
    private TextView screen;
    private TextView mini_screen;

    public CurrencyCalculationTest() {
        super(MainActivity.class);
    }

    // flow of use
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();

        screen = (TextView) mActivity.findViewById(R.id.calculator_screen);
        mini_screen = (TextView) mActivity.findViewById(R.id.mini_screen);
    }

    private void touchAction(Button button) {
        TouchUtils.clickView(this, button);
        getInstrumentation().waitForIdleSync();

    }

    // As a user
    // when I tap on a number button
    // then I should see the number displayed on the screen
    public void testNumberPress() {
        testButton1 = (Button) mActivity.findViewById(R.id.number_1);
        testButton2 = (Button) mActivity.findViewById(R.id.number_2);



        touchAction(testButton1);

        String expected = "1";
        String actual = screen.getText().toString();

        assertEquals("Number Press assertion error", expected, actual);
        getInstrumentation().waitForIdleSync();

        touchAction(testButton2);

        expected = "12";
        actual = screen.getText().toString();

        assertEquals("Number Press assertion error", expected, actual);
    }

    // As a user
    // when I launch the app
    // and I tap on an operand
    // then I should see the operand on a mini screen attached to number 0 on the screen
    public void testOperandPress() {
        testButton1 = (Button) mActivity.findViewById(R.id.add);
        testButton2 = (Button) mActivity.findViewById(R.id.subtract);

        touchAction(testButton1);

        String expected = "USD 0 + ";
        String actual = mini_screen.getText().toString();

        assertEquals("Operand Press Assertion Error", expected, actual);

        touchAction(testButton2);

        expected = "USD 0 - ";
        actual = mini_screen.getText().toString();

        assertEquals("Operand Press Assertion Error", expected, actual);

    }

    // As a user
    // when I launch the app
    // and I tap on the decimal button
    // then I should see 0. displayed on the screen
    public void testDecimalPress() {
        testButton1 = (Button) mActivity.findViewById(R.id.decimal);

        touchAction(testButton1);

        String expected = "0.";
        String actual = screen.getText().toString();

        assertEquals("Operand Press Assertion Error", expected, actual);
    }

    // As a user
    // when I tap on a number
    // and I tap on an operand
    // and I tap on another number
    // when I tap on the equal button
    // then I see the result of the calculation on the screen
    public void testEqualButton() {
        testButton1 = (Button) mActivity.findViewById(R.id.number_6);
        touchAction(testButton1);
        testButton1 = (Button) mActivity.findViewById(R.id.subtract);
        touchAction(testButton1);
        testButton2 = (Button) mActivity.findViewById(R.id.number_2);
        touchAction(testButton2);
        testButton2 = (Button) mActivity.findViewById(R.id.equal);
        touchAction(testButton2);

        String expected = "4";
        String actual = screen.getText().toString();

        assertEquals("Calculation Assertion Error", expected, actual);
    }

    // As a user
    // when I tap on top ten button
    // then I should see another activity listing the list of top ten currency evaluations
    public void testTopTenActivityChange() {
        Instrumentation.ActivityMonitor nextActivity = getInstrumentation().addMonitor(TopTenActivity.class.getName(), null, false);

        testButton1 = (Button) mActivity.findViewById(R.id.topTen);
        touchAction(testButton1);

        TopTenActivity topTenActivity = (TopTenActivity) getInstrumentation().waitForMonitor(nextActivity);
        assertNotNull(topTenActivity);
    }

    // As a user
    // When I tap on a number
    // And I tap on an operand
    // And I tap on another number
    // And I tap on the equal button
    // When I tap on convert button
    // Then I should see the equivalent in the current spinner currency
    public void testConvertScreen() {
        // set target currency in spinner
        final Spinner spinner = (Spinner) mActivity.findViewById(R.id.destination_currency_picker);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(2);
            }
        });
        getInstrumentation().waitForIdleSync();

        TextView targetCurrencyScreen = (TextView) mActivity.findViewById(R.id.destination_currency);


        testButton1 = (Button) mActivity.findViewById(R.id.number_9);
        touchAction(testButton1);
        testButton1 = (Button) mActivity.findViewById(R.id.subtract);
        touchAction(testButton1);
        testButton2 = (Button) mActivity.findViewById(R.id.number_1);
        touchAction(testButton2);
        testButton2 = (Button) mActivity.findViewById(R.id.equal);
        touchAction(testButton2);
        testButton2 = (Button) mActivity.findViewById(R.id.conversionButton);
        touchAction(testButton2);

        String actual = targetCurrencyScreen.getText().toString();

        assertNotSame("Conversion button assertion error", "", actual);

    }

}

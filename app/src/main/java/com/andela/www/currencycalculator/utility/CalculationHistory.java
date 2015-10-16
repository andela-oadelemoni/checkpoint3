package com.andela.www.currencycalculator.utility;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by kamiye on 10/16/15.
 */
public class CalculationHistory {

    private ArrayDeque<String> history = new ArrayDeque<>();

    public String getHistory() {
        String historyString = "";
        for (Iterator<String> string = history.descendingIterator(); string.hasNext();) {
            historyString += string.next();
            String display[] = historyString.split(Pattern.quote("."));
            int decimal = Integer.valueOf(display[1]);
            // check if initialInput is integer
            if (decimal == 0) return display[0];
            else return historyString;
        }
        return historyString;
    }

    public void clearHistory() {
        history.clear();
    }

    public void addHistory(String string) {
        history.push(string);
    }
}

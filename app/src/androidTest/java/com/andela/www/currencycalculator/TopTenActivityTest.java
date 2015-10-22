package com.andela.www.currencycalculator;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.andela.www.currencycalculator.activities.TopTenActivity;
import com.andela.www.currencycalculator.adapter.TopTenAdapter;

/**
 * Created by kamiye on 10/22/15.
 */
public class TopTenActivityTest extends ActivityInstrumentationTestCase2<TopTenActivity> {

    private ListView topTenListView;
    private Activity mActivity;

    public TopTenActivityTest() {
        super(TopTenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.putExtra("USD_VALUE", 1);
        setActivityIntent(intent);
        mActivity = getActivity();
        topTenListView = (ListView) mActivity.findViewById(R.id.listview);

    }

    public void testListView_elementsCount() {
        getInstrumentation().waitForIdleSync();
        // sleep thread to allow complete synchronization
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("List View Count assertion error", 10, topTenListView.getCount());
    }

    public void testListView_childContent() {
        getInstrumentation().waitForIdleSync();
        // sleep thread to allow complete synchronization
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TopTenAdapter adapter = (TopTenAdapter) topTenListView.getAdapter();
        View view = adapter.getView(0, null, null);
        TextView currencyValue = (TextView) view.findViewById(R.id.currency_value);
        assertNotSame("List View child element assertion error", "", currencyValue.getText().toString());
    }
}

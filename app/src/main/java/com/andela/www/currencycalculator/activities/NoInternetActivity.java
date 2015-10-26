package com.andela.www.currencycalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andela.www.currencycalculator.R;

/**
 * Created by kamiye on 10/26/15.
 */
public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        Button refreshButton = (Button) findViewById(R.id.refresh_button);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoInternetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

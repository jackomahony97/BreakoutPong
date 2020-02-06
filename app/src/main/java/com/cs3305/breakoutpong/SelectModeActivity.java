package com.cs3305.breakoutpong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectModeActivity extends AppCompatActivity {

    Spinner dropdown;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);


        //get the spinner from the xml.
        dropdown = findViewById(R.id.et_mode);
        //create a list of items for the spinner.
        String[] items = new String[]{"Single", "2-Player"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        btn = findViewById(R.id.btn_continue_mode);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = dropdown.getSelectedItem().toString();
                if (text.equals("Single")) {
                    Intent myIntent = new Intent(SelectModeActivity.this, SingleGameActivity.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    SelectModeActivity.this.startActivity(myIntent);
                }
                else if (text.equals("2-Player")) {
                    Intent myIntent = new Intent(SelectModeActivity.this, TwoPlayerGameActivity.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    SelectModeActivity.this.startActivity(myIntent);
                }
            }
        });
    }
}
package com.cs3305.breakoutpong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class BluetoothActivity extends AppCompatActivity {

    Spinner dropdown;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        //get the spinner from the xml.
        dropdown = findViewById(R.id.et_bl_devices);
        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        btn = findViewById(R.id.btn_continue);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = dropdown.getSelectedItem().toString();
                if (text.equals("three")) {
                    Intent myIntent = new Intent(BluetoothActivity.this, SelectModeActivity.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    BluetoothActivity.this.startActivity(myIntent);
                }
            }
        });
    }
}

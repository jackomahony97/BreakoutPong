package com.cs3305.breakoutpong;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;


/**
 * Activity to choose an already paired bluetooth device
 * <p>
 * Includes code from https://developer.android.com/guide/topics/connectivity/bluetooth
 * and https://developer.android.com/guide/topics/ui/controls/spinner
 * and https://developer.android.com/reference/android/widget/ArrayAdapter
 * and https://developer.android.com/guide/topics/ui/dialogs
 * and https://developer.android.com/reference/android/widget/Button
 * with modifications as per developer.android.com code reuse licence
 */
public class BluetoothActivity extends AppCompatActivity {
    /**
     * Spinner : dropdown menu
     */
    private Spinner dropdown;

    /**
     * onCreate to check if device has a bluetooth adapter, if its on and and to list paired devices
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //link xml
        setContentView(R.layout.activity_bluetooth);

        //Activity to choose an already paired bluetooth device
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Device doesn't support Bluetooth
        if (bluetoothAdapter == null) {
            //Create an alert with an exit button
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Set alert message
            builder.setMessage("Device does not support bluetooth")
                    .setCancelable(false)
                    //Set alert button
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

        assert bluetoothAdapter != null;

        //If bluetooth adapter is there but not on
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //set to 1 as per android documentation
            int REQUEST_ENABLE_BLUETOOTH = 1;
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        }

        //paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        //counter for
        int i = 0;
        String[] items = new String[pairedDevices.size()]; //make an array (size of paired devices)


        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                items[i] = deviceName;
                i++;
            }
        }


        //get the spinner from the xml.
        dropdown = findViewById(R.id.et_bl_devices);
        //create a list of items for the spinner.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to dropdown
        dropdown.setAdapter(adapter);

        //grab continue button from xml
        Button btn = findViewById(R.id.btn_continue);
        //listener for the continue button
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Get the chosen paired bluetooth device
                String text = dropdown.getSelectedItem().toString();
                if (text.equals("HC05")) {
                    //start next activity
                    Intent myIntent = new Intent(BluetoothActivity.this, SelectModeActivity.class);
                    BluetoothActivity.this.startActivity(myIntent);
                }
            }
        });
    }


}




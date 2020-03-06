package com.cs3305.breakoutpong;

import android.widget.EditText;

/**
 * Validate EditText inputs
 */
public class inputValidate {

    private String stringInput;

    public inputValidate(EditText input){
        this.stringInput = input.getText().toString().trim();
    }

    public boolean validate(){
        boolean notValid = false;
        if (stringInput.equals("")){
            return notValid;
        }
        return !notValid;
    }
}

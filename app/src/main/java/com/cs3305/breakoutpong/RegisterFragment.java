package com.cs3305.breakoutpong;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass representing a representing a register
 * <p>
 * Includes code from http://www.androidtutorialshub.com/android-login-and-register-with-sqlite-database-tutorial/
 * with modifications as per comments on page regarding code reuse
 * and https://developer.android.com/guide/components/fragments
 * and https://developer.android.com/reference/android/widget/Button
 * and https://developer.android.com/reference/android/content/Intent
 * and https://developer.android.com/reference/android/widget/EditText
 * with modifications as per developer.android.com code reuse licence
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    /**
     * Button : represents the register button
     */
    private Button registerButton;
    /**
     * View : sets view
     */
    private View rootView;
    /**
     * DatabaseHelper : instance of the DatabaseHelper class
     */
    private DatabaseHelper databaseHelper;
    /**
     * EditText : name from xml
     */
    private EditText  nameEt;
    /**
     * EditText : email from xml
     */
    private EditText  emailEt;
    /**
     * EditText : password from xml
     */
    private EditText  passwordEt;
    /**
     * EditText : repeated password from xml
     */
    private EditText  repasswordEt;
    /**
     * Users : instnace of user class to help pass sql data
     */
    private Users user;

    /**
     * Constructor to set view and return view and call init methods
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container,false);
        initViews();
        initListeners();
        initObjects();
        return rootView;
    }

    /**
     * This method to initialize views
     */
    private void initViews() {
        nameEt  = rootView.findViewById(R.id.et_name);
        emailEt  = rootView.findViewById(R.id.et_email);
        passwordEt = rootView.findViewById(R.id.et_password);
        repasswordEt = rootView.findViewById(R.id.et_repassword);
        registerButton = rootView.findViewById(R.id.btn_register);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        registerButton.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(getActivity());
        user = new Users();
    }

    /**
     * This method is to listen the click on view, validate the input text fields
     * verify users not in database and add user to database
     *
     * @param v : represents the view
     */
    @Override
    public void onClick(View v) {
        // validate text fields
        boolean validateName = new inputValidate(nameEt).validate();
        boolean validateEmail = new inputValidate(emailEt).validate();
        boolean validatePassword = new inputValidate(passwordEt).validate();
        boolean validateRePassword = new inputValidate(repasswordEt).validate();
        if (validateName && validateEmail && validatePassword && validateRePassword) {
            //check if already in database
            if (!databaseHelper.checkUser(emailEt.getText().toString().trim())) {
                //set details
                user.setName(nameEt.getText().toString().trim());
                user.setEmail(emailEt.getText().toString().trim());
                user.setPassword(passwordEt.getText().toString().trim());
                //add to database
                databaseHelper.addUser(user);
                //global var
                ((GlobalClass) this.getActivity().getApplication()).setEmail(emailEt.getText().toString().trim());

                //start next activity
                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(intent);
            }
        } else if (!validateName){
            popup("Name not Valid");
        } else if (!validateEmail){
            popup("Email not Valid");
        } else {
            popup("Password not Valid");
        }
    }

    public void popup(String message) {
        //Create a popup with a custom message
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        //Set alert message
        builder.setMessage(message)
                .setCancelable(false)
                //Set a way to dismiss
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

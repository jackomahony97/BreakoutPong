package com.cs3305.breakoutpong;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass representing a login
 * <p>
 * Includes code from http://www.androidtutorialshub.com/android-login-and-register-with-sqlite-database-tutorial/
 * with modifications as per comments on page regarding code reuse
 *
 * and https://developer.android.com/guide/components/fragments
 * and https://developer.android.com/reference/android/widget/Button
 * and https://developer.android.com/reference/android/content/Intent
 * and https://developer.android.com/reference/android/widget/EditText
 * with modifications as per developer.android.com code reuse licence
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    /**
     * View : sets view
     */
    private View rootView;
    /**
     * EditText : email from xml
     */
    private EditText emailEt;
    /**
     * EditText : password from xml
     */
    private EditText passwordEt;
    /**
     * Button : login button
     */
    private Button loginButton;
    /**
     * DatabaseHelper : instance of the DatabaseHelper class
     */
    private DatabaseHelper databaseHelper;

    /**
     * Constructor to set view and return view and call init methods
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View : represents the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        initListeners();
        initObjects();
        return rootView;
    }

    /**
     * This method to initialize views
     */
    private void initViews() {
        emailEt = rootView.findViewById(R.id.et_email);
        passwordEt = rootView.findViewById(R.id.et_password);
        loginButton = rootView.findViewById(R.id.btn_login);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        loginButton.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(getActivity());
    }

    /**
     * This method is to listen the click on view, validate the input text fields
     * and verify login credentials from SQLite
     *
     * @param v : represents the view
     */
    @Override
    public void onClick(View v) {
        // validate text fields
        if (!emailEt.getText().toString().trim().equals("") && !passwordEt.getText().toString().trim().equals("")) {
            // if users in database
            if (databaseHelper.checkUser(emailEt.getText().toString().trim(), passwordEt.getText().toString().trim())) {
                //start next activity
                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(intent);
            }
        }
    }

}
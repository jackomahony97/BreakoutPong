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

    private View rootView;

    private DatabaseHelper databaseHelper;

    private EditText  nameEt;

    private EditText  emailEt;

    private EditText  passwordEt;

    private EditText  repasswordEt;

    private Users user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container,false);
        initViews();
        initListeners();
        initObjects();
        return rootView;
    }

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
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (!nameEt.getText().toString().trim().equals("") && !emailEt.getText().toString().trim().equals("") && !passwordEt.getText().toString().trim().equals("") && !repasswordEt.getText().toString().trim().equals("")) {
            if (!databaseHelper.checkUser(emailEt.getText().toString().trim())) {
                user.setName(nameEt.getText().toString().trim());
                user.setEmail(emailEt.getText().toString().trim());
                user.setPassword(passwordEt.getText().toString().trim());
                databaseHelper.addUser(user);
                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(intent);
            }
        }
    }
}

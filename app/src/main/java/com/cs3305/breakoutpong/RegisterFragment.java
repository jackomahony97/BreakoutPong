package com.cs3305.breakoutpong;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    /**
     * Button : represents the register button
     */
    private Button btn;
    private DatabaseHelper dbHelper = new DatabaseHelper(getContext());


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container,         false);

        btn = rootView.findViewById(R.id.btn_register );
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //----------------------------------------------------------------------------------
                // TODO check database to see if credentials are already registered
                //----------------------------------------------------------------------------------


                //----------------------------------------------------------------------------------
                // If user is not already registered
                //----------------------------------------------------------------------------------


                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }

}

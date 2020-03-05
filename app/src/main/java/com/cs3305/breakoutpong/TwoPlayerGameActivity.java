package com.cs3305.breakoutpong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * TwoPlayerGameActivity is an activity that represents the multi-player game (pong) model
 * <p>
 * Includes code from https://developer.android.com/reference/android/content/Intent
 * and https://developer.android.com/guide/topics/ui/dialogs
 * with modifications as per developer.android.com code reuse licence
 */
public class TwoPlayerGameActivity extends AppCompatActivity
{
    /**
     * gameView represents the view for the game
     */
    private TwoPlayerGameView gameView;

    /**
     * onCreate to set view and keep screen from going off
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initilaize gameView
        gameView = new TwoPlayerGameView(this);
        //set view
        setContentView(gameView);
        // keep screen from timing out
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


    /**
     * Method to handle press of back button
     */
    @Override
    public void onBackPressed() {
        //Create an alert with an exit button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Set alert message
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                //Set alert button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(TwoPlayerGameActivity.this, SelectModeActivity.class);
                        TwoPlayerGameActivity.this.startActivity(myIntent);
                        finish();
                    }
                })
                //Set alert button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * pausing the game when activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    /**
     * running the game when activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
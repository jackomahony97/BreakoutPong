package com.cs3305.breakoutpong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * SingleGameActivity is an activity that represents the single game (breakout) model
 * <p>
 * Includes code from https://developer.android.com/reference/android/content/Intent
 * and https://developer.android.com/guide/topics/ui/dialogs
 * with modifications as per developer.android.com code reuse licence
 */
public class SingleGameActivity extends AppCompatActivity
{
    /**
     * SingleGameView : represents the view for the game
     */
    private SingleGameView gameView;

    /**
     * onCreate to set view and keep screen from going off
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initilaize gameView
        gameView = new SingleGameView(this);
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
                        //TODO get rid of intent : should reset bluetooth
                        Intent myIntent = new Intent(SingleGameActivity.this, SelectModeActivity.class);
                        SingleGameActivity.this.startActivity(myIntent);
                        finish();
                        System.exit(0);
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
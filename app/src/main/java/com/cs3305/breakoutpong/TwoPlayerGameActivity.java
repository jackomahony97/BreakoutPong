package com.cs3305.breakoutpong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * SingleGameActivity is an activity that represents the single game (breakout) model
 */
public class TwoPlayerGameActivity extends AppCompatActivity
{
    /**
     * gameView represents the view for the game
     */
    private TwoPlayerGameView gameView;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        //
        gameView = new TwoPlayerGameView(this);
        //
        setContentView(gameView);
        // keep screen from timing out
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(TwoPlayerGameActivity.this, SelectModeActivity.class);
                        // myIntent.putExtra("key", value); //Optional parameters
                        TwoPlayerGameActivity.this.startActivity(myIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     *
     */
    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    /**
     *
     */
    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
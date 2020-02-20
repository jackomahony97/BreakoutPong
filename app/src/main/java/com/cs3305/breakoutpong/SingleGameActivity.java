package com.cs3305.breakoutpong;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


/**
 * SingleGameActivity is an activity that represents the single game (breakout) model
 */
public class SingleGameActivity extends AppCompatActivity
{
    /**
     * gameView represents the view for the game
     */
    private GameView gameView;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        //
        gameView = new GameView(this);
        //
        setContentView(gameView);
        // keep screen from timing out
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
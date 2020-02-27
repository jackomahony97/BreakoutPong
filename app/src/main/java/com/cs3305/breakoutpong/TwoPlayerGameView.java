package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TwoPlayerGameView extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //adding the brick to this class
    private Paddle paddle1;

    //adding the brick to this class
    private Paddle paddle2;

    //adding the ball to this class
    private Ball ball;


    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    private Handler handler;

    private BluetoothThread myBluetooth;

    private String s = "2";

    /**
     * Screen width
     */
    private int width;
    /**
     * Screen height
     */
    private int height;

    private boolean isGameOver;



    //Class constructor
    public TwoPlayerGameView(Context context) {
        super(context);


        isGameOver = false;

        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height= context.getResources().getDisplayMetrics().heightPixels;

        //initializing brick object
        paddle1 = new Paddle(context);

        paddle2 = new Paddle(context);

        //initializing ball object
        ball = new Ball(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        myBluetooth = new BluetoothThread(new Handler() {

            @Override
            public void handleMessage(Message message) {
                s = (String) message.obj;
            }
        });

        myBluetooth.start();
    }


    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }


    }


    private void update() {
        paddle1.update(s);
        paddle2.update(s);
        s="2";
        /**
         *  brick.getX(), brick.getY() - brick.height        brick.get(X) + brick.length, brick.getY() - brick.height
         *          --------------------------------------------
         *          |                                           |
         *          |                                           |
         *          --------------------------------------------
         *  brick.getX(), brick.getY()                         brick.get(X) + brick.length, brick.getY()
         *
         */


        if (ball.getY() > paddle1.getY()-25) {

            if (ball.getX() >= paddle1.getX() && ball.getX() <= (paddle1.getX() + 200)) {
                ball.update();
            } else {
                playing = false;
                isGameOver = true;
            }
        }else {
            ball.update();
        }


        if (ball.getY() > paddle2.getY()-25) {

            if (ball.getX() >= paddle2.getX() && ball.getX() <= (paddle2.getX() + 200)) {
                ball.update();
            } else {
                playing = false;
                isGameOver = true;
            }
        }else {
            ball.update();
        }


    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);
            //Drawing the brick
            canvas.drawBitmap(
                    paddle1.getBitmap(),
                    paddle1.getX(),
                    paddle1.getY(),
                    paint);

            //Drawing the brick
            canvas.drawBitmap(
                    paddle2.getBitmap(),
                    paddle2.getX(),
                    0,
                    paint);

            //Drawing the ball
            canvas.drawBitmap(
                    ball.getBitmap(),
                    ball.getX(),
                    ball.getY(),
                    paint);

            //Drawing the bricks
            int counter = 0;
            int tempscore = 0;


            int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));


            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);


                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
                canvas.drawText("Score = " + (tempscore),canvas.getWidth()/2,yPos + 200,paint);
            }else{
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                canvas.drawText("Score = " + (tempscore),canvas.getWidth()/2,yPos + 200,paint);

            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
            myBluetooth.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        myBluetooth = new BluetoothThread(new Handler() {

            @Override
            public void handleMessage(Message message) {
                s = (String) message.obj;
            }
        });

        myBluetooth.start();
    }
}
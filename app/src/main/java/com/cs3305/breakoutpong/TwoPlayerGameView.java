package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
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
    /**
     * MediaPlayer : sound for when ball hits paddle
     */
    private MediaPlayer paddleSound;
    /**
     * MediaPlayer : sound for when game ends
     */
    private MediaPlayer gameoverSound;
    /**
     * DatabaseHelper :
     */
    private DatabaseHelper databaseHelper;


    /**
     * Constructor to initialize variables and start bluetooth thread and handler
     *
     * @param context
     */
    public TwoPlayerGameView(Context context) {
        super(context);

        databaseHelper = new DatabaseHelper(context);

        paddleSound = MediaPlayer.create(context, R.raw.paddle_sound);
        gameoverSound = MediaPlayer.create(context, R.raw.gameover_sound);

        isGameOver = false;

        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height= context.getResources().getDisplayMetrics().heightPixels;

        //initializing brick object
        paddle1 = new Paddle(context);

        paddle2 = new Paddle(context);
        paddle2.setX(600);
        paddle2.setY(0);

        //initializing ball object
        ball = new Ball(context);
        ball.setTop(100);

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

    /**
     * Method that runs when gameThread.start() is called
     */
    @Override
    public void run() {
        // game loop
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }


    }

    /**
     * Method to update the frame
     */
    private void update() {
        // game button unique identifiers
        paddle1.update(s, "0\r", "1\r");
        paddle2.update(s, "0\r", "1\r");
        // arbitrary value to avoid false positives
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

        //player 1
        // if the ball passes the paddle vertically
        if (ball.getY() > paddle1.getY()-25) {
            // if the ball is within the paddles horizontal width
            if (ball.getX() >= paddle1.getX() && ball.getX() <= (paddle1.getX() + 200)) {
                //play sound that indicates ball has hit paddle
                paddleSound.start();
                //update ball
                ball.update();
            } else {
                //play sound that indicates the game is over
                gameoverSound.start();
                playing = false;
                isGameOver = true;
            }
        }else {
            ball.update();
        }

        //player 2
        // if the ball passes the paddle vertically
        if (ball.getY() < paddle2.getPaddleHeight()){
            // if the ball is within the paddles horizontal width
            if (ball.getX() >= paddle2.getX() && ball.getX() <= (paddle2.getX() + 200)) {
                //play sound that indicates ball has hit paddle
                paddleSound.start();
                //change ball direction as per gravity
                ball.changeUp();
            } else {
                //play sound that indicates the game is over
                gameoverSound.start();
                playing = false;
                isGameOver = true;
            }
        }else {
            ball.update();
        }


    }

    /**
     * Method to draw the frame
     */
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
                    paddle2.getY(),
                    paint);

            //Drawing the ball
            canvas.drawBitmap(
                    ball.getBitmap(),
                    ball.getX(),
                    ball.getY(),
                    paint);

            //Drawing the bricks


            int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));


            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);


                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
                paint.setTextSize(50);
                if (ball.getY() < 500) {
                    canvas.drawText("Winner = Player 1", canvas.getWidth() / 2, yPos + 200, paint);
                    if (databaseHelper.addToWinTotal()){
                        canvas.drawText("Win total incremented" , canvas.getWidth()/2,yPos + 200,paint);
                    }
                } else {
                    canvas.drawText("Winner = Player 2", canvas.getWidth() / 2, yPos + 200, paint);
                }
            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    /**
     * Method to control
     */
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the game is paused
     */
    public void pause() {
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
            myBluetooth.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * When the game is resumed
     */
    public void resume() {
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
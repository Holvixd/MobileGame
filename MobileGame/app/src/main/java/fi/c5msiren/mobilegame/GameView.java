package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Miika on 17.4.2017.
 */

public class GameView extends SurfaceView implements Runnable {

    // Boolean variable to track if the game is playing or not
    volatile boolean playing;

    private Thread gameThread = null;
    private Player player;

    // These are used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    // Array of obstacles
    private Obstacle[] obstacles;
    // Amount of obstacles
    private int obstacleCount = 3;

    private boolean isGameOver;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // Initialize player object
        player = new Player(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // Initializing obstacle array
        obstacles = new Obstacle[obstacleCount];
        for (int i = 0; i < obstacleCount; i++) {
            obstacles[i] = new Obstacle(context, screenX, screenY);
        }

        isGameOver = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                // When user releases on the screen
                player.stopAccelerating();
                break;
            case MotionEvent.ACTION_DOWN:
                // When user presses on the screen
                player.startAccelerating();
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            // To update the frame
            update();

            // To draw the frame
            draw();

            // To control
            control();
        }
    }

    private void update() {
        // Update player position
        player.update();

        // Updating the obstacle coordinate with respect to player speed
        for (int i = 0; i < obstacleCount; i++) {
            obstacles[i].update(player.getSpeed());
        }

        // Check if player collides with obstacle
        for (int i = 0; i < obstacleCount; i++) {
            if (Rect.intersects(player.getDetectCollision(), obstacles[i].getDetectCollision())) {
                //setting playing false to stop the game
                playing = false;
                //setting the isGameOver true as the game is over
                isGameOver = true;
            }
        }
    }

    private void draw() {
        // Check if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas so no one else can write code to the canvas
            canvas = surfaceHolder.lockCanvas();
            // Draw temp background
            canvas.drawColor(Color.BLACK);
            // Draw the player
            canvas.drawBitmap(player.getCurrentFrame(), player.getX(), player.getY(), paint);
            // Draw the obstacles
            for (int i = 0; i < obstacleCount; i++) {
                // If the obstacle is at the top of screen, flip it
                if (obstacles[i].isObstacleAtTopOfScreen()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(180);
                    Bitmap bitmap = Bitmap.createBitmap(
                            obstacles[i].getObstacleBitmap(),
                            0,
                            0,
                            obstacles[i].getObstacleBitmap().getWidth(),
                            obstacles[i].getObstacleBitmap().getHeight(),
                            matrix,
                            true
                    );
                    canvas.drawBitmap(
                            bitmap,
                            obstacles[i].getX(),
                            obstacles[i].getY(),
                            paint
                    );
                } else {
                    canvas.drawBitmap(
                            obstacles[i].getObstacleBitmap(),
                            obstacles[i].getX(),
                            obstacles[i].getY(),
                            paint
                    );
                }

            }

            // Unlock the canvas and post the code
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

        playing = false;

        // Stopping the thread
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        // Starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}

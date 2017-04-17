package fi.c5msiren.mobilegame;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by Miika on 17.4.2017.
 */

public class GameView extends SurfaceView implements Runnable {

    // Boolean variable to track if the game is playing or not
    volatile boolean playing;

    // The game thread
    private Thread gameThread = null;

    // Class constructor
    public GameView(Context context) {
        super(context);

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

    }

    private void draw() {

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

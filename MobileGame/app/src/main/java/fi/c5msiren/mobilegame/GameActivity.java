package fi.c5msiren.mobilegame;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    // Declaring GameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        // Get screen resolution
        Point size = new Point();
        display.getSize(size);

        // Initializing game view object
        gameView = new GameView(this, size.x, size.y);

        // Adding it to ContentView
        setContentView(gameView);
    }

    // Pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // Running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}

package fi.c5msiren.mobilegame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

public class GameActivity extends AppCompatActivity {

    // Declaring GameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing game view object
        gameView = new GameView(this);

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

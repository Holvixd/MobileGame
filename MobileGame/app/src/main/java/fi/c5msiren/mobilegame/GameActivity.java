package fi.c5msiren.mobilegame;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.widget.EditText;

public class GameActivity extends AppCompatActivity {

    // Declaring GameView
    private GameView gameView;

    private String playerName = "";

    private Point size = new Point();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view temporarily to activity_game
        // so it will have black background while typing the player name
        setContentView(R.layout.activity_game);

        // Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        // Get screen resolution
        display.getSize(size);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Player name");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerName = input.getText().toString();

                // Initializing game view object
                gameView = new GameView(GameActivity.this, size.x, size.y, playerName);
                // Adding it to ContentView
                setContentView(gameView);
                // And starting it
                gameView.resume();
            }
        });

        builder.show();
    }

}

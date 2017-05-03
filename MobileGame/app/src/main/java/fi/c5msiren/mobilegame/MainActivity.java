package fi.c5msiren.mobilegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Start button
    private ImageButton buttonPlay;
    // Highscore button
    private ImageButton buttonHighscore;

    // These are used for drawing
    private Paint paint;
    private Canvas canvas;

    // ImageView where to draw the stars
    private ImageView mImageView;

    // List of stars
    private ArrayList<Star> stars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the ImageView
        mImageView = (ImageView) findViewById(R.id.iv);
        // Getting the start button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        // Getting the highscore button
        buttonHighscore = (ImageButton) findViewById(R.id.buttonScore);

        // Getting display object
        Display display = getWindowManager().getDefaultDisplay();
        // Get screen resolution
        Point size = new Point();
        display.getSize(size);

        // Adding a click listener
        buttonPlay.setOnClickListener(this);
        buttonHighscore.setOnClickListener(this);

        // Initializing canvas
        Bitmap bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        // Draw background color to canvas
        canvas.drawColor(Color.BLACK);
        // Adding stars
        int starAmount = 120;
        for (int i = 0; i < starAmount; i++) {
            Star star = new Star(size.x, size.y);
            stars.add(star);
        }

        // Setting the color to white for stars
        paint = new Paint();
        paint.setColor(Color.WHITE);
        // Draw the stars
        for (Star star : stars) {
            paint.setStrokeWidth(star.getStarWidth());
            canvas.drawPoint(star.getX(), star.getY(), paint);
        }

        // Draw the stars to the ImageView
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonPlay) {
            // If user pressed the start button
            // Start game activity
            startActivity(new Intent(this, GameActivity.class));
        } else if (v.getId() == R.id.buttonScore) {
            // If user pressed the highscore button
            // Start highscore activity
            startActivity(new Intent(this, HighscoreActivity.class));
        }
    }
}

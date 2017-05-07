package fi.c5msiren.mobilegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Miika on 1.5.2017.
 */
public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Start AsynchronousTask getScores
        new getScores(this).execute("");
    }
}

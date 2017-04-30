package fi.c5msiren.mobilegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HighscoreActivity extends AppCompatActivity {

    ListView list;
    String[] name = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7"};
    int[] score = new int[] {350, 252, 235, 730, 134, 525, 623};
    Integer[] imageId = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        HighscoreArrayAdapter adapter = new
                HighscoreArrayAdapter(HighscoreActivity.this, name, score, imageId);
        list = (ListView)findViewById(R.id.listview);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(HighscoreActivity.this, "Player: " + name[+ position] + " Score: " + score[+ position], Toast.LENGTH_SHORT).show();

            }
        });
    }
}

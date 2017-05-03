package fi.c5msiren.mobilegame;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    // ListView for showing all the scores
    private ListView list;
    // JSONArray for storing the data fetched from backend
    private JSONArray jsonScores = new JSONArray();
    // String array for storing the name of players
    private String[] name;
    // Integer array for storing the player scores
    private int[] score;
    // Integer array for storing the images
    private Integer[] imageId;
    // Check if managed to connect to server
    private boolean connectToServer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Start AsynchronousTask getScores
        new getScores(this).execute("");
    }
}

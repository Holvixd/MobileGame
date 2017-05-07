package fi.c5msiren.mobilegame;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
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

/**
 * Created by Miika on 3.5.2017.
 */
public class getScores extends AsyncTask<String, String, String> {

    // Reference to the activity that calls this class
    private Activity activity;
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

    public getScores(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... args) {

        try {
            // Connect to the backend
            connectToServer = true;
            URL url = new URL("https://hidden-anchorage-16513.herokuapp.com/scores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // Get the json string from the backend and add it to JSONArray
            String line;
            while ((line = reader.readLine()) != null) {
                jsonScores = new JSONArray(line);
            }

            // Sort the array by the score
            jsonScores = sortScores(jsonScores);

            // The list only shows the best 9 scores, if it fetches more only display nine.
            if (jsonScores.length() > 9) {
                score = new int[9];
                name = new String[9];
                imageId = new Integer[9];
                // Loop through the JSONArray and get the data from individual
                // JSONObjects and store it in the arrays
                for (int i = 0; i < 9; i++) {
                    JSONObject scoreObject = new JSONObject(jsonScores.get(i).toString());
                    score[i] = scoreObject.getInt("score");
                    name[i] = scoreObject.getString("name");
                    imageId[i] = activity.getResources().getIdentifier("image_" + (i+1), "drawable", "fi.c5msiren.mobilegame");
                    System.out.println("SCOREOBJECT: " + scoreObject);
                }
            } else {
                // Make arrays length based what you got from the backend
                score = new int[jsonScores.length()];
                name = new String[jsonScores.length()];
                imageId = new Integer[jsonScores.length()];
                // Loop through the JSONArray and get the data from individual
                // JSONObjects and store it in the arrays
                for (int i = 0; i < jsonScores.length(); i++) {
                    JSONObject scoreObject = new JSONObject(jsonScores.get(i).toString());
                    score[i] = scoreObject.getInt("score");
                    name[i] = scoreObject.getString("name");
                    imageId[i] = activity.getResources().getIdentifier("image_" + (i+1), "drawable", "fi.c5msiren.mobilegame");
                    System.out.println("SCOREOBJECT: " + scoreObject);
                }
            }


            // Close bufferedreader, inputstream and connection.
            reader.close();
            in.close();
            conn.disconnect();

            // If cant connect to backend server, show error message
        } catch (MalformedURLException | SocketTimeoutException | ProtocolException e) {
            // Connecting to server failed
            connectToServer = false;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(),
                            "Server error occurred",
                            Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Done";
    }

    // After fetching the data show it on the adapter
    @Override
    protected void onPostExecute(String result) {

        if (connectToServer) {
            // Create new adapter with the fetched data
            HighscoreArrayAdapter adapter = new
                    HighscoreArrayAdapter(activity, name, score, imageId);
            ListView list = (ListView) activity.findViewById(R.id.listview);
            list.setAdapter(adapter);

            // On click show the score information
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(activity, "Player: " + name[+ position] + " Score: " + score[+ position], Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public JSONArray sortScores(JSONArray unsorted) throws JSONException {

        JSONArray sorted = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < unsorted.length(); i++) {
            jsonValues.add(unsorted.getJSONObject(i));
        }

        Collections.sort( jsonValues, new Comparator<JSONObject>() {
            //Sort the collection by score
            private static final String KEY_NAME = "score";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                Integer valA = new Integer(0);
                Integer valB = new Integer(0);

                try {
                    valA = (Integer) a.get(KEY_NAME);
                    valB = (Integer) b.get(KEY_NAME);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                return -valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return valA.compareTo(valB);
            }
        });

        for (int i = 0; i < unsorted.length(); i++) {
            sorted.put(jsonValues.get(i));
        }

        return sorted;
    }

}
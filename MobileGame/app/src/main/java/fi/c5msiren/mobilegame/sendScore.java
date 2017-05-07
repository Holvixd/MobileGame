package fi.c5msiren.mobilegame;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Miika on 5.5.2017.
 */

public class sendScore extends AsyncTask<String, String, String> {

    // Reference to the activity that calls this class
    private Activity activity;
    // Reference to the player score amount and name
    private int amount;
    private String name;

    public sendScore(Activity activity, int amount, String name) {
        this.activity = activity;
        this.amount = amount;
        this.name = name;
    }

    @Override
    protected String doInBackground(String... args) {

        try {
            // Connect to the back end
            URL url = new URL("https://hidden-anchorage-16513.herokuapp.com/scores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            // Add the score information to JSON object
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", name);
            jsonParam.put("score", amount);

            // Send the JSON data to the server
            OutputStream os = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bufferedWriter.write(jsonParam.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

            bufferedReader.close();
            inputStream.close();
            conn.disconnect();

        } catch (MalformedURLException | SocketTimeoutException | ProtocolException e) {
            // If cant connect to backend server, show error message
            // Connecting to server failed
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(),
                            "Server error occurred",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Done";
    }

    // After fetching the data show it on the adapter
    @Override
    protected void onPostExecute(String result) {

    }
}

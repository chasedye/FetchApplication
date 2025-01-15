package com.fetchApplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtility {
    private final String TAG = this.getClass().toString();
    private final DataCallback mainActivityCallback;

    NetworkUtility(DataCallback callback)
    {
        mainActivityCallback = callback;
    }


    public void fetchData(String urlStr)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // check response code
                    if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // success
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null)
                            stringBuilder.append(line + "\n");
                        // collected all data
                        reader.close();
                        mainActivityCallback.Success(new JSONArray(stringBuilder.toString()));
                    }
                } catch (MalformedURLException e) {
                    mainActivityCallback.EncounteredError(e.getMessage());
                } catch (IOException e) {
                    mainActivityCallback.EncounteredError(e.getMessage());
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mainActivityCallback.EncounteredError(e.getMessage());
                }

            }
        });
    }

}

package com.grohden.niceanimals;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveNiceAnimalsTask extends AsyncTask<URL, Integer, String> {
    private static final String SHIBE_API_ENDPOINT = "http://shibe.online/api/shibes?count=10";

    OkHttpClient mClient = new OkHttpClient();


    protected String doInBackground(URL... urls) {
        Request request = new Request.Builder()
                .url(SHIBE_API_ENDPOINT)
                .build();

        try {
            Response response = null;
            response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        // setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        try {
            JSONArray json = new JSONArray(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //showDialog("Downloaded " + result + " bytes");
    }
}

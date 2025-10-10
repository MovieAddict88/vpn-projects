package com.slipkprojects.ultrasshservice.util.securepreferences.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by: KervzCodes
 * Date Crated: 08/10/2020
 * Project: SocksHttp-master (ENGLISH)
 **/
public class vagol extends AsyncTask<String, String, String> {

    private Context context;
    private OnUpdateListener listener;
    private ProgressDialog progressDialog;
    private boolean isOnCreate;
	// MODIFIED: Replaced obfuscated URL with the new API endpoint.
    // IMPORTANT: Replace "127.0.0.1" with the actual IP address or domain of your admin panel server.
    private final static String Updater = "http://127.0.0.1/admin_panel/api.php";

    public vagol(Context context, OnUpdateListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void start(boolean isOnCreate) {
        this.isOnCreate = isOnCreate;
        execute();
    }

    public interface OnUpdateListener {
        void onUpdateListener(String result);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL(Updater);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response;

            while ((response = br.readLine()) != null) {
                sb.append(response);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error on getting data: " + e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!isOnCreate) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait while loading");
            progressDialog.setTitle("Checking Update");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!isOnCreate && progressDialog != null) {
            progressDialog.dismiss();
        }
        if (listener != null) {
            listener.onUpdateListener(s);
        }
    }
}
package me.root4.whereami;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 *
 * Require permission
 *     <uses-permission android:name="android.permission.INTERNET" />
 */
public class RestIntentService extends IntentService {

    public static final String ACTION_GETLOCATIONS = "me.root4.whereami.action.GETLOCATIONS";
    public static final String ACTION_POSTLOCATIONS = "me.root4.whereami.action.POSTLOCATIONS";

    public static final String EXTRA_URL = "me.root4.whereami.extra.URL";

    private static final String TAG = "REST_HTTP_HANDLER";

    public RestIntentService() {
        super("RestIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GETLOCATIONS.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                try {
                    handleActionGetLocations(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void handleActionGetLocations(String urlString) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String res = readStream(in);

            Log.d(TAG,res);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getStackTrace().toString() );
            throw e;
        }
        finally {
            urlConnection.disconnect();
        }

    }

    private void handleActionPostLocations(String urlString) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
        }       catch (Exception e)
        {
            Log.e(TAG, e.getStackTrace().toString() );
            throw e;
        }
        finally {
            urlConnection.disconnect();
        }

    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null){
            sb.append(line);
        }

        return sb.toString();
    }

}

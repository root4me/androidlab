package me.root4.whereami;

import android.app.IntentService;
import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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
                    //handleActionGetLocations(url);
                    handleActionPostLocations(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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

    private void handleActionPostLocations(String urlString) throws IOException, JSONException {
        URL url = new URL(urlString);


        DatabaseHelper db = new DatabaseHelper(this);
        List<Location> locations = db.getToBeSynced();

        for (int i = 0; i < locations.size(); i++) {

            JSONObject location = new JSONObject();
            location.put("longitude", locations.get(i).getLng());
            location.put("latitude", locations.get(i).getLat());
            // TODO might need to format this date and convert to utc
            location.put("captured", locations.get(i).getCaptureDate());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(String.valueOf(location));
                out.close();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inp = readStream(in);

                Log.d(TAG,inp);

                JSONObject res = new JSONObject(inp);
                if (res.has("_id"))
                {
                    Log.d(TAG,"save worked");
                }
                else
                {
                    Log.d(TAG,"save failed");
                }


            } catch (Exception e){
                Log.e(TAG,e.getMessage());
            }
            finally{
                urlConnection.disconnect();
            }
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

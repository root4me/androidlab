package me.root4.locatemelab;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
LocationListener{

    private static final String TAG = "Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
        /*
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
       */

                    // Get location info
                    GetLocationInfo(MainActivity.this);
                }
            });
        }
    }

    // Location service
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public void GetLocationInfo(Context context) {
        Log.d(TAG, "Inside GetLocationInfo");

        // Create instance of GoogleApiClient to make connection to google play service
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();

        }

        // create location request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);

        // if connected get location
        if (mGoogleApiClient.isConnected())
        {
            getLocation();
        }

    }

    private void getLocation()
    {
        // try to fetch last known location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null)
        {
            Log.d(TAG, "Location not found");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else
        {
            Log.d(TAG, "Location found in getLocation");
            Toast.makeText(this, "longitude @ " + location.getLongitude() + " latitude @ " + location.getLatitude(), Toast.LENGTH_LONG).show();
            displayLocationInfo(location);
        }
    }

    private void displayLocationInfo(Location location)
    {
        ((TextView) findViewById(R.id.lblLatitude)).setText(String.valueOf(location.getLatitude()));
        ((TextView) findViewById(R.id.lblLongitude)).setText(String.valueOf(location.getLongitude()));
        ((TextView) findViewById(R.id.lblBearing)).setText(String.valueOf(location.getBearing()));
        ((TextView) findViewById(R.id.lblAltitude)).setText(String.valueOf(location.getAltitude()));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Google play service connected");

/*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1
                    );

            return;
        }
*/
        getLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d(TAG, "Google play service suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Google play service concoction failed");
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG,"Location found in location update");
        // stop location update

        displayLocationInfo(location);
    }
}

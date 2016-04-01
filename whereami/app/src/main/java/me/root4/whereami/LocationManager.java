package me.root4.whereami;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by harish on 3/31/16.
 *
 * Manage location fetching and returns a location object
 */



public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final String TAG = "LocationManager";

    public abstract interface LocationCallback {
        public void handleLocation(Location location);
    }

    private LocationCallback mLocationCallback;

    public LocationManager(Context context) {

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
            GetLocation();
        }
        else
        {
            mGoogleApiClient.connect();
        }

    }

    private void GetLocation()
    {
        // try to fetch last known location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null)
        {
            Log.d(TAG, "Location not found");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // raise a call back to notify request location update
        }
        else
        {
            Log.d(TAG, "Location found in getLocation");
         //   displayLocationInfo(location);
            //raise a callback to notify loaction data
            mLocationCallback.handleLocation(location);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Google play service connected");

        GetLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLocationCallback.handleLocation(location);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

package me.root4.whereami;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by harish on 3/31/16.
 */

/**
 * Manage location fetching
 *
 * Require permission :
 *  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *
 *  Dependency in build.gradle
 *     compile 'com.google.android.gms:play-services:8.4.0'
 *      targetSdkVersion 22
 *          While running on emulator, doesn't seem to work if compiled with targetsdk 23
 *
 *  Device must have Settings / Location : on
 *
 */


public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final String TAG = "LocationManager";
    private boolean mwWaitingOnCallback = false;

    private LocationCallback mLocationCallback;
    private Context mContext;

    public interface LocationCallback {
        void onLocationFound(Location location);
        void onLocationStatus(String message);
    }



    public LocationManager(Context context, LocationCallback callback) {
        mContext = context;
        mLocationCallback = callback;

        // Create instance of GoogleApiClient to make connection to google play service
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // create location request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);

    }

    /**
     * Attempt to get the location.
     * If a location is found,  raises onLocationFound callback.
     * If location is not available, fire a request for location update.
     */
    public void getLocation() {

        if (!(mGoogleApiClient.isConnected()))
        {
            mGoogleApiClient.connect();
        }
        else
        {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            // check if the location data is recent
            if (location == null || isStaleLocation(location))
            {
                Log.d(TAG, "Location not found or stale location");
                if(!(mwWaitingOnCallback)) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    mwWaitingOnCallback = true;
                    // raise a call back to notify request location update
                    mLocationCallback.onLocationStatus("Waiting on Location update");
                }
            }
            else
            {
                Log.d(TAG, "Location found in getLocation");
                mwWaitingOnCallback = false;

                //raise a callback to notify loaction data
                mLocationCallback.onLocationFound(location);
            }
        }

    }

    public boolean isStaleLocation(Location location)
    {
        long diff = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            diff = (location.getElapsedRealtimeNanos() - SystemClock.elapsedRealtimeNanos())/1000000000;
        }

        Log.d(TAG, "ellapsed tine : " + String.valueOf(diff));

        // Location info is more than 10 mins old
        if (diff > 600)
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Google API connected
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Google play service connected");
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mwWaitingOnCallback = false;
        mLocationCallback.onLocationFound(location);

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Not going to look for any more location update");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mLocationCallback.onLocationStatus("Connection failed");
    }
}


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
            Log.d(TAG, "mGoogleApiClient null");
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
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
        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "mGoogleApiClient connected in LocationManager ");
            GetLocation();
        } else {
            mGoogleApiClient.connect();
        }

    }

    /**
     * Attempt to get the location. If location is not available, fire a requestLocation update.
     * Even if a location is found, this does not return the location and instead raises onLocationFound event
     */
    public void GetLocation() {

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null)
        {
            Log.d(TAG, "Location not found");
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
            //   displayLocationInfo(location);
            mwWaitingOnCallback = false;
            //raise a callback to notify loaction data
            mLocationCallback.onLocationFound(location);
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

        mLocationCallback.onLocationFound(location);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Not going to look for any more location update");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

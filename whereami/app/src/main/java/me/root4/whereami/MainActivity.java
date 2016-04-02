package me.root4.whereami;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationManager.LocationCallback {

    private static final String TAG = "Main_Activity";

    // Recycler view
    private List<NameValue> nameValueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NameValueAdapter mAdapter;

    boolean mPlot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Looking up location", Snackbar.LENGTH_LONG)
                        .show();

                LocationManager lm = new LocationManager(MainActivity.this, MainActivity.this);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new NameValueAdapter(nameValueList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //displayLocationInfo(null);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_locateH) {

            LocationManager lm = new LocationManager(this, this);
            //lm.GetLocation();

            Snackbar.make((RelativeLayout) findViewById(R.id.activity_main_content), "Looking up location", Snackbar.LENGTH_LONG).show();

        } else if (id == R.id.nav_locateL) {
            Snackbar.make((RelativeLayout) findViewById(R.id.activity_main_content), "Not implemented", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.nav_plot) {

            mPlot = true;
            LocationManager lm = new LocationManager(this, this);

        } else if (id == R.id.nav_history) {
            Snackbar.make((RelativeLayout) findViewById(R.id.activity_main_content), "Not implemented", Snackbar.LENGTH_LONG).show();

            startActivity(new Intent(this,LocationHistoryActivity.class));
        }
        if (id == R.id.nav_send) {
            Snackbar.make((RelativeLayout) findViewById(R.id.activity_main_content), "Not implemented", Snackbar.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationFound(Location location) {
        Log.i(TAG,"Location found " + String.valueOf(location.getLatitude()));

        Snackbar.make((RelativeLayout) findViewById(R.id.activity_main_content), "Location found " + String.valueOf(location.getLatitude()), Snackbar.LENGTH_LONG).show();
        displayLocationInfo(location);

        if (mPlot == true)
        {
            // navigate to plot page
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("EXTRA_LATITUDE", location.getLatitude());
            intent.putExtra("EXTRA_LONGITUDE", location.getLongitude());
            startActivity(intent);
        }

        DatabaseHandler db = new DatabaseHandler(this);
        db.addLocation(new me.root4.whereami.Location(location.getLatitude(), location.getLongitude(), location.getTime()));
        db.getLocation(1);

        db.getAllLocations();
    }

    @Override
    public void onLocationStatus(String message) {

        Snackbar
                .make((RelativeLayout) findViewById(R.id.activity_main_content), message, Snackbar.LENGTH_LONG).show();

    }

    private void displayLocationInfo(Location location) {

        NameValue nameValue = new NameValue("Latitude",  String.valueOf(location.getLatitude()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Longitude", String.valueOf(location.getLongitude()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Altitude",  String.valueOf(location.getAltitude()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Accuracy",  String.valueOf(location.getAccuracy()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Bearing", String.valueOf(location.getBearing()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Ellapsed ",  String.valueOf(location.getElapsedRealtimeNanos()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Provider", String.valueOf(location.getProvider()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("speed", String.valueOf(location.getSpeed()));
        nameValueList.add(nameValue);

        nameValue = new NameValue("Time", String.valueOf(DateFormat.format("MM/dd/yyyy hh:mm:ss", location.getTime())));
        nameValueList.add(nameValue);


        mAdapter.notifyDataSetChanged();
    }
}

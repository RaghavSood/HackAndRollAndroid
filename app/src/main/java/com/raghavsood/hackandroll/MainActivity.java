package com.raghavsood.hackandroll;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button surpriseButton;
    int i = 0;
    final static int LOCATION_CALLBACK_FLAG = 1000;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    boolean requestedUpdates = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(60*1000);
        mLocationRequest.setFastestInterval(1000);

        surpriseButton = (Button) findViewById(R.id.btn_download);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationUpdates();
            }
        });
    }

    public void getLocationUpdates() {
        checkPermission();
        startLocationUpdates();
    }

    public void startLocationUpdates() {
        if (!hasLocationPermission()) {
            Toast.makeText(this, "We need the location permission!", Toast.LENGTH_LONG).show();
            return;
        }
        if(hasLocationPermission() && !requestedUpdates) {
            //noinspection MissingPermission
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            requestedUpdates = true;
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        if(hasLocationPermission() && !requestedUpdates) {
            //noinspection MissingPermission
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            requestedUpdates = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void checkPermission() {
        if(!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CALLBACK_FLAG);
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private boolean hasLocationPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1 || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CALLBACK_FLAG:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "We have the location permission!", Toast.LENGTH_LONG).show();
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "We need the location permission!", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            // Create an Intent to go to the About activity
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent); // Launch the About activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "Latitude: " +location.getLatitude() + " Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT ).show();
    }
}

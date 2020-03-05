package com.example.appication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;

import android.os.Bundle;

import android.os.Looper;

import android.util.Log;
import android.widget.Toast;

import com.example.appication.ui.notifications.NotificationsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double MyLocationLat;
    private double MyLocationLng;
    private LatLng CurrentLocation;
    private LocationRequest requestingLocationUpdate;
    private LocationCallback locationCallback;
    private LatLng Sample;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String ,Object> CurrentPosition = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (status == ConnectionResult.SUCCESS) {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE );
                CreateBackGroundLocation();
            }
        }


            CreateUI();
            CreateLocationRequest();



                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    private void CreateUI() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    MyLocationLat = location.getLatitude();
                    MyLocationLng = location.getLongitude();
                    CurrentLocation = new LatLng(MyLocationLat, MyLocationLng);
                    mMap.addMarker(new MarkerOptions().position(CurrentLocation));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 7));
                    db.collection("users").document("CurrentPosition")
                            .update("lat", MyLocationLat,"lng", MyLocationLng)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Updata data firestore", "Update Success");
                                }
                            });
                }
            }
        };


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Sample = new LatLng(13.7563,100.5018);
        mMap.addMarker(new MarkerOptions().position(Sample));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        Enablelocation();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                
                return false;
            }
        });

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    MyLocationLat = location.getLatitude();
                    MyLocationLng = location.getLongitude();
                    CurrentLocation = new LatLng(MyLocationLat, MyLocationLng);
                    CurrentPosition.put("lat", MyLocationLat);
                    CurrentPosition.put("lng", MyLocationLng);
                    db.collection("users").document("CurrentPosition").set(CurrentPosition)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                    mMap.addMarker(new MarkerOptions().position(CurrentLocation));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation,7));
                }

            }
        });

        // Create a new user with a first and last name
        Map<String, Object> users = new HashMap<>();
            users.put("Purple", "ทิงกีวิงกี");
            users.put("Green", "ดิบซี่");
            users.put("Yellow", "ล่าลา");
            users.put("Red", "โพ");

        db.collection("users").document("เทเลทับบี้").set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Set Data on firestore", "Connecting Successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Set Data on firestore", "Connecting Error", e);
            }
        });



    }




    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdate!=null) {
            startLocationUpdate();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        CreateUI();
        CreateLocationRequest();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location is not use now",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void Enablelocation() {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            else {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }

        }
    }


    private void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(requestingLocationUpdate, locationCallback, Looper.myLooper());
    }


    protected void CreateLocationRequest() {
        requestingLocationUpdate = LocationRequest.create();
        requestingLocationUpdate.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        requestingLocationUpdate.setInterval(10000);
        requestingLocationUpdate.setFastestInterval(5000);
        requestingLocationUpdate.setNumUpdates(1000);
    }


    private void CreateBackGroundLocation() {
        boolean PermissionAccessFindLocationApproved = ActivityCompat.checkSelfPermission(MapsActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (PermissionAccessFindLocationApproved) {
            boolean BackGroundLocationPermissionAppoved = ActivityCompat.checkSelfPermission(MapsActivity.this
                    , Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (BackGroundLocationPermissionAppoved) {

            }

            else {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }

        else {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_BACKGROUND_LOCATION}, LOCATION_REQUEST_CODE);
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }





}




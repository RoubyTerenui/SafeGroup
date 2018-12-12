package com.project.safegroup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.DescriptionData;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.ExpandableMemberAdapter;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.MemberData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dataBase.model.OtherState;
import dataBase.model.SelfState;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private DatabaseReference myRef;
    private ValueEventListener myListener;
    private static GoogleMap mMap;
    private static MarkerOptions mo;
    private static Marker myMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static MarkerOptions hmo;
    private static Marker hisMarker;
    private static LatLng hisCoordinates;
    private static LatLng myCoordinates;
    private static boolean positionAvailable;
    private static String userID;
    private static String groupID;
    private static TextView titleText;
    private static TextView descriptionText;
    private static TextView distanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        titleText = findViewById(R.id.title);
        descriptionText = findViewById(R.id.textDescription);
        distanceText = findViewById(R.id.textDistance);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                positionAvailable = false;
            } else {
                userID = extras.getString("USER_ID");
                groupID = extras.getString("GROUP_ID");
                loadDatas();
            }
        }
        sendPosition();
        mapFragment.getMapAsync(this);
        Log.d("create","I CREATE");
    }

    private void sendPosition() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("CHANGED", location.getLatitude() + ": " + location.getLongitude());
                myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
                //set changes in window
                if (myMarker != null)
                    myMarker.setPosition(myCoordinates);
                else {
                    mo = new MarkerOptions().position(myCoordinates).title("Me").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    myMarker = mMap.addMarker(mo);
                }

                if (hisMarker != null) {
                    double distanceEnKm = Math.acos(Math.sin(myCoordinates.latitude) * Math.sin(hisCoordinates.latitude) + Math.cos(myCoordinates.latitude) * Math.cos(hisCoordinates.latitude) * Math.cos(hisCoordinates.longitude - myCoordinates.longitude)) * 6371;
                    distanceText.setText(String.format(getResources().getString(R.string.localisation_distance), (Math.round((distanceEnKm<1)?distanceEnKm*1000:distanceEnKm)+((distanceEnKm<1)?"m":"km"))));
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 14.0f));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Log.d("Try","Begin");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Try","version upper");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Try","permission ok");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                Log.d("Try","permission send");
                return;
            }
            else{
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
            }
        } else {
            Log.d("Try","version lower");
            locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Result","received");
        switch (requestCode) {
            case 10:
                Log.d("Result","case10");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Result","positive result");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Log.d("Result","permission check pass");
                    locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
                    Log.d("Result","request send");
                    return;
                }
        }
    }

    private void loadDatas() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("group").child(groupID).child("members").child(userID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Resources res = getResources();
                String name = ((String) dataSnapshot.child("name").getValue());
                String date = ((String) dataSnapshot.child("last_Update").getValue());
                String description = "";
                if (dataSnapshot.child("selfState").getValue() != null) {
                    int descriptionIndex = (((Long) dataSnapshot.child("selfState").child("stateDescription").getValue()).intValue());
                    String[] preciseState = res.getStringArray(R.array.precise_state);
                    description = preciseState[descriptionIndex];
                }
                titleText.setText("Danger : " + description);
                descriptionText.setText(String.format(res.getString(R.string.localisation_description), name, date));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("position");
        myListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double latitude = ((Double) dataSnapshot.child("lat").getValue());
                Double longitude = ((Double) dataSnapshot.child("long").getValue());
                hisCoordinates = new LatLng(latitude, longitude);
                //set changes in window
                if (hisMarker != null)
                    hisMarker.setPosition(hisCoordinates);
                else {
                    hmo = new MarkerOptions().position(hisCoordinates).title("Him").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    hisMarker = mMap.addMarker(hmo);
                }
                positionAvailable = true;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hisCoordinates, 14.0f));
                if (myMarker != null) {
                    double distanceEnKm = Math.acos(Math.sin(myCoordinates.latitude) * Math.sin(hisCoordinates.latitude) + Math.cos(myCoordinates.latitude) * Math.cos(hisCoordinates.latitude) * Math.cos(hisCoordinates.longitude - myCoordinates.longitude)) * 6371.0;
                    distanceText.setText(String.format(getResources().getString(R.string.localisation_distance), (Math.round((distanceEnKm<1)?distanceEnKm*1000:distanceEnKm)+((distanceEnKm<1)?"m":"km"))));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(myListener);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("callback","THERE I CALL");
        if(mo!=null){
            Log.d("callback","AND I HAD MARKER");
            myMarker = mMap.addMarker(mo);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 14.0f));
        }
        if(hmo!=null){
            Log.d("callback","AND I HAD MARKER");
            hisMarker = mMap.addMarker(hmo);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hisCoordinates, 14.0f));
        }
    }

    @Override
    protected void onDestroy() {
        locationManager.removeUpdates(locationListener);
        myRef.removeEventListener(myListener);
        super.onDestroy();
    }
}

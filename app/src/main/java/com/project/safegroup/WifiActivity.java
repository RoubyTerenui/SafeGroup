package com.project.safegroup;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiActivity extends AppCompatActivity {
    TextView wifiName = null;
    Button button_delete = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        wifiName = findViewById(R.id.textView_wifi);
        button_delete = findViewById(R.id.button_delete);
        updateWifiName();

        final Button button = findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                final String ssid = wifiInfo.getSSID();
                int temp = wifiInfo.getNetworkId();
                final String networkid = Integer.toString(temp);
                //final String bssid = wifiInfo.getBSSID();
                Log.d("wifi", "networkid = " + networkid);
                Log.d("wifi", "SSID = " + wifiInfo.getSSID());


                if (networkid == null) {
                    Toast.makeText(getBaseContext(), getString(R.string.please_connect_wifi), Toast.LENGTH_SHORT);
                } else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(WifiActivity.this);
                    dialog.setTitle(getString(R.string.add_this_wifi));
                    dialog.setMessage(  String.format(getString(R.string.confirm_add_wifi), ssid)  );

                    // Setting Positive "Yes" Button
                    dialog.setPositiveButton(getString(R.string.confirm),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog

                                    addWifiToSafePlace(networkid, ssid);

                                    //Ici le code pour faire rejoindre un user à un groupe

                                }
                            });

                    // Setting Negative "NO" Button
                    dialog.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    dialog.show();
                }
            }
        });


        button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WifiActivity.this);
                dialog.setTitle(getString(R.string.delete_wifi));
                dialog.setMessage(getString(R.string.confirm_delete_wifi));

                dialog.setPositiveButton(getString(R.string.delete),
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                deleteWifi();
                            }
                        });

                // Setting Negative "NO" Button
                dialog.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                dialog.show();

            }
        });
    }

    public void deleteWifi(){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        final DatabaseReference ref=mDatabase.child(FirebaseAuth.getInstance().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    dataSnapshot.child("wifi").getRef().removeValue();

                    wifiName.setText("None");
                    button_delete.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateWifiName(){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        final DatabaseReference ref=mDatabase.child(FirebaseAuth.getInstance().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    String wifiId = dataSnapshot.child("wifi").getValue(String.class);
                    WifiManager wifiManager = (WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                    if(wifiId != null) {
                        try {
                            String ssid = wifiManager.getConfiguredNetworks().get(Integer.parseInt(wifiId)).SSID;
                            wifiName.setText(ssid);
                            button_delete.setEnabled(true);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        button_delete.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void addWifiToSafePlace(final String bssid, final String ssid){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        final DatabaseReference ref=mDatabase.child(FirebaseAuth.getInstance().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    ref.child("wifi").setValue(bssid);
                    wifiName.setText(ssid);
                    button_delete.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

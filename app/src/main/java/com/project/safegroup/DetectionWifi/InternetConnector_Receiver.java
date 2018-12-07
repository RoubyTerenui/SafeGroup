package com.project.safegroup.DetectionWifi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.GroupSelection.GroupSelectionDataAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class InternetConnector_Receiver extends BroadcastReceiver {
	public InternetConnector_Receiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
				ConnectivityManager connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();

				// Check internet connection and accrding to state change the
				// text of activity by calling method
				if (networkInfo != null && networkInfo.isConnected()) {
					WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					String wifiId = Integer.toString(wifiInfo.getNetworkId());

                    Log.d("Wifi", "wifi connecté au reseau " + wifiInfo.getSSID() + " possedant l'id " + wifiId);

					notifyGroupsHome(wifiId);

				} else {
					Log.d("Wifi", "wifi déconnecté");
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    private void notifyGroupsHome(final String wifiId) {
        //Reference to the database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        DatabaseReference ref=mDatabase.child(FirebaseAuth.getInstance().getUid()).child("wifi");

        //Add an event to load group data and load the group view
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String wifi = dataSnapshot.getValue(String.class);
                if(wifi.equals(wifiId)){
					Log.d("WIFI", "Il s'agit du wifi home de l'utilisateur, changement de l'etat à 'safe' (a implementer)");
				}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });
	}
}

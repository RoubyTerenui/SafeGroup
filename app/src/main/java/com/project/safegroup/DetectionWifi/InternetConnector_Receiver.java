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

import java.util.ArrayList;
import java.util.Date;

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
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref=mDatabase.getRef();

        //Add an event to load group data and load the group view
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            	Log.d("parametre wifiId", wifiId);
                String wifi = dataSnapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("wifi").getValue(String.class);

                if(wifiId.equals(wifi)){
					Log.d("WIFI", "Il s'agit du wifi home de l'utilisateur, changement de l'etat à 'safe' (a implementer)");

					//recupération des groups de l'utilisateur
					ArrayList<String> groups = new ArrayList<>();
					Iterable<DataSnapshot> it = dataSnapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("groups").getChildren();
					for(DataSnapshot data : it){
						Log.d("Group", data.child("group_id").getValue(String.class));
						groups.add(data.child("group_id").getValue(String.class));
					}

					//Pour chaque groupe
					for(String group : groups){
						long state = dataSnapshot.child("group").child(group).child("members").child(FirebaseAuth.getInstance().getUid()).child("state").getValue(Long.class);
						if(state == 2){
							//Changer l'etat pour 'dans un lieu sur'
							dataSnapshot.child("group").child(group).child("members").child(FirebaseAuth.getInstance().getUid()).child("state").getRef().setValue(1);
							dataSnapshot.child("group").child(group).child("members").child(FirebaseAuth.getInstance().getUid()).child("last_Update").getRef().setValue(new Date().toString());
						}
					}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });
	}
}

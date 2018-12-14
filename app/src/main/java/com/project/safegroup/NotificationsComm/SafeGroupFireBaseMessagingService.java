package com.project.safegroup.NotificationsComm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class SafeGroupFireBaseMessagingService extends FirebaseMessagingService {
    public SafeGroupFireBaseMessagingService(){
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0) {
            Map<String , String > payload = remoteMessage.getData();
            if(payload.get("username")!=null) {
                System.out.println("messssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                System.out.println("name" + payload.get("username"));
                System.out.println("newState" + payload.get("newState"));
                showNotification(payload);
            }
            else{
                if(payload.get("groupName")!=null){
                    System.out.println("askkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                    System.out.println("name" + payload.get("groupName"));
                    showNotificationBis(payload);
                }
                else{
                    if(payload.get("unknown")!=null){
                        System.out.println("Updaaaaaaaaaaaaaattttttttteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        System.out.println("name" + payload.get("state"));
                        showNotificationV3(payload);
                    }
                }

            }
        }
    }

    private void showNotification(Map<String,String > payload) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.titlenotificationState,payload.get("username")))
                .setContentText(getResources().getString(R.string.notificationDangerState,payload.get("username")))
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "SafeGroupChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        manager.notify(0,builder.build());

    }
    private void showNotificationBis(Map<String,String > payload) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.titlenotificationState, payload.get("groupName")))
                .setContentText(getResources().getString(R.string.notificationAskedState, payload.get("groupName")))
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "SafeGroupChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        manager.notify(0,builder.build());

    }

    private void showNotificationV3(Map<String,String > payload) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.titlenotificationState,"SafeGroup"))
                .setContentText(getResources().getString(R.string.notificationUnresolvedState))
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "SafeGroupChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        manager.notify(0,builder.build());

    }

    @Override
    public void onNewToken(String Token){
     FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                final String refreshToken = instanceIdResult.getToken();
                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(refreshToken);;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for (DataSnapshot childy:dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groups").getChildren()) {
                            dataSnapshot.child("groups").child((String)childy.child("group_id").getValue()).child("members").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").getRef().setValue(refreshToken);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
    });

    }

}

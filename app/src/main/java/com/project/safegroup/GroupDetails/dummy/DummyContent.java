package com.project.safegroup.GroupDetails.dummy;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataBase.model.Group;
import dataBase.model.User;
import retrofit2.http.HEAD;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Group> ITEMS = new ArrayList<Group>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Group> ITEM_MAP = new HashMap<String, Group>();

    static {

        final DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  DataSnapshot dataSnapshot1=dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groups");
               // List<String> listGr_Id=new ArrayList<String>();
                //for (DataSnapshot datasnapshot3: dataSnapshot.getChildren() ) {
                  //  listGr_Id.add((String)datasnapshot3.child("group_id").getValue());
                  //  System.out.println((String)datasnapshot3.child("group_id").getValue());
                //}

                // do your stuff here with value
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });


    }

    private static void addItem(Group item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGr_id(), item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

}

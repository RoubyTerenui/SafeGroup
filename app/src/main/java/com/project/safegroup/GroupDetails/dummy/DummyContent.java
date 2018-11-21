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
import dataBase.model.Member;
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




    }

    private static void addItem(Group item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGr_id(), item);
    }

    public static void update(){
        ITEMS.clear();
        ITEM_MAP.clear();

        final DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot children:dataSnapshot.child("users").child(currentID).child("groups").getChildren()) {

                    String group_id = children.child("group_id").getValue().toString();
                    System.out.println(group_id);

                    DataSnapshot datatemp = dataSnapshot.child("group").child(group_id);
                    Group group = new Group();
                    group.setGr_id(group_id);
                    group.setAdministrator(datatemp.child("administrator").getValue(String.class));
                    group.setName(datatemp.child("name").getValue(String.class));

                    List<Member> members = new ArrayList<>();
                    for (DataSnapshot data:datatemp.child("members").getChildren()   ) {
                        members.add(data.getValue(Member.class));
                    }
                    group.setMembers(members);
                    addItem(group);
                }

                // do your stuff here with value
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

}

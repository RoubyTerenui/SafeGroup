package com.project.safegroup.GroupDetails.dummy;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import dataBase.model.Group;

public class DBManager {
    public static String str;
    public static Group grp;

    public static String getCurrentUserId(){
       return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static Group getGroupById(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("group").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String name = (String)dataSnapshot.child("name").getValue();
                //grp.setName( name);
                String id = (String)dataSnapshot.child("gr_id").getValue();
                //grp.setGr_id(id);
                String admin = (String)dataSnapshot.child("administrator").getValue();
                //grp.setAdministrator(admin);
                List<Integer> typeOfGroup = (List<Integer>)dataSnapshot.child("typeOfGroup").getValue();
                //grp.setTypeOfGroup(typeOfGroup);
                List<String> members = (List<String>)dataSnapshot.child("members").getValue();
               // grp.setMembers(members);

                grp = new Group(name, id, admin, typeOfGroup, FirebaseDatabase.getInstance().getReference());
                grp.setMembers(members);

                System.out.println("Nom du groupe : " + name + admin + " et membre : " + members.get(0) );
                // do your stuff here with value
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return grp;
    }

}

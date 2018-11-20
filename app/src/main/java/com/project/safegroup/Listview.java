package com.project.safegroup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataBase.model.Group;

public class Listview extends AppCompatActivity {
    public static final List<Group> ITEMS = new ArrayList<Group>();
    public static final Map<String, Group> ITEM_MAP = new HashMap<String, Group>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        //  ---METHOD TO ADD TO A LIST ALL THE GROUP ITEM ASSOCIATED AT THE ACTUAL USER
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> listString=new ArrayList<String>();
                System.out.println(dataSnapshot.child("users").child(user.getUid()).child("listGroupId").getValue());
                for (DataSnapshot children:dataSnapshot.child("users").child(user.getUid()).child("listGroupId").getChildren()) {
                    System.out.println((String) children.getValue());
                    listString.add((String) children.getValue());
                }
                if(listString.size()>0){
                    for (String gr_id:listString) {
                        Group group=dataSnapshot.child("group").child(gr_id).getValue(Group.class);
                        addItem(group);

                    }
                }


                // do your stuff here with value
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        // --- END OF THE METHOD TO GET ALL THE GROUP OF THE ACTUAL USER

    }
    private static void addItem(Group item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGr_id(), item);
    }
}

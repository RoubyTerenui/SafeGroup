package com.project.safegroup.GroupDetails.dummy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.GroupList;
import com.project.safegroup.MainActivity;

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
    public static final List<GroupData> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, GroupData> ITEM_MAP = new HashMap<>();

    private static void addItem(GroupData item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGroupID(), item);
    }



    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

    public static void update(final RecyclerView recyclerView, final GroupList groupList, final boolean mTwoPane) {

        ITEMS.clear();
        ITEM_MAP.clear();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot children:dataSnapshot.child("users").child(currentID).child("groups").getChildren()) {

                    String group_id = children.child("group_id").getValue().toString();
                    System.out.println(group_id);

                    DataSnapshot datatemp = dataSnapshot.child("group").child(group_id);
                    GroupData groupData = new GroupData();
                    groupData.setGroupID(group_id);
                    groupData.setAdminName(datatemp.child("administrator").getValue(String.class));
                    groupData.setGroupName(datatemp.child("name").getValue(String.class));
                    
                    addItem(groupData);
                }

                recyclerView.setAdapter(new GroupList.SimpleItemRecyclerViewAdapter((MainActivity)groupList.getActivity(), DummyContent.ITEMS, mTwoPane));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });
    }
}

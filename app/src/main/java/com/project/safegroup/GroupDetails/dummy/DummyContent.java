package com.project.safegroup.GroupDetails.dummy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.GroupDataAdapter;
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

public class DummyContent {

    public static final ArrayList<GroupData> ITEMS = new ArrayList<>();
    public static final Map<String, GroupData> ITEM_MAP = new HashMap<>();
    private static Context mContext;
    private static ListView mListView;

    private static void addItem(GroupData item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGroupID(), item);
    }

    public static void create(ListView listView, GroupList groupList, Context context) {
        ITEMS.clear();
        ITEM_MAP.clear();
        mContext = context;
        mListView = listView;
        final String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(currentID).child("groups");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(currentID,"trying");
                for (DataSnapshot children:dataSnapshot.getChildren()) {
                    Log.d(currentID,((String)children.child("name").getValue()));
                    GroupData groupData = new GroupData(((String)children.child("name").getValue()),((String)children.child("group_id").getValue()),((boolean)children.child("favorite").getValue()),((boolean)children.child("party").getValue()));
                    addItem(groupData);
                    Log.d("dummylenght"," " + DummyContent.ITEMS.size());
                }
                Log.d("tailleItemstofit"," "+DummyContent.ITEM_MAP.size());
                mListView.setAdapter(new GroupDataAdapter(DummyContent.ITEMS, mContext));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });
    }
}

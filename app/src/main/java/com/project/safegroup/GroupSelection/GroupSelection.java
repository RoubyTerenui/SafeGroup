package com.project.safegroup.GroupSelection;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

import java.util.ArrayList;

public class GroupSelection extends Fragment {
    private static final String TAG = "com.louis.safegroup.GroupSelection";
    private Button sendButton; //Button to send the state of the user on the selected group
    private static ListView groupList; //View of the groups available
    private static ArrayList<GroupSelectionData> groupDatas = new ArrayList<>(); //List of the groups available
    private static Context mContext;
    private DatabaseReference userGroupRef;
    private ValueEventListener grouplistener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialise the view and th link to the object in the view
        View view = inflater.inflate(R.layout.group_selection,container,false);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        groupList = (ListView) view.findViewById(R.id.groupSelectionList);
        sendButton.setClickable(false);
        sendButton.setBackgroundColor(Color.DKGRAY);
        mContext = getContext();
        //Search the groups of the users on the firebase server
        loadDatas();

        //When the button is clicked, modify the state in all selected groups
        sendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<String> group = groupIdToSend(groupDatas);
                ((MainActivity)getActivity()).sendNotificationTo(groupIdToSend(groupDatas));
            }
        });

        // When the user click on a group, select or deselect the group and color it
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(groupDatas.get(position).select())
                {
                    view.setBackgroundColor(getResources().getColor(R.color.colorBlueButton));
                }
                else
                {
                    view.setBackgroundColor(Color.WHITE);
                }
                if(isSelection(groupDatas)){
                    sendButton.setClickable(true);
                    sendButton.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    sendButton.setClickable(false);
                    sendButton.setBackgroundColor(Color.DKGRAY);
                }
            }
        });
        return view;
    }

    public void loadDatas(){

        //Reference to the database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userGroupRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("groups");
        grouplistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupDatas = new ArrayList<>();
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                    Boolean favori = data.child("favorite").getValue(Boolean.class);
                    String id = data.child("group_id").getValue(String.class);
                    String name = data.child("name").getValue(String.class);
                    groupDatas.add(new GroupSelectionData(name,false,favori,id));
                }
                GroupSelectionDataAdapter adapter = new GroupSelectionDataAdapter(groupDatas,mContext);
                groupList.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        userGroupRef.addValueEventListener(grouplistener);
    }

    private boolean isSelection(ArrayList<GroupSelectionData> groupDatas){
        //Return true if at least one group is selected
        for (GroupSelectionData data:groupDatas) {
            if(data.isSelected()==true){
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> groupIdToSend(ArrayList<GroupSelectionData> groupDatas){
        //Return the list of the id of the selected groups
        ArrayList<String> groupIdToSend = new ArrayList<>();
        for (GroupSelectionData data:groupDatas) {
            if(data.isSelected()==true){
                groupIdToSend.add(data.getId());
            }
        }
        return groupIdToSend;
    }

    @Override
    public void onDestroy() {
        userGroupRef.removeEventListener(grouplistener);
        super.onDestroy();
    }
}

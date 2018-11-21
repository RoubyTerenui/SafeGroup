package com.project.safegroup.GroupSelection;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private static final String TAG = "com.louis.safegroup.GroupQuad";
    private Button sendButton;
    private static ListView groupList;
    private static ArrayList<GroupSelectionData> groupDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.group_selection,container,false);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        groupList = (ListView) view.findViewById(R.id.groupSelectionList);

        loadDatas();

        sendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<String> group = groupIdToSend(groupDatas);
                ((MainActivity)getActivity()).sendNotificationTo(groupIdToSend(groupDatas));
            }
        });
        return view;
    }

    public void loadDatas(){

        sendButton.setClickable(false);
        sendButton.setBackgroundColor(Color.DKGRAY);

        //RECUPERATION DES DONNEES TEST
        final DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("groups");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupDatas =new ArrayList<>();
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                    Boolean favori = data.child("favoris").getValue(Boolean.class);
                    String id = data.child("group_id").getValue(String.class);
                    String name = data.child("name").getValue(String.class);
                    groupDatas.add(new GroupSelectionData(name,false,favori,id));
                }
                GroupSelectionDataAdapter adapter = new GroupSelectionDataAdapter(groupDatas,getContext());
                groupList.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("PROBLEME DE CONNEXION");
            }
        });

        // ListView Item Click Listener
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

    }

    private boolean isSelection(ArrayList<GroupSelectionData> groupDatas){
        for (GroupSelectionData data:groupDatas) {
            if(data.isSelected()==true){
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> groupIdToSend(ArrayList<GroupSelectionData> groupDatas){
        ArrayList<String> groupIdToSend = new ArrayList<>();
        for (GroupSelectionData data:groupDatas) {
            if(data.isSelected()==true){
                groupIdToSend.add(data.getId());
            }
        }
        return groupIdToSend;
    }


}

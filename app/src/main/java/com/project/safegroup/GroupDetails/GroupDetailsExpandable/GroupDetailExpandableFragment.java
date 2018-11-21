package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.dummy.DummyContent;
import com.project.safegroup.GroupDetails.dummy.GroupData;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.GroupSelection.GroupSelectionDataAdapter;
import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dataBase.model.Group;
import dataBase.model.User;

public class GroupDetailExpandableFragment extends Fragment {
    private static final String TAG = "com.louis.safegroup.GroupDetailExpandableFragment";
    private static ExpandableListView groupList;
    private static ArrayList<MemberData> groupDatas = new ArrayList<>();
    private static ArrayList<String> groupDescriptions = new ArrayList<>();
    public static final String ARG_ITEM_ID = "item_id";
    private GroupData mItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getGroupName());
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_detail_expandable,container,false);
        groupList = (ExpandableListView) view.findViewById(R.id.expandableListGroup);
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) view.findViewById(R.id.groupName)).setText("Administrateur : " + mItem.getAdminName());
            loadDatas();
        }

        groupList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
            @Override
            public void onGroupExpand(int groupPosition) {
                for(int i=0; i<groupList.getExpandableListAdapter().getGroupCount(); i++) {
                    if(i != groupPosition)
                        groupList.collapseGroup(i);
                }
            }
        });

        groupList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                if(parent.isGroupExpanded(groupPosition))
                {
                    parent.collapseGroup(groupPosition);
                }
                else {
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });
        return view;
    }

    public void loadDatas(){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("group").child(mItem.getGroupID());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                        groupDatas =new ArrayList<>();
                        groupDescriptions= new ArrayList<>();
                    for (DataSnapshot members:dataSnapshot.child("members").getChildren()) {
                        String name = members.child("name").getValue(String.class);
                        String id = members.getKey();
                        int state = members.child("state").getValue(Integer.class);
                        int statePrecision = members.child("state_Precision").getValue(Integer.class);
                        String editorName =  members.child("nameModifier").getValue(String.class);
                        String editorDate =  members.child("last_Update").getValue(String.class);
                        groupDatas.add(new MemberData(name,id,state));
                        Resources res = getResources();
                        String[] states = res.getStringArray(R.array.state);
                        String[] preciseState = res.getStringArray(R.array.precise_state);
                        String description;
                        if(name.equals(editorName))
                        {
                            description = String.format(res.getString(R.string.self_description_member),states[state],preciseState[statePrecision],editorDate);
                        }
                        else {
                            description = String.format(res.getString(R.string.description_member),states[state],preciseState[statePrecision],editorDate,editorName);
                        }
                        groupDescriptions.add(description);
                    }

                    ExpandableMemberAdapter adapter = new ExpandableMemberAdapter(groupDatas,groupDescriptions,getContext());
                    groupList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }
}

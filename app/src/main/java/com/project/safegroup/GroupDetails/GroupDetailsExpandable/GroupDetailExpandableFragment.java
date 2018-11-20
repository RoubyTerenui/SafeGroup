package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.GroupSelection.GroupSelectionDataAdapter;
import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupDetailExpandableFragment extends Fragment {
    private static final String TAG = "com.louis.safegroup.GroupDetailExpandableFragment";
    private ExpandableListView groupList;
    private ArrayList<MemberData> groupDatas = new ArrayList<>();
    private ArrayList<String> groupDescriptions = new ArrayList<>();
    private ExpandableMemberAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.group_detail_expandable,container,false);
        groupList = (ExpandableListView) view.findViewById(R.id.expandableListGroup);

        loadDatas();
        return view;
    }

    public void loadDatas(){

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> nameIds = new ArrayList<>();
        ArrayList<Integer> states = new ArrayList<>();
        ArrayList<Integer> stateDescriptions = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<String> editNames = new ArrayList<>();
        groupDatas =new ArrayList<>();


        //RECUPERATION DES DONNEES TEST
        names.add("Justin");
        names.add("Antoine");
        names.add("Dimitri");
        nameIds.add("group1");
        nameIds.add("group2");
        nameIds.add("group3");
        states.add(0);
        states.add(1);
        states.add(2);
        stateDescriptions.add(0);
        stateDescriptions.add(5);
        stateDescriptions.add(3);
        dates.add(new Date(2018,11,12));
        dates.add(new Date(2018,11,18));
        dates.add(new Date(2018,11,15));
        editNames.add("Jacques");
        editNames.add("Jean");
        editNames.add("Dimitri");
        //FIN DE RECUPERATION

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss");
        for (int i =0;i<names.size();i++) {
            groupDatas.add(new MemberData(names.get(i),nameIds.get(i),states.get(i)));

            Resources res = getResources();
            String[] state = res.getStringArray(R.array.state);
            String[] preciseState = res.getStringArray(R.array.precise_state);
            String description;
            if(names.get(i).equals(editNames.get(i)))
            {
                description = String.format(res.getString(R.string.self_description_member),state[states.get(i)],preciseState[stateDescriptions.get(i)],formatter.format(dates.get(i)));
            }
            else {
                description = String.format(res.getString(R.string.description_member),state[states.get(i)],preciseState[stateDescriptions.get(i)],formatter.format(dates.get(i)),editNames.get(i));
            }
            groupDescriptions.add(description);
        }

        adapter = new ExpandableMemberAdapter(groupDatas,groupDescriptions,getContext());
        groupList.setAdapter(adapter);

        groupList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
            @Override
            public void onGroupExpand(int groupPosition) {
                for(int i=0; i<adapter.getGroupCount(); i++) {
                    if(i != groupPosition)
                        groupList.collapseGroup(i);
                }
            }
        });

        groupList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                parent.expandGroup(groupPosition);
                return true;
            }
        });
    }


}

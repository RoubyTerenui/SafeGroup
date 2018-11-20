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

import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

import java.util.ArrayList;

public class GroupSelection extends Fragment {
    private static final String TAG = "com.louis.safegroup.GroupQuad";
    private Button sendButton;
    private ListView groupList;
    private ArrayList<GroupSelectionData> groupDatas = new ArrayList<>();

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
        ArrayList<String> groupNames = new ArrayList<>();
        ArrayList<Boolean> isSelected = new ArrayList<>();
        ArrayList<Boolean> isFavorite = new ArrayList<>();
        ArrayList<String> groupIds = new ArrayList<>();
        groupDatas =new ArrayList<>();


        //RECUPERATION DES DONNEES TEST
        groupNames.add("group1");
        groupNames.add("group2");
        groupNames.add("group3");
        groupIds.add("group1");
        groupIds.add("group2");
        groupIds.add("group3");
        for (int i =0;i<groupNames.size();i++) {
            isSelected.add(false);
            isFavorite.add(false);
        }
        //FIN DE RECUPERATION

        for (int i =0;i<groupNames.size();i++) {
            groupDatas.add(new GroupSelectionData(groupNames.get(i),isSelected.get(i),isFavorite.get(i),groupIds.get(i)));
        }


        GroupSelectionDataAdapter adapter = new GroupSelectionDataAdapter(groupDatas,getContext());

        // Assign adapter to ListView
        groupList.setAdapter(adapter);

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

package com.project.safegroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class GroupSelection extends Fragment {
    private static final String TAG = "com.louis.safegroup.GroupQuad";
    private Button sendButton;
    private ListView groupList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.group_selection,container,false);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        groupList = (ListView) view.findViewById(R.id.groupSelectionList);


        sendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFragment(6);
            }
        });
        return view;
    }
}

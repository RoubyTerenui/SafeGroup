package com.project.safegroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.louis.safegroup.R;

import java.util.ArrayList;

public class MyGroupsActivity extends AppCompatActivity {
    ListView groups_listView;

    ArrayList<String> myGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        myGroups = new ArrayList<>();
        myGroups.add("Famille");
        myGroups.add("Amis");
        myGroups.add("Sorties");

        groups_listView = (ListView)findViewById(R.id.groups_listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_my_groups, R.id.groups_listView, myGroups);
        groups_listView.setAdapter(arrayAdapter);
    }
}

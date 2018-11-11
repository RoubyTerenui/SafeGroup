package com.project.safegroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.safegroup.R;

import java.util.ArrayList;

public class MyGroupsActivity extends AppCompatActivity {
    ListView simpleList;
    String countryList[] = {"Favoris", "Soirées", "Famille", "Amis", "Travail", "Autres"};
    int flags[] = {R.drawable.favorite_icon, R.drawable.party_icon, R.drawable.family_icon, R.drawable.friends_icon, R.drawable.work_icon, R.drawable.other_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);


        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);
    }
}

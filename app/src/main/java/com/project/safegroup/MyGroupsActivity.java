package com.project.safegroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.louis.safegroup.R;

import java.util.ArrayList;

public class MyGroupsActivity extends AppCompatActivity {
    ListView simpleList;
    String countryList[] = {"Favoris", "Soirées", "Famille", "Amis", "Travail", "Autres"};
    int flags[] = {R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp, R.drawable.ic_notifications_black_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);


        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);
    }
}

package com.project.safegroup.GroupDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.project.safegroup.GroupDetails.GroupDetailsExpandable.GroupDetailExpandableFragment;
import com.project.safegroup.MainActivity;
import com.project.safegroup.R;

/**
 * An activity representing a single Group detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 */
public class GroupDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(GroupDetailExpandableFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(GroupDetailExpandableFragment.ARG_ITEM_ID));
            GroupDetailExpandableFragment fragment = new GroupDetailExpandableFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.group_detail_container, fragment)
                    .commit();
        }
    }
}

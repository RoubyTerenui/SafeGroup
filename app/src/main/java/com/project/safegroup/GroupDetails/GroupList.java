package com.project.safegroup.GroupDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//import com.project.safegroup.GroupDetails.dummy.DBManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.GroupDetailExpandableFragment;
import com.project.safegroup.GroupDetails.dummy.GroupData;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.GroupSelection.GroupSelectionDataAdapter;
import com.project.safegroup.MainActivity;
import com.project.safegroup.NewGroupActivity;
import com.project.safegroup.R;

import com.project.safegroup.GroupDetails.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class GroupList extends Fragment {

    private boolean mTwoPane;
    private static ListView groupList;
    private static Context mContext;
    private static final int NEWGROUP_LAUNCH = 123;
    private Button favBut;
    private Button parBut;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_group_list,container,false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewGroupActivity.class);
                startActivityForResult(intent,NEWGROUP_LAUNCH);
                Snackbar.make(view, "Ajouter un nouveau groupe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (view.findViewById(R.id.group_detail_container) != null) {
            mTwoPane = true;
        }
        mContext = getContext();
        groupList = (ListView) view.findViewById(R.id.group_list);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(GroupDetailExpandableFragment.ARG_ITEM_ID,DummyContent.ITEMS.get(position).getGroupID());
                    GroupDetailExpandableFragment fragment = new GroupDetailExpandableFragment();
                    fragment.setArguments(arguments);
                    ((MainActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.group_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, GroupDetailActivity.class);
                    intent.putExtra(GroupDetailExpandableFragment.ARG_ITEM_ID,DummyContent.ITEMS.get(position).getGroupID());
                    context.startActivity(intent);
                }
            }
        });
        DummyContent.create(groupList, this, mContext);
        return view;
    }

}

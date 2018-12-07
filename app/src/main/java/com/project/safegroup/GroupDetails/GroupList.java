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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.project.safegroup.GroupDetails.dummy.DBManager;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.ExpandableMemberAdapter;
import com.project.safegroup.GroupDetails.GroupDetailsExpandable.GroupDetailExpandableFragment;
import com.project.safegroup.GroupDetails.dummy.GroupData;
import com.project.safegroup.MainActivity;
import com.project.safegroup.NewGroupActivity;
import com.project.safegroup.R;

import com.project.safegroup.GroupDetails.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import dataBase.model.Group;

public class GroupList extends Fragment {

    private boolean mTwoPane;

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
                startActivity(intent);
                Snackbar.make(view, "Ajouter un nouveau groupe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (view.findViewById(R.id.group_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = view.findViewById(R.id.group_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        return view;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        DummyContent.update(recyclerView, this, mTwoPane);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter((MainActivity)getActivity(), DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final MainActivity mParentActivity;
        private final List<GroupData> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupData item = (GroupData) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(GroupDetailExpandableFragment.ARG_ITEM_ID, item.getGroupID());
                    GroupDetailExpandableFragment fragment = new GroupDetailExpandableFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.group_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, GroupDetailActivity.class);
                    intent.putExtra(GroupDetailExpandableFragment.ARG_ITEM_ID, item.getGroupID());
                    context.startActivity(intent);
                }
            }
        };

        public SimpleItemRecyclerViewAdapter(MainActivity parent, List<GroupData> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getGroupID());
            holder.mContentView.setText(mValues.get(position).getGroupName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}

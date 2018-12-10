package com.project.safegroup.GroupDetails;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.safegroup.GroupDetails.dummy.GroupData;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.R;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<GroupData> {

    private ArrayList<GroupData> dataSet;
    Context mContext;


    public GroupDataAdapter(ArrayList<GroupData> data, Context context) {
        super(context, 0, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.group_selection_item,parent,false);

        GroupData currentData = dataSet.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.groupName);
        name.setText(currentData.getGroupName());
        if(currentData.isFavorite()) {
            ImageView star = (ImageView) listItem.findViewById(R.id.star);
        }
        ConstraintLayout layout = (ConstraintLayout) listItem.findViewById(R.id.layout);
        if(currentData.isParty()){
            layout.setBackgroundColor(Color.BLUE);
        }
        else{
            layout.setBackgroundColor(Color.WHITE);
        }
        return listItem;
    }
}

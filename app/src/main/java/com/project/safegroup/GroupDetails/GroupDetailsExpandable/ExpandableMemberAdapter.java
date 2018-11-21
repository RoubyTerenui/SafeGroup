package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.R;

import java.util.ArrayList;

public class ExpandableMemberAdapter extends BaseExpandableListAdapter {

    ArrayList<MemberData> members;
    ArrayList<DescriptionData> descriptions;
    Context mContext;

    public  ExpandableMemberAdapter(ArrayList<MemberData> data,ArrayList<DescriptionData> descriptions, Context context) {
        this.members = data;
        this.descriptions=descriptions;
        this.mContext=context;
    }
    @Override
    public int getGroupCount() {
        return members.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return members.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return descriptions.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.group_detail_expandable_item,parent,false);

        MemberData currentData = members.get(groupPosition);

        TextView name = (TextView) listItem.findViewById(R.id.memberName);
        Button state = (Button) listItem.findViewById(R.id.buttonState);

        name.setText(currentData.getName());
        switch(currentData.getState()){
            case 0:
                state.setBackgroundColor(mContext.getResources().getColor(R.color.colorRedButton));
                break;
            case 1:
                state.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrangeButton));
                break;
            case 2 :
                state.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreenButton));
                break;
            case 3:
                state.setBackgroundColor(Color.LTGRAY);
                break;
            default:
                break;
        }
        return listItem;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View expandedListItem = convertView;
        if(expandedListItem == null)
            expandedListItem = LayoutInflater.from(mContext).inflate(R.layout.group_detail_expandable_item_expanded,parent,false);




        String currentDescription = descriptions.get(groupPosition);

        TextView description = (TextView) expandedListItem.findViewById(R.id.memberDescription);
        description.setText(currentDescription);
        return expandedListItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

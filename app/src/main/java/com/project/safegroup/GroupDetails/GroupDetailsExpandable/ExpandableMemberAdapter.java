package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.content.Context;
import android.content.res.Resources;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        Resources res = mContext.getResources();
        String[] states = res.getStringArray(R.array.state);
        String[] preciseStates = res.getStringArray(R.array.precise_state);
        String description;
        DescriptionData child = descriptions.get(groupPosition);
        if(child.getOtherState()!=null) {
            Date dateSelfState=null;
            Date dateOtherState=null;
            String SdateSelfState = child.getSelfState().getLast_Update();
            String SdateOtherState = child.getOtherState().getLast_Update();
            String name = child.getName();
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
            try {
                dateSelfState = format.parse(SdateSelfState);
                dateOtherState = format.parse(SdateOtherState);
            } catch (java.text.ParseException error) {
                System.out.println("error Date Conversion" + error);
            }
            if (dateOtherState.after(dateSelfState)) {
                int state = child.getOtherState().getState();
                int selfState = child.getSelfState().getState();
                String nameModifier = child.getOtherState().getName();
                description = String.format(res.getString(R.string.description_member), states[state], format.format(dateOtherState), nameModifier, name, states[selfState],format.format(dateSelfState));
            } else {
                int state = child.getState();
                int statePrecision = child.getSelfState().getStateDescription();
                description = String.format(res.getString(R.string.self_description_member), states[state], preciseStates[statePrecision], format.format(dateSelfState));
            }
        }
        else{
            String SdateSelfState = child.getSelfState().getLast_Update();
            int state = child.getState();
            int statePrecision = child.getSelfState().getStateDescription();
            description = String.format(res.getString(R.string.self_description_member), states[state], preciseStates[statePrecision], SdateSelfState);

        }
        TextView descriptionView = (TextView) expandedListItem.findViewById(R.id.memberDescription);
        descriptionView.setText(description);
        return expandedListItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

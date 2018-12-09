package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.safegroup.GroupDetails.GroupDetailActivity;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpandableMemberAdapter extends BaseExpandableListAdapter {

    ArrayList<MemberData> members;
    ArrayList<DescriptionData> descriptions;
    String groupId;
    Context mContext;

    public  ExpandableMemberAdapter(ArrayList<MemberData> data,ArrayList<DescriptionData> descriptions,String groupId, Context context) {
        this.members = data;
        this.descriptions=descriptions;
        this.mContext=context;
        this.groupId = groupId;
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        View expandedListItem = convertView;
        if(expandedListItem == null) {
            expandedListItem = LayoutInflater.from(mContext).inflate(R.layout.group_detail_expandable_item_expanded, parent, false);
        }
        Resources res = mContext.getResources();
        String[] states = res.getStringArray(R.array.state);
        String[] preciseStates = res.getStringArray(R.array.precise_state);
        String description = "";
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

        final boolean isAsked = child.isAsked();
        boolean isSelfDescribed = child.isSelfDescribed();
        boolean isOtherDescribed = child.isOtherDescribed();

        if(isSelfDescribed) {
            int selfStatePrecision = child.getSelfStatePrecision();
            int selfState = child.getSelfState();
            String selfDate = child.getSelfDate();
            description = String.format(res.getString(R.string.self_description_member),states[selfState],preciseStates[selfStatePrecision],selfDate);
        }

        if(isOtherDescribed)
        {
            String otherName = child.getOtherName();
            int otherState = child.getOtherState();
            String otherDate = child.getOtherDate();
            description += String.format(res.getString(R.string.description_member),states[otherState],otherDate,otherName);
        }

        if(!isSelfDescribed && !isOtherDescribed){
            description = res.getString(R.string.no_description);
        }

        TextView descriptionView = (TextView) expandedListItem.findViewById(R.id.memberDescription);
        descriptionView.setText(description);

        Button askButton = (Button) expandedListItem.findViewById(R.id.askButton);
        Button changeButton = (Button) expandedListItem.findViewById(R.id.changeButton);

        if(isAsked){
            changeButton.setBackgroundColor(mContext.getResources().getColor(R.color.common_google_signin_btn_text_dark_pressed));
        }

        //DESIGN DE LA SELECTION DE L'ETAT DES MEMBRES
        final StateDialog cdd=new StateDialog((Activity) mContext,members.get(groupPosition).getName(),groupId);
        askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdd.show();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle click
            }
        });
        return expandedListItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

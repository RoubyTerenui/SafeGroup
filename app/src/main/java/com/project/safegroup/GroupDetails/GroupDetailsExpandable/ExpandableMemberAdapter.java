package com.project.safegroup.GroupDetails.GroupDetailsExpandable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.safegroup.GroupDetails.GroupDetailActivity;
import com.project.safegroup.GroupSelection.GroupSelectionData;
import com.project.safegroup.MainActivity;
import com.project.safegroup.MapsActivity;
import com.project.safegroup.R;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataBase.model.OtherState;
import dataBase.model.SelfState;

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
                state.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreenDarkButton));
                break;
            case 4:
                state.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreyButton));
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
        String description="";
        DescriptionData child = descriptions.get(groupPosition);

        Resources res = mContext.getResources();
        if(child.getOtherState()==null && child.getSelfState()==null){
            description = res.getString(R.string.no_description);
        }
        else{
            String[] states = res.getStringArray(R.array.state);
            String[] preciseStates = res.getStringArray(R.array.precise_state);
            String selfDescription = "";
            String otherDescription = "";

            //Determine est la date la plus récente
            boolean isSelfMoreRecent = true;
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
            if(child.getOtherState()!=null && child.getSelfState()!=null) {
                Date dateSelfState=null;
                Date dateOtherState=null;
                String SdateSelfState = child.getSelfState().getLast_Update();
                String SdateOtherState = child.getOtherState().getLast_Update();
                try {
                    dateSelfState = format.parse(SdateSelfState);
                    Log.d("self",dateSelfState.toString());
                    dateOtherState = format.parse(SdateOtherState);
                    Log.d("other",dateOtherState.toString());
                    if (dateOtherState.after(dateSelfState)) {
                        isSelfMoreRecent=false;
                    }
                } catch (java.text.ParseException error) {
                    System.out.println("error Date Conversion" + error);
                }
            }
            if(child.getSelfState()!=null){
                SelfState _selfState= child.getSelfState();
                int selfStatePrecision = _selfState.getStateDescription();
                int selfState = _selfState.getState();
                String selfDate = _selfState.getLast_Update();
                selfDescription = String.format(res.getString(R.string.self_description_member), states[selfState], preciseStates[selfStatePrecision], selfDate);
            }
            if(child.getOtherState()!=null) {
                OtherState _otherState =child.getOtherState();
                String otherName = _otherState.getName();
                int otherState = _otherState.getState();
                String otherDate = _otherState.getLast_Update();
                otherDescription += String.format(res.getString(R.string.description_member), states[otherState], otherDate, otherName);
            }
            description=(isSelfMoreRecent)?selfDescription+otherDescription:otherDescription+selfDescription;
        }
        TextView descriptionView = (TextView) expandedListItem.findViewById(R.id.memberDescription);
        descriptionView.setText(description);
        final boolean isAsked = child.isAsked();
        Button askButton = (Button) expandedListItem.findViewById(R.id.askButton);
        Button changeButton = (Button) expandedListItem.findViewById(R.id.changeButton);
        Button localiseButton = (Button) expandedListItem.findViewById(R.id.localiseButton);
        final String indexMember = members.get(groupPosition).getId();
        askButton.setText(res.getString(R.string.askState));
        localiseButton.setEnabled(false);
        localiseButton.setClickable(false);
        askButton.setEnabled(false);
        askButton.setClickable(false);
        changeButton.setEnabled(false);
        changeButton.setClickable(false);
        askButton.setText(res.getString(R.string.already_asked));
        if(!members.get(groupPosition).getName().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()))
        {
            changeButton.setEnabled(true);
            changeButton.setClickable(true);
            if(!isAsked){
                askButton.setEnabled(true);
                askButton.setClickable(true);
                askButton.setText(res.getString(R.string.askState));
                askButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("group").child(groupId).child("members").child(indexMember).child("asked").setValue(true);
                    }
                });
            }
            if(child.getSelfState()!=null&&child.getSelfState().getState()==0){
                localiseButton.setEnabled(true);
                localiseButton.setClickable(true);
                localiseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, MapsActivity.class);
                        i.putExtra("USER_ID", indexMember);
                        i.putExtra("GROUP_ID", groupId);
                        mContext.startActivity(i);
                    }
                });
            }
            final StateDialog cdd=new StateDialog((Activity) mContext,members.get(groupPosition).getId(),groupId);
            changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cdd.show();
                }
            });
        }
        return expandedListItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

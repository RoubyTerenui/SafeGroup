package com.project.safegroup.GroupDetails;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        final GroupData currentData = dataSet.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.groupName);
        name.setText(currentData.getGroupName());
        Button favoriteButton = (Button) listItem.findViewById(R.id.star);
        Button partyButton = (Button) listItem.findViewById(R.id.party);
        Resources res = mContext.getResources();
        if(currentData.isFavorite()) {
            favoriteButton.setBackground(res.getDrawable(R.drawable.favori_valide));
        }
        else {
            favoriteButton.setBackground(res.getDrawable(R.drawable.favori_off));
        }
        ConstraintLayout layout = (ConstraintLayout) listItem.findViewById(R.id.layout);
        if(currentData.isParty()){
            partyButton.setBackground(res.getDrawable(R.drawable.fete_valide));
        }
        else{
            partyButton.setBackground(res.getDrawable(R.drawable.fete_off));;
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK","FAV");
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groups").child(currentData.getGroupID()).child("favorite").setValue(!currentData.isFavorite());
            }
        });
        partyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK","PARTY");
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groups").child(currentData.getGroupID()).child("party").setValue(!currentData.isParty());
            }
        });
        return listItem;
    }
}
